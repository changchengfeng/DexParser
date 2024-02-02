package dex.code

import dex.DexFile
import dex.readULeb128
import dex.toPrint
import java.nio.ByteBuffer

class EncodedCatchHandler(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val size: Int
    var handlers: Array<EncodedTypeAddrPair>? = null
    var catch_all_addr: Int? = null

    init {

        size = byteBuffer.readULeb128()

        if (size != 0) {
            handlers = Array(Math.abs(size)) {
                EncodedTypeAddrPair(dexFile, byteBuffer)
            }
        }
        if (size <= 0) {
            catch_all_addr = byteBuffer.readULeb128()
        }

    }

    override fun toString(): String {
        return "EncodedCatchHandler(handlers ${handlers?.toPrint() ?: " == null"} catch_all_addr ${catch_all_addr?.also { } ?: " == null"})"
    }
}