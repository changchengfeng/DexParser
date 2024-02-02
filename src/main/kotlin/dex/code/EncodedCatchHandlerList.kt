package dex.code

import dex.DexFile
import dex.readULeb128
import dex.toPrint
import java.nio.ByteBuffer

class EncodedCatchHandlerList(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val size: Int
    val list: Array<EncodedCatchHandler>

    init {
        size = byteBuffer.readULeb128()
        list = Array(size) {
            EncodedCatchHandler(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "EncodedCatchHandlerList(list ${list.toPrint()})"
    }
}