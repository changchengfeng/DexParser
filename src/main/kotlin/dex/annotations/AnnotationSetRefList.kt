package dex.annotations

import dex.DexFile
import dex.toPrint
import java.nio.ByteBuffer

class AnnotationSetRefList(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val size: Int
    val list: Array<AnnotationSetRefItem>

    init {
        size = byteBuffer.int
        val position = byteBuffer.position()
        list = Array(size) {
            byteBuffer.position(position + it * 0x04)
            AnnotationSetRefItem(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "AnnotationSetRefList(" +
                "list ${list.toPrint()}" +
                ")"
    }
}