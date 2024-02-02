package dex

import java.nio.ByteBuffer

class TypeItem(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val type_idx: Short

    init {
        type_idx = byteBuffer.short
    }

    override fun toString(): String {
        return "TypeItem(    type_idx #${type_idx} ${dexFile.typeIdItems[type_idx.toInt()]}    )"
    }
}