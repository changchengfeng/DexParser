package dex.code

import dex.DexFile
import dex.readULeb128
import java.nio.ByteBuffer

class EncodedTypeAddrPair(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val type_idx: Int
    val addr: Int

    init {

        type_idx = byteBuffer.readULeb128()
        addr = byteBuffer.readULeb128()
    }

    override fun toString(): String {
        return "EncodedTypeAddrPair(type_idx #$type_idx ${dexFile.typeIdItems[type_idx]} addr $addr )"
    }
}