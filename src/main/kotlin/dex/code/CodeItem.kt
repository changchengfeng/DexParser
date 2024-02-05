package dex.code

import dex.DexFile
import dex.toPrint
import dex.toPrintln
import okio.buffer
import okio.source
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

class CodeItem(val dexFile: DexFile, byteBuffer: ByteBuffer) {
    val registers_size: Short
    val ins_size: Short  //the number of words of incoming arguments to the method that this code is for
    val outs_size: Short  // the number of words of outgoing argument space required by this code for method invocation
    val tries_size: Short


    val debug_info_off: Int
    val insns_size: Int

    val insns_: ByteArray
    var tries: Array<TryItem>? = null
    var handlers: EncodedCatchHandlerList? = null

    init {
        registers_size = byteBuffer.short
        ins_size = byteBuffer.short
        outs_size = byteBuffer.short
        tries_size = byteBuffer.short

        debug_info_off = byteBuffer.int
        insns_size = byteBuffer.int
        insns_ = ByteArray(insns_size * 2)
        byteBuffer.get(insns_)
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
        return """
CodeItem(registers_size $registers_size ins_size $ins_size outs_size $outs_size
         insns_size =  $insns_size 
         insns_ = ${ByteArrayInputStream(insns_).source().buffer().toPrintln(dexFile, insns_)} 
         tries_size =  $tries_size ${if (tries_size == null) "" else tries?.toPrint()})           
        """
    }
}