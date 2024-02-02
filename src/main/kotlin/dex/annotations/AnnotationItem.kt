package dex.annotations

import dex.DexFile
import java.nio.ByteBuffer

const val VISIBILITY_BUILD = 0x00  //intended only to be visible at build time (e.g., during compilation of other code)
const val VISIBILITY_RUNTIME = 0x01  // intended to visible at runtime
const val VISIBILITY_SYSTEM =
    0x02 // intended to visible at runtime, but only to the underlying system (and not to regular user code)

class AnnotationItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val visibility: Byte  // field_ids
    val annotation: EncodedAnnotation

    init {

        visibility = byteBuffer.get()
//        println("AnnotationItem visibility $visibility")
        annotation = EncodedAnnotation(dexFile, byteBuffer)
    }

    override fun toString(): String {
        return "AnnotationItem(visibility $visibility annotation $annotation)"
    }
}