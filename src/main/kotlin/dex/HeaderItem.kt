package dex

import okio.BufferedSource
import okio.ByteString.Companion.toByteString
import okio.internal.commonToUtf8String


const val ENDIAN_CONSTANT = 0x12345678
const val REVERSE_ENDIAN_CONSTANT = 0x78563412

class HeaderItem {

    var magic: String = ""
    var checksum_: Int = 0
    var signature: String = ""

    var file_size_: Int = 0   // size of entire file
    var header_size_: Int = 0  // offset to start of next section
    var endian_tag_: Int = 0

    var link_size_: Int = 0    // unused
    var link_off_: Int = 0     // unused
    var map_off_: Int = 0   // unused

    var string_ids_size_: Int = 0   // number of StringIds
    var string_ids_off_: Int = 0    // file offset of StringIds array

    var type_ids_size_: Int = 0    // number of TypeIds, we don't support more than 65535
    var type_ids_off_: Int = 0     // file offset of TypeIds array

    var proto_ids_size_: Int = 0     // number of ProtoIds, we don't support more than 65535
    var proto_ids_off_: Int = 0    // file offset of ProtoIds array

    var field_ids_size_: Int = 0     // number of FieldIds
    var field_ids_off_: Int = 0   // file offset of FieldIds array

    var method_ids_size_: Int = 0    // number of MethodIds
    var method_ids_off_: Int = 0     // file offset of MethodIds array

    var class_defs_size_: Int = 0   // number of ClassDefs
    var class_defs_off_: Int = 0     // file offset of ClassDef array

    var data_size_: Int = 0  // size of data section
    var data_off_: Int = 0  // file offset of data section

    override fun toString(): String {
        return """
            
             Header_item(
                    magic:${magic.substring(0, 3)}${magic.substring(4, 7)}
                    checksum_:${checksum_}
                    signature:${signature}
                    
                    file_size_:${file_size_}
                    header_size_:${header_size_}
                    endian_tag_:${endian_tag_.toByteString().hex()}
                    
                    link_size_:${link_size_}
                    link_off_:${link_off_}
                    map_off_:${map_off_}
                    
                    string_ids_size_:${string_ids_size_}
                    string_ids_off_:${string_ids_off_}
                    
                    type_ids_size_:${type_ids_size_}
                    type_ids_off_:${type_ids_off_}
                    
                    proto_ids_size_:${proto_ids_size_}
                    proto_ids_off_:${proto_ids_off_}
                    
                    field_ids_size_:${field_ids_size_}
                    field_ids_off_:${field_ids_off_}
                    
                    method_ids_size_:${method_ids_size_}
                    method_ids_off_:${method_ids_off_}
                    
                    class_defs_size_:${class_defs_size_}
                    class_defs_off_:${class_defs_off_}
                    
                    data_size_:${data_size_}
                    data_off_:${data_off_}
             )
        """.trimIndent()
    }

    companion object {
        @JvmStatic
        fun from(source: BufferedSource): HeaderItem {
            val item = HeaderItem()
            item.magic = source.readByteArray(8).commonToUtf8String()
            item.checksum_ = source.readIntLe()
            item.signature = source.readByteArray(20).toByteString().hex().uppercase()

            item.file_size_ = source.readIntLe()
            item.header_size_ = source.readIntLe()
            item.endian_tag_ = source.readIntLe()

            item.link_size_ = source.readIntLe()
            item.link_off_ = source.readIntLe()
            item.map_off_ = source.readIntLe()

            item.string_ids_size_ = source.readIntLe()
            item.string_ids_off_ = source.readIntLe()

            item.type_ids_size_ = source.readIntLe()
            item.type_ids_off_ = source.readIntLe()

            item.proto_ids_size_ = source.readIntLe()
            item.proto_ids_off_ = source.readIntLe()

            item.field_ids_size_ = source.readIntLe()
            item.field_ids_off_ = source.readIntLe()

            item.method_ids_size_ = source.readIntLe()
            item.method_ids_off_ = source.readIntLe()

            item.class_defs_size_ = source.readIntLe()
            item.class_defs_off_ = source.readIntLe()

            item.data_size_ = source.readIntLe()
            item.data_off_ = source.readIntLe()
            return item
        }
    }
}