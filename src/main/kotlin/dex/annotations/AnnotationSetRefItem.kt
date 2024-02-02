package dex.annotations

import dex.DexFile
import java.nio.ByteBuffer

class AnnotationSetRefItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val annotations_off: Int
    val annotation_set_item: AnnotationSetItem

    init {
        annotations_off = byteBuffer.int
        byteBuffer.position(annotations_off)
        annotation_set_item = AnnotationSetItem(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "AnnotationSetRefItem(" +
                "annotation_set_item $annotation_set_item" +
                ")"
    }
}