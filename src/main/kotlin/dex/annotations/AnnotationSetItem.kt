package dex.annotations

import dex.DexFile
import dex.toPrint
import java.nio.ByteBuffer

class AnnotationSetItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val size: Int  // field_ids
    val entries: Array<AnnotationOffItem>

    init {

        size = byteBuffer.int
        val position = byteBuffer.position()
        entries = Array(size) {
            byteBuffer.position(position + it * 0x04)
            AnnotationOffItem(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "AnnotationSetItem(entries ${entries.toPrint()})"
    }
}