package dex.annotations

import dex.DexFile
import dex.EncodedValue
import dex.readULeb128
import java.nio.ByteBuffer

class AnnotationElement(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val name_idx: Int  // string_ids
    val value: EncodedValue //element value

    init {
        name_idx = byteBuffer.readULeb128()
        value = EncodedValue(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "AnnotationElement(name_idx #$name_idx ${dexFile.stringDataItems[name_idx]} value $value)"
    }
}