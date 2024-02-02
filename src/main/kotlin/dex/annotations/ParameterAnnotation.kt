package dex.annotations

import dex.DexFile
import java.nio.ByteBuffer

class ParameterAnnotation(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val method_idx: Int  // field_ids
    val annotations_off: Int //annotation_set_ref_list
    val annotationSetRefList: AnnotationSetRefList

    init {

        method_idx = byteBuffer.int
        annotations_off = byteBuffer.int
        byteBuffer.position(annotations_off)
        annotationSetRefList = AnnotationSetRefList(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "ParameterAnnotation(" +
                "method_idx #${method_idx} ${dexFile.methodIdItems[method_idx]}" +
                "annotationSetRefList ${annotationSetRefList}" +
                ")"
    }
}