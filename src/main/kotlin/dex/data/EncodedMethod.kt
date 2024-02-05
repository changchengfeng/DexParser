package dex.data

import dex.DexFile
import dex.code.CodeItem
import dex.readULeb128
import java.nio.ByteBuffer

class EncodedMethod(val dexFile: DexFile, byteBuffer: ByteBuffer) {

    companion object {
        var POSITION = 0
    }

    val method_idx_diff: Int
    val access_flags: Int
    val code_off: Int
    var codeItem: CodeItem? = null

    init {
        method_idx_diff = POSITION + byteBuffer.readULeb128()
        access_flags = byteBuffer.readULeb128()
        code_off = byteBuffer.readULeb128()
        POSITION = method_idx_diff
        val position = byteBuffer.position()
        if (code_off != 0) {
            byteBuffer.position(code_off)
            codeItem = CodeItem(dexFile, byteBuffer)
        }
        byteBuffer.position(position)
    }

    override fun toString(): String {


        return """
EncodedMethod( access_flags $access_flags
       method_idx_diff #$method_idx_diff ${dexFile.methodIdItems[method_idx_diff]}
       codeItem $codeItem )
                """
    }
}