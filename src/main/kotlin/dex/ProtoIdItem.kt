package dex

import java.nio.ByteBuffer

class ProtoIdItem(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val shorty_idx_: Int
    val return_type_idx_: Int
    val parameters_off_: Int
    var typeList: TypeList? = null

    init {
        shorty_idx_ = byteBuffer.int
        return_type_idx_ = byteBuffer.int
        parameters_off_ = byteBuffer.int
        if (parameters_off_ != 0) {
            byteBuffer.position(parameters_off_)
            typeList = TypeList(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "ProtoIdItem( \n" +
                "       shorty_idx_ #$shorty_idx_  ${dexFile.stringDataItems[shorty_idx_]}\n" +
                "       return_type_idx_ #$return_type_idx_ ${dexFile.typeIdItems[return_type_idx_]}\n" +
                "       parameters_off_ = ${if (parameters_off_ == 0) " 0" else " $typeList}"} )"

    }
}