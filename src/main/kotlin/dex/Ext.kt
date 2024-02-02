package dex

import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString
import okio.ByteString.Companion.readByteString
import okio.ByteString.Companion.toByteString
import java.io.ByteArrayInputStream
import java.nio.ByteOrder
import kotlin.experimental.and




typealias u2 = Short
typealias u1 = Byte
typealias u4 = Int
typealias u1Array = ByteArray
typealias u2Array = ShortArray

sealed class BooleanExt<out T>
object Otherwise : BooleanExt<Nothing>()
class WithData<T>(val data: T) : BooleanExt<T>()

inline fun <T> Boolean.yes(block: () -> T) =
    when {
        this -> {
            WithData(block())
        }

        else -> {
            Otherwise
        }
    }

fun ByteString.toInt(order: ByteOrder = ByteOrder.BIG_ENDIAN): Int {
    if (size == 0) {
        return 0
    }
    if (size == 1) {
        return this[0].toInt()
    }
    if (order == ByteOrder.BIG_ENDIAN) {

        when (this.size) {
            4 -> {
                return (this[0].toInt() shl 24) or
                        (this[1].toInt() shl 16) or
                        (this[2].toInt() shl 8) or
                        (this[3].toInt())
            }

            3 -> {
                return (this[0].toInt() shl 16) or
                        (this[1].toInt() shl 8) or
                        this[2].toInt()
            }

            2 -> {
                return (this[0].toInt() shl 8) or
                        this[1].toInt()
            }
        }
    } else if (order == ByteOrder.LITTLE_ENDIAN) {
        when (this.size) {
            4 -> {
                return (this[3].toInt() shl 24) or
                        (this[2].toInt() shl 16) or
                        (this[1].toInt() shl 8) or
                        (this[0].toInt())
            }

            3 -> {
                return (this[2].toInt() shl 16) or
                        (this[1].toInt() shl 8) or
                        this[0].toInt()
            }

            2 -> {
                return (this[1].toInt() shl 8) or
                        this[0].toInt()
            }
        }
    }
    throw IllegalArgumentException("ByteString size (= ${this.size}) error ")
}

fun Int.toByteString(): ByteString {
    val byteArray = ByteArray(4)
    byteArray[0] = ((this shr 24) and 0x000000FF).toByte()
    byteArray[1] = ((this shr 16) and 0x000000FF).toByte()
    byteArray[2] = ((this shr 8) and 0x000000FF).toByte()
    byteArray[3] = (this and 0x000000FF).toByte()
    return ByteArrayInputStream(byteArray).readByteString(4)
}


fun BufferedSource.readFloat(): Float {
    return Float.fromBits(readInt())
}

fun BufferedSource.readDouble(): Double {
    return Double.fromBits(readLong())
}

inline fun <T> Boolean.no(block: () -> T) = when {
    this -> Otherwise
    else -> {
        WithData(block())
    }
}

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Otherwise -> block()
        is WithData -> this.data
    }


fun arrayToStr(arrayOfAnys: Array<out Any?>, tag: String, level: Int = 3): String {
    val stringBuffer = StringBuilder()
    val tab = StringBuilder()

    for (it in arrayOfAnys.indices) {
        if (it != 0) {
            tab.clear()
            for (it in 0..level) {
                tab.append("    ")
            }
        }
        stringBuffer.append("${tab}$tag[${it}] = ${arrayOfAnys[it]}")
        if (it != arrayOfAnys.size - 1) {
            stringBuffer.append("\n")
        }
    }
    return stringBuffer.toString()
}

fun u1Array.readInt(index: Int): Int {
    return byteArrayOf(this[index], this[index + 1], this[index + 2], this[index + 3]).toByteString()
        .toInt()
}

inline fun <T> Array<out T>.printToString(): String {
    val builder = StringBuilder()
    val componentType = this.javaClass.componentType.simpleName
    builder.append("\n..........................................$componentType start ......................................................\n")
    for (i in indices) {
        builder.append("$componentType[${i}] = ${this[i]}\n")
    }
    builder.append("..........................................$componentType end........................................................")
    return builder.toString()
}



fun BufferedSource.readSignedLeb128(): Int {
    var result = 0
    var cur: Int
    var count = 0
    var signBits = -1


    do {
        cur = (this.readByte() and 0xff.toByte()).toInt()
        result = result or (cur and 0x7f shl count * 7)
        signBits = signBits shl 7
        count++
    } while (cur and 0x80 == 0x80 && count < 5)


    if (cur and 0x80 == 0x80) {
        throw DexException("invalid LEB128 sequence")
    }

    // Sign extend if appropriate
    if (signBits shr 1 and result != 0) {
        result = result or signBits
    }

    return result
}


fun BufferedSource.readUnsignedLeb128(): Int {
    var result = 0
    var cur: Int
    var count = 0
    do {
        cur = (this.readByte() and 0xff.toByte()).toInt()
        result = result or (cur and 0x7f shl count * 7)
        count++
    } while (cur and 0x80 == 0x80 && count < 5)
    if (cur and 0x80 == 0x80) {
        throw DexException("invalid LEB128 sequence")
    }
    return result
}


/**
 * Writes `value` as an unsigned integer to `out`, starting at
 * `offset`. Returns the number of bytes written.
 */
fun BufferedSink.writeUnsignedLeb128(value: Int) {
    var value = value
    var remaining = value ushr 7
    while (remaining != 0) {
        this.writeByte(value and 0x7f or 0x80)
        value = remaining
        remaining = remaining ushr 7
    }
    this.writeByte(value and 0x7f)
    this.flush()
}

/**
 * Writes `value` as a signed integer to `out`, starting at
 * `offset`. Returns the number of bytes written.
 */
fun BufferedSink.writeSignedLeb128(value: Int) {
    var value = value
    var remaining = value shr 7
    var hasMore = true
    val end = if (value and Int.MIN_VALUE == 0) 0 else -1
    while (hasMore) {
        hasMore = (remaining != end
                || remaining and 1 != value shr 6 and 1)
        this.writeByte(value and 0x7f or if (hasMore) 0x80 else 0)
        value = remaining
        remaining = remaining shr 7
    }
    this.flush()
}


fun Array<out Any>.toPrint(tab: Int = 0): String {
    val builder = StringBuilder()
    for (it in this.indices) {
        for (int in 0 until tab) {
            builder.append("        ")
        }
        builder.append("#${it} = ${this[it]}")
        if (it != (size - 1)) {
            builder.append("\n")
        }
    }
    return builder.toString()
}
fun List<out Any>.toPrintList(tab: Int = 0): String {
    val builder = StringBuilder()
    for (it in this.indices) {
        for (int in 0 until tab) {
            builder.append("        ")
        }
        builder.append("#0x${it.toString(16)} = ${this[it]}")
        if (it != (size - 1)) {
            builder.append("\n")
        }
    }
    return builder.toString()
}

fun Array<ByteArray>.toPrintln(): String {
    val builder = StringBuilder()
    for (it in this.indices) {
        builder.append("#${it} = ${this[it].toByteString().hex()}\n")
    }
    return builder.toString()
}




