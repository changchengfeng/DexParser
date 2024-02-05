package dex

import dex.code.OpcodeType
import dex.code.Opcodes
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


fun Array<out Any>.toPrint(): String {
    val builder = StringBuilder()
    for (it in this.indices) {
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


fun BufferedSource.toPrintln(dexFile: DexFile): String {
    val builder = StringBuilder()

    while (!exhausted()) {
        val opcodes = Opcodes.getOpcodesTypeBy(readByte().toUByte())
        println(" opcodes = $opcodes")
        when (opcodes.type) {
            OpcodeType.T10x -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("ØØ${readByte()}")
                builder.append(")\n")
            }

            OpcodeType.T12x -> {
                val byte = readByte()
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${byte and 0x0f.toByte()},")
                builder.append("v${byte and 0xf0.toByte()}")
                builder.append(")\n")
            }

            OpcodeType.T11n -> {
                val byte = readByte()
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${byte and 0x0f.toByte()},")
                builder.append("#${byte and 0xf0.toByte()}")
                builder.append(")\n")
            }

            OpcodeType.T11x -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()}")
                builder.append(")\n")
            }

            OpcodeType.T10t -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("+${readByte()}")
                builder.append(")\n")
            }

            OpcodeType.T20t -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("ØØ${readByte()},")
                builder.append("+${readShortLe()}")
                builder.append(")\n")
            }

            OpcodeType.T22x -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("v${readShortLe()}")
                builder.append(")\n")
            }

            OpcodeType.T21t -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("+${readShortLe()}")
                builder.append(")\n")
            }

            OpcodeType.T21s -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("#${readShortLe()}")
                builder.append(")\n")
            }

            OpcodeType.T21h_1 -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("#${readShortLe()}")
                builder.append(" 0000")
                builder.append(")\n")
            }

            OpcodeType.T21h_2 -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("#${readShortLe()}")
                builder.append(" 00000000")
                builder.append(")\n")
            }

            OpcodeType.T21c_type -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                val shortLe = readShortLe()
                builder.append("${dexFile.typeIdItems[shortLe.toInt()]} ,")
                builder.append("type@${shortLe}")
                builder.append(")\n")
            }

            OpcodeType.T21c_field -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                val shortLe = readShortLe()
                builder.append("${dexFile.fieldIdItems[shortLe.toInt()]} ,")
                builder.append("field@${shortLe}")
                builder.append(")\n")
            }

            OpcodeType.T21c_method_handle -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                val shortLe = readShortLe()
                builder.append("${dexFile.methodHandles?.get(shortLe.toInt())} ,")
                builder.append("method_handle@${shortLe}")
                builder.append(")\n")
            }

            OpcodeType.T21c_proto -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                val shortLe = readShortLe()
                builder.append("${dexFile.protoIdItems[shortLe.toInt()]} ,")
                builder.append("proto@${shortLe}")
                builder.append(")\n")
            }

            OpcodeType.T21c_string -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                val shortLe = readShortLe()
                builder.append("${dexFile.stringDataItems[shortLe.toInt()]} ,")
                builder.append("string@${shortLe}")
                builder.append(")\n")
            }

            OpcodeType.T23x -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("v${readByte()},")
                builder.append("v${readByte()},")
                builder.append(")\n")
            }

            OpcodeType.T22b -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("v${readByte()},")
                builder.append("#${readByte()},")
                builder.append(")\n")
            }

            OpcodeType.T22t -> {
                builder.append(opcodes.name)
                builder.append("(")
                val byte = readByte()
                builder.append("v${byte and 0x0f.toByte()},")
                builder.append("v${byte and 0xf0.toByte()},")
                builder.append("+${readShortLe()},")
                builder.append(")\n")
            }

            OpcodeType.T22s -> {
                builder.append(opcodes.name)
                builder.append("(")
                val byte = readByte()
                builder.append("v${byte and 0x0f.toByte()},")
                builder.append("v${byte and 0xf0.toByte()},")
                builder.append("#${readShortLe()},")
                builder.append(")\n")
            }

            OpcodeType.T22c_type -> {
                builder.append(opcodes.name)
                builder.append("(")
                val byte = readByte()
                builder.append("v${byte and 0x0f.toByte()},")
                builder.append("v${byte and 0xf0.toByte()},")
                val shortLe = readShortLe()
                builder.append("${dexFile.typeIdItems[shortLe.toInt()]} ,")
                builder.append("type@${shortLe}")
                builder.append(")\n")
            }

            OpcodeType.T22c_field -> {
                builder.append(opcodes.name)
                builder.append("(")
                val byte = readByte()
                builder.append("v${byte and 0x0f.toByte()},")
                builder.append("v${byte and 0xf0.toByte()},")
                val shortLe = readShortLe()
                builder.append("${dexFile.fieldIdItems[shortLe.toInt()]} ,")
                builder.append("field@${shortLe}")
                builder.append(")\n")
            }

            OpcodeType.T30t -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("ØØ${readByte()},")
                builder.append("+${readIntLe()}")
                builder.append(")\n")
            }

            OpcodeType.T32x -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("ØØ${readByte()},")
                builder.append("v${readShortLe()}")
                builder.append("v${readShortLe()}")
                builder.append(")\n")
            }

            OpcodeType.T31i -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("#${readIntLe()}")
                builder.append(")\n")
            }

            OpcodeType.T31t -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                builder.append("+${readIntLe()}")
                builder.append(")\n")
            }

            OpcodeType.T31c -> {
                builder.append(opcodes.name)
                builder.append("(")
                builder.append("v${readByte()},")
                val readIntLe = readIntLe()
                builder.append("${dexFile.stringDataItems[readIntLe]}")
                builder.append("string@${readIntLe}")
                builder.append(")\n")
            }

            OpcodeType.T35c -> {
                builder.append(opcodes.name)
                builder.append("(")
                val byte = readByte()
                val A = byte and 0xf0.toByte()
                val G = byte and 0x0f.toByte()
                val BBBB = readShortLe()
                val byte2 = readByte()
                val D = byte2 and 0xf0.toByte()
                val C = byte2 and 0x0f.toByte()
                val byte3 = readByte()
                val F = byte3 and 0xf0.toByte()
                val E = byte3 and 0x0f.toByte()
                var refType: String
                var refValue: String
                if (opcodes == Opcodes.filledNewArray) {
                    refType = "type"
                    refValue = "${dexFile.typeIdItems[BBBB.toInt()]}"
                } else if (opcodes == Opcodes.invokeCustom) {
                    refType = "call_site"
                    refValue = "${dexFile.callSiteIds?.get(BBBB.toInt())}"
                } else {
                    refType = "meth"
                    refValue = "${dexFile.methodIdItems[BBBB.toInt()]}"
                }
                when (A) {
                    0.toByte() -> {}
                    1.toByte() -> {
                        builder.append("v${C},")
                    }

                    2.toByte() -> {
                        builder.append("v${C},v${D},")
                    }

                    3.toByte() -> {
                        builder.append("v${C},v${D},v${E},")
                    }

                    4.toByte() -> {
                        builder.append("v${C},v${D},v${E},v${F},")
                    }

                    5.toByte() -> {
                        builder.append("v${C},v${D},v${E},v${F},v${G},")
                    }
                }
                builder.append("${refValue},")
                builder.append("${refType}@${BBBB}")
                builder.append(")\n")
            }

            OpcodeType.T3rc -> {
                builder.append(opcodes.name)
                builder.append("(")
                val AA = readByte()
                val BBBB = readShortLe()
                val CCCC = readShortLe()
                val NNNN = (CCCC + AA - 1).toShort()
                builder.append("v${CCCC} ,")
                builder.append("v${NNNN} ,")
                var refType: String
                var refValue: String
                if (opcodes == Opcodes.filledNewArrayRange) {
                    refType = "type"
                    refValue = "${dexFile.typeIdItems[BBBB.toInt()]}"
                } else if (opcodes == Opcodes.invokeCustomRange) {
                    refType = "call_site"
                    refValue = "${dexFile.callSiteIds?.get(BBBB.toInt())}"
                } else {
                    refType = "meth"
                    refValue = "${dexFile.methodIdItems[BBBB.toInt()]}"
                }
                builder.append("${refValue},")
                builder.append("${refType}@${BBBB}")
                builder.append(")\n")
            }


            OpcodeType.T45cc -> {
                builder.append(opcodes.name)
                builder.append("(")
                val byte = readByte()
                val A = byte and 0xf0.toByte()
                val G = byte and 0x0f.toByte()
                val BBBB = readShortLe()
                val byte2 = readByte()
                val D = byte2 and 0xf0.toByte()
                val C = byte2 and 0x0f.toByte()
                val byte3 = readByte()
                val F = byte3 and 0xf0.toByte()
                val E = byte3 and 0x0f.toByte()
                val HHHH = readShortLe()
                when (A) {
                    1.toByte() -> {
                        builder.append("v${C},")
                    }

                    2.toByte() -> {
                        builder.append("v${C},v${D},")
                    }

                    3.toByte() -> {
                        builder.append("v${C},v${D},v${E},")
                    }

                    4.toByte() -> {
                        builder.append("v${C},v${D},v${E},v${F},")
                    }

                    5.toByte() -> {
                        builder.append("v${C},v${D},v${E},v${F},v${G},")
                    }
                }
                builder.append("${dexFile.protoIdItems[HHHH.toInt()]},")
                builder.append("proto@${HHHH}")
                builder.append("${dexFile.methodIdItems[BBBB.toInt()]},")
                builder.append("meth@${BBBB}")
                builder.append(")\n")
            }

            OpcodeType.T4rcc -> {
                builder.append(opcodes.name)
                builder.append("(")
                val AA = readByte()
                val BBBB = readShortLe()
                val CCCC = readShortLe()
                val HHHH = readShortLe()
                val NNNN = CCCC + AA - 1
                builder.append("v${CCCC},")
                builder.append("v${NNNN},")
                builder.append("${dexFile.methodIdItems[BBBB.toInt()]},")
                builder.append("meth@${BBBB}")
                builder.append("${dexFile.protoIdItems[HHHH.toInt()]},")
                builder.append("proto@${HHHH}")
                builder.append(")\n")
            }

            OpcodeType.T51l -> {
                builder.append(opcodes.name)
                builder.append("(")
                val AA = readByte()
                val BBBBlo = readShortLe()
                val BBBBBBBB = readIntLe()
                val BBBBhi = readShortLe()
                val BBBBBBBBBBBBBBBB =
                    (BBBBhi.toLong() shl 48) + (BBBBBBBB.toLong() shl 16) +
                            BBBBlo.toLong()
                builder.append("v${AA},")
                builder.append("#+ $BBBBBBBBBBBBBBBB")
                builder.append(")\n")
            }
        }
    }
    return builder.toString()
}




