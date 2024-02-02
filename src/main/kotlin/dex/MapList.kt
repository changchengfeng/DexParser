package dex

import java.nio.ByteBuffer

class MapList(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val size: Int
    val list: Array<MapItem>

    init {
        size = byteBuffer.int
        list = Array(size) {
            MapItem(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "MapList{ list = \n${list.toPrint()}   }"
    }

    fun getTypeItem(typeCode: TYPE_CODES) =
        list.filter { it.type == typeCode }.first()

}