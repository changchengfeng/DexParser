package dex

import java.nio.ByteBuffer

class FieldIdItem(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val class_idx_: u2
    val type_idx_: u2
    val name_idx_: Int

    init {
        class_idx_ = byteBuffer.short
        type_idx_ = byteBuffer.short
        name_idx_ = byteBuffer.int
    }

    override fun toString(): String {

        return """
FieldIdItem( 
          class_idx_ #$class_idx_ ${dexFile.typeIdItems[class_idx_.toInt()]}
          type_idx_ #$type_idx_ ${dexFile.typeIdItems[type_idx_.toInt()]}
          name_idx_ #$name_idx_ ${dexFile.stringDataItems[name_idx_]} )
               """
    }
}