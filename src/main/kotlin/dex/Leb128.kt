package dex

import dex.toByteString
import dex.toInt
import okio.ByteString.Companion.toByteString
import okio.buffer
import okio.sink
import okio.source
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.Base64.Decoder
import kotlin.experimental.and
import kotlin.experimental.or


fun main() {
//    println(" encodeToLeb128(624485) = ${encodeToLeb128(624485).toByteString()}")
//    println(" encodeToLeb128(-123456) = ${encodeToLeb128(-123456).toByteString()}")
//    println(" encodeToLeb128(2097151) = ${encodeToLeb128(2097151).toByteString()}")
//    println(" encodeToLeb128(-2097151) = ${encodeToLeb128(-2097151).toByteString()}")
//
//
//    var byteArrayOutputStream = ByteArrayOutputStream(5)
//    byteArrayOutputStream.sink().buffer().writeSignedLeb128(2097151)
//    println(byteArrayOutputStream.toByteArray().toByteString())
//
//    byteArrayOutputStream = ByteArrayOutputStream(5)
//    byteArrayOutputStream.sink().buffer().writeSignedLeb128(-2097151)
//    println(byteArrayOutputStream.toByteArray().toByteString())


//    println("encodeToLeb128(624485) = ${encodeToLeb128(624485).toByteString()}  ${decoder(encodeToLeb128(624485))}")
//    println("encodeToLeb128(-123456) = ${encodeToLeb128(-123456).toByteString()}  ${decoder(encodeToLeb128(-123456))}")
//
//    println("encodeToLeb128(2097151) = ${encodeToLeb128(2097151).toByteString()}  ${decoder(encodeToLeb128(2097151))}")
//    println("encodeToLeb128(-2097151) = ${encodeToLeb128(-2097151).toByteString()}  ${decoder(encodeToLeb128(-2097151))}")
//    println("encodeToLeb128(-10000) = ${encodeToLeb128(-10000).toByteString()}  ${decoder(encodeToLeb128(-10000))}")
}

fun encodeToLeb128(value: Int) =
    if (value >= 0) {
        val size = calSize(value)
        val bytes = ByteArray(size)
        encode(size, bytes, value)
    } else {
        val size = calSize(-value)
        val temp = -value
        val bytes = ByteArray(size)
        var toInt = 0
        for (it in size - 1 downTo 0) {
            bytes[it] = (((temp shr (it * 7)).inv()) and 0x7F).toByte()
            toInt += (bytes[it].toInt() shl (it * 7))
        }
        toInt = (toInt + 1)
        encode(size, bytes, toInt)
    }

private fun encode(size: Int, bytes: ByteArray, value: Int): ByteArray {
    for (it in size - 1 downTo 0) {
        bytes[it] = (value shr (it * 7) and 0x7F).toByte()
        if (it != (size - 1)) {
            bytes[it] = bytes[it] or 0x80.toByte()
        }
    }
    return bytes
}

private fun calSize(value: Int): Int {
    var temp = value
    var bit = 1
    while ((temp shr 1) > 0) {
        temp = temp shr 1
        bit++
    }
    return (bit / 7 + 1)
}

fun decoder(bytes: ByteArray): Int {
    var sum = 0
    var size = 0
    var temp: Int
    for (it in bytes.indices) {
        temp = (bytes[it] and 0xFF.toByte()).toInt()
        if ((temp and 0x80) == 0x80) {
            sum += (temp and 0x7f shl (it * 7))
            size++
        } else {
            sum += (temp and 0x7f shl (it * 7))
            size++
            break
        }
    }
    if ((sum shr (size * 7 - 1)) > 0) {
        sum = sum - 1
        sum = sum.inv() and ((1 shl (size * 7)) - 1)
        sum = -sum
    }
    return sum
}

fun ByteBuffer.readSignLeb128(): Int {
    var sum = 0
    var size = 0
    var temp: Int
    while (true) {
        temp = (get() and 0xFF.toByte()).toInt()
        if ((temp and 0x80) == 0x80) {
            sum += (temp and 0x7f shl (size * 7))
            size++
        } else {
            sum += (temp and 0x7f shl (size * 7))
            size++
            break
        }
    }
    if ((sum shr (size * 7 - 1)) > 0) {
        sum = sum - 1
        sum = sum.inv() and ((1 shl (size * 7)) - 1)
        sum = -sum
    }
    return sum
}

fun ByteBuffer.readULeb128(): Int {
    var sum = 0
    var size = 0
    var temp: Int
    while (true) {
        temp = (get() and 0xFF.toByte()).toInt()
        if ((temp and 0x80) == 0x80) {
            sum += (temp and 0x7f shl (size * 7))
            size++
        } else {
            sum += (temp and 0x7f shl (size * 7))
            size++
            break
        }
    }
    return sum
}