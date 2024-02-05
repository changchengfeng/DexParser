package dex.callsite

import dex.DexFile
import dex.EncodedArrayItem
import java.nio.ByteBuffer

class CallSiteItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val call_site_off: Int
    val encoded_array_item: EncodedArrayItem

    init {
        call_site_off = byteBuffer.int
        byteBuffer.position(call_site_off)
        encoded_array_item =EncodedArrayItem(dexFile,byteBuffer)
    }
}