package dex

import dex.u2
import java.nio.ByteBuffer

class MethodIdItem(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val class_idx_: u2
    val proto_idx_: u2
    val name_idx_: Int

    init {
        class_idx_ = byteBuffer.short
        proto_idx_ = byteBuffer.short
        name_idx_ = byteBuffer.int
    }

    override fun toString(): String {
        return "MethodIdItem(\n" +
                "       class_idx_ #$class_idx_ ${dexFile.typeIdItems[class_idx_.toInt()]}\n" +
                "       proto_idx_ #$proto_idx_ ${dexFile.protoIdItems[proto_idx_.toInt()]}\n" +
                "       name_idx_ #$name_idx_ ${dexFile.stringDataItems[name_idx_]}" +
                ")"
    }
}