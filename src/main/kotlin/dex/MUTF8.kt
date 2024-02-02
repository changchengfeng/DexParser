package dex;

import java.io.UTFDataFormatException
import java.nio.ByteBuffer
import kotlin.experimental.and


@Throws(UTFDataFormatException::class)
fun ByteBuffer.readMUTF8(out: CharArray): String {
    var s = 0
    while (true) {
        val a = (get() and 0xff.toByte()).toInt().toChar()
        if (a.code == 0) {
            return String(out, 0, s)
        }
        out[s] = a
        if (a < '\u0080') {
            s++
        } else if (a.code and 0xe0 == 0xc0) {
            val b = get() and 0xff.toByte()
            if ((b and 0xC0.toByte()) != (0x80).toByte()) {
                throw UTFDataFormatException("bad second byte")
            }
            out[s++] = ((a.code and 0x1F shl 6) or (b and 0x3F.toByte()).toInt()).toChar()
        } else if (a.code and 0xf0 == 0xe0) {
            val b = get() and (0xff.toByte())
            val c = get() and (0xff.toByte())
            if (((b and 0xC0.toByte()) != 0x80.toByte()) || ((c and 0xC0.toByte()) != 0x80.toByte())) {
                throw UTFDataFormatException("bad second or third byte")
            }
            out[s++] =
                (((a.code and 0x0F) shl 12) or ((b and 0x3F).toInt() shl 6) or ((c and 0x3F.toByte()).toInt())).toChar()
        } else {
            throw UTFDataFormatException("bad byte")
        }
    }
}