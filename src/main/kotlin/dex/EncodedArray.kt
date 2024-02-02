package dex

import java.nio.ByteBuffer

class EncodedArray(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val size: Int  // field_ids

    ////a series of size encoded_value byte sequences in the format specified by this section, concatenated sequentially.
    var values: Array<EncodedValue>? = null

    init {
        size = byteBuffer.readULeb128()
        if (size != 0) {
            values = Array(size) {
                EncodedValue(dexFile, byteBuffer)
            }
        }
    }

    override fun toString(): String {
        return "EncodedArray(values ${values?.toPrint() ?: "values == null"})"
    }
}