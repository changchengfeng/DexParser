package dex

import dex.callsite.CallSiteItem
import dex.callsite.MethodHandleItem
import okio.ByteString.Companion.toByteString
import okio.FileHandle
import okio.buffer
import java.io.PrintWriter
import java.nio.ByteBuffer
import java.nio.ByteOrder

const val NO_INDEX = -1

class DexFile(writer: PrintWriter, fileHandle: FileHandle) {


    val headerItem: HeaderItem
    val mapList: MapList
    val stringDataItems: Array<StringDataItem>
    val typeIdItems: Array<TypeIdItem>
    val protoIdItems: Array<ProtoIdItem>
    val fieldIdItems: Array<FieldIdItem>
    val methodIdItems: Array<MethodIdItem>
    val classDefs: Array<ClassDef>
    var callSiteIds: Array<CallSiteItem>? = null
    var methodHandles: Array<MethodHandleItem>? = null
    var data: ByteArray? = null
    var linkData: ByteArray? = null

    init {

        val bufferedSource = fileHandle.source().buffer()
        headerItem = HeaderItem.from(bufferedSource)

        writer.println(headerItem)
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        val byteArray = fileHandle.source().buffer().readByteArray()
        val byteBuffer = ByteBuffer.wrap(byteArray)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

        byteBuffer.position(headerItem.map_off_)
        mapList = MapList(this, byteBuffer)
        writer.println(mapList)
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")


        stringDataItems = Array(headerItem.string_ids_size_) {
            byteBuffer.position(headerItem.string_ids_off_ + (it * 0x04))
            StringDataItem(byteBuffer)
        }
        writer.println(stringDataItems.toPrint())
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")

        typeIdItems = Array(headerItem.type_ids_size_) {
            byteBuffer.position(headerItem.type_ids_off_ + (it * 0x04))
            TypeIdItem(this, byteBuffer)
        }
        writer.println(typeIdItems.toPrint())
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")

        protoIdItems = Array(headerItem.proto_ids_size_) {
            byteBuffer.position(headerItem.proto_ids_off_ + (it * 0x0c))
            ProtoIdItem(this, byteBuffer)
        }
        writer.println(protoIdItems.toPrint())
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")

        fieldIdItems = Array(headerItem.field_ids_size_) {
            byteBuffer.position(headerItem.field_ids_off_ + (it * 0x08))
            FieldIdItem(this, byteBuffer)
        }

        writer.println(fieldIdItems.toPrint())
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")

        methodIdItems = Array(headerItem.method_ids_size_) {
            byteBuffer.position(headerItem.method_ids_off_ + (it * 0x08))
            MethodIdItem(this, byteBuffer)
        }
        writer.println(methodIdItems.toPrint())
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")

        classDefs = Array(headerItem.class_defs_size_) {
            byteBuffer.position(headerItem.class_defs_off_ + (it * 0x20))
            ClassDef(this, byteBuffer)
        }
        writer.println(classDefs.toPrint())
        writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")

        val callSiteMapItem = mapList.list.find { it.type == TYPE_CODES.TYPE_CALL_SITE_ID_ITEM }
        callSiteMapItem?.let {
            byteBuffer.position(callSiteMapItem.offset)
            callSiteIds = Array(callSiteMapItem.size) {
                CallSiteItem(this@DexFile, byteBuffer)
            }
            writer.println(callSiteIds?.toPrint())
            writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        }

        val methodHandleMapItem = mapList.list.find { it.type == TYPE_CODES.TYPE_METHOD_HANDLE_ITEM }
        methodHandleMapItem?.let {
            byteBuffer.position(methodHandleMapItem.offset)
            methodHandles = Array(methodHandleMapItem.size) {
                MethodHandleItem(this@DexFile, byteBuffer)
            }
            writer.println(methodHandles?.toPrint())
            writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        }

        if (headerItem.data_off_ != 0) {
            byteBuffer.position(headerItem.data_off_)
            data = ByteArray(headerItem.data_size_)
            byteBuffer.get(data)
            writer.println("data_size_ : ")
//            writer.println(data?.toByteString()?.hex())
            writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        }
        if (headerItem.link_off_ != 0) {
            byteBuffer.position(headerItem.link_off_)
            linkData = ByteArray(headerItem.link_size_)
            byteBuffer.get(linkData)
            writer.println("link_size_ : ")
            writer.println(linkData?.toByteString()?.hex())
            writer.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        }
        writer.flush()
        writer.close()
    }
}