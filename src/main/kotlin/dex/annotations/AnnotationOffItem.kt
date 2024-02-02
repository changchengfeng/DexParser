package dex.annotations

import dex.DexFile
import java.nio.ByteBuffer

class AnnotationOffItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val annotations_off: Int //annotation_item

    val annotationItem: AnnotationItem

    init {
        annotations_off = byteBuffer.int
        byteBuffer.position(annotations_off)
        annotationItem = AnnotationItem(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "AnnotationOffItem(annotationItem $annotationItem)"
    }
}