package dex.annotations

import dex.DexFile
import java.nio.ByteBuffer

class FieldAnnotation(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val field_idx: Int  // field_ids
    val annotations_off: Int //annotation_set_item
    val annotationSetItem: AnnotationSetItem

    init {
        field_idx = byteBuffer.int
        annotations_off = byteBuffer.int
        byteBuffer.position(annotations_off)
        annotationSetItem = AnnotationSetItem(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "FieldAnnotation(" +
                "   field_idx #${field_idx} ${dexFile.fieldIdItems[field_idx]} \n" +
                "   annotationSetItem #${annotationSetItem} \n" +
                ")"
    }
}