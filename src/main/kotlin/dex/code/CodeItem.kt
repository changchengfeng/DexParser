package dex.code

import dex.DexFile
import dex.toPrint
import dex.toPrintln
import java.nio.ByteBuffer

class CodeItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val registers_size: Short
    val ins_size: Short  //the number of words of incoming arguments to the method that this code is for
    val outs_size: Short  // the number of words of outgoing argument space required by this code for method invocation
    val tries_size: Short


    val debug_info_off: Int
    val insns_size: Int

    val insns: Array<ByteArray>
    var tries: Array<TryItem>? = null
    var handlers: EncodedCatchHandlerList? = null

    init {
        registers_size = byteBuffer.short
        ins_size = byteBuffer.short
        outs_size = byteBuffer.short
        tries_size = byteBuffer.short

        debug_info_off = byteBuffer.int
        insns_size = byteBuffer.int
        insns = Array(insns_size) {
            ByteArray(2) {
                byteBuffer.get()
            }
//            var short = byteBuffer.short
//            short = ((short.toInt() shr 8 and 0xFF) or (short.toInt() shl 8 and 0xFF00) and 0xFFFF).toShort()
//            short
        }
        if (tries_size.toInt() != 0) {
            if (insns_size % 2 != 0) {
                byteBuffer.short
            }
            tries = Array(tries_size.toInt()) {
                TryItem(handlers, dexFile, byteBuffer)
            }
            handlers = EncodedCatchHandlerList(dexFile, byteBuffer)
        }

    }

    override fun toString(): String {
        return "CodeItem(registers_size $registers_size ins_size $ins_size outs_size $outs_size \n" +
                "insns_size =  $insns_size \n${insns.toPrintln()}  \n" +
                "tries_size =  $tries_size ${if (tries_size == null) "" else tries}  \n" +
                ")"
    }
}