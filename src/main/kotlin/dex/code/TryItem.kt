package dex.code

import dex.DexFile
import java.nio.ByteBuffer

class TryItem(val handlers: EncodedCatchHandlerList?, dexFile: DexFile, byteBuffer: ByteBuffer) {
    val start_addr: Int
    val insn_count: Short
    val handler_off: Short

    init {
        start_addr = byteBuffer.int
        insn_count = byteBuffer.short
        handler_off = byteBuffer.short
    }

    override fun toString(): String {
        return "TryItem(start_addr $start_addr insn_count $insn_count " +
                "handler_off #$handler_off ${handlers?.list?.get(handler_off.toInt()) ?: ""} \n" +
                ")"
    }
}