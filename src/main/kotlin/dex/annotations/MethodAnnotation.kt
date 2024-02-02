package dex.annotations

import dex.DexFile
import java.nio.ByteBuffer

class MethodAnnotation(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val method_idx: Int  // method_ids
    val annotations_off: Int //annotation_set_item

    val annotationSetItem: AnnotationSetItem

    init {

        method_idx = byteBuffer.int
        annotations_off = byteBuffer.int

        byteBuffer.position(annotations_off)
        annotationSetItem = AnnotationSetItem(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "MethodAnnotation(" +
                "method_idx #$method_idx ${dexFile.methodIdItems[method_idx]}\n" +
                "annotationSetItem ${annotationSetItem}" +
                ")"
    }
}