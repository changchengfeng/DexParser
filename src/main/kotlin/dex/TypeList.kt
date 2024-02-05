package dex

import java.nio.ByteBuffer

class TypeList(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val size: Int
    val list: Array<TypeItem>

    init {
        size = byteBuffer.int
        list = Array(size) {
            TypeItem(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "TypeList(${list.toPrint()})"
    }
}