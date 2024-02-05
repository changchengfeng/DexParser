package dex.callsite

import dex.DexFile
import java.nio.ByteBuffer

class MethodHandleItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val method_handle_type: Short
    val unused1: Short
    val field_or_method_id: Short
    val unused2: Short

    init {
        method_handle_type = byteBuffer.short
        unused1 = byteBuffer.short
        field_or_method_id = byteBuffer.short
        unused2 = byteBuffer.short
    }
}