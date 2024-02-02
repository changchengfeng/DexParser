package dex

import java.nio.ByteBuffer

class TypeIdItem(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val descriptor_idx_: Int

    init {
        descriptor_idx_ = byteBuffer.int
    }

    override fun toString(): String {
        return "TypeIdItem( #${descriptor_idx_} = ${dexFile.stringDataItems[descriptor_idx_]} )"
    }
}