package dex

import java.nio.ByteBuffer

class EncodedArrayItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val value: EncodedArray

    init {
        value = EncodedArray(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "EncodedArrayItem(value $value)"
    }
}