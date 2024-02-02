package dex.data

import dex.DexFile
import dex.readULeb128
import java.nio.ByteBuffer

class EncodedField(val dexFile: DexFile, byteBuffer: ByteBuffer) {

    companion object {
        var POSITION = 0
    }

    val field_idx_diff: Int
    val access_flags: Int

    init {
        field_idx_diff = POSITION + byteBuffer.readULeb128()
        access_flags = byteBuffer.readULeb128()
        POSITION = field_idx_diff
    }

    override fun toString(): String {
        return "EncodedField(\n" +
                "       access_flags $access_flags\n" +
                "       field_idx_diff #$field_idx_diff ${dexFile.fieldIdItems[field_idx_diff]}\n" +
                ")"
    }
}