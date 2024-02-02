package dex.annotations

import dex.DexFile
import dex.readULeb128
import dex.toPrint
import java.nio.ByteBuffer

class EncodedAnnotation(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val type_idx: Int  // type of the annotation. This must be a class (not array or primitive) type.
    val size: Int //number of name-value mappings in this annotation
    val elements: Array<AnnotationElement>

    init {

        type_idx = byteBuffer.readULeb128()
        size = byteBuffer.readULeb128()
        elements = Array(size) {
            AnnotationElement(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "EncodedAnnotation(type_idx #${type_idx} ${dexFile.typeIdItems[type_idx]} elements ${elements.toPrint()} )"
    }
}