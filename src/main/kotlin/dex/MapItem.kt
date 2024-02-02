package dex

import java.nio.ByteBuffer

class MapItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val type: TYPE_CODES
    val unused: Short
    val size: Int
    val offset: Int

    init {
        type = TYPE_CODES.getTypeCodeByValue(byteBuffer.short)
        unused = byteBuffer.short
        size = byteBuffer.int
        offset = byteBuffer.int
    }

    override fun toString(): String {
        return "MapItem(    type $type size $size offset $offset    )"
    }
}