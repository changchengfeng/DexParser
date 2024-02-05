package dex

import java.nio.ByteBuffer

class StringDataItem(byteBuffer: ByteBuffer) {
    val string_data_off_: Int
    val value: String

    init {
        string_data_off_ = byteBuffer.int
        val size = byteBuffer.position(string_data_off_).readULeb128()
        val charArray = CharArray(size)
        value = byteBuffer.readMUTF8(charArray)
    }

    override fun toString(): String {
        return "StringDataItem( $value )"
    }
}