package dex

enum class TYPE_CODES(val value: u2) {

    TYPE_HEADER_ITEM(0x0000),
    TYPE_STRING_ID_ITEM(0x0001),
    TYPE_TYPE_ID_ITEM(0x0002),
    TYPE_PROTO_ID_ITEM(0x0003),
    TYPE_FIELD_ID_ITEM(0x0004),
    TYPE_METHOD_ID_ITEM(0x0005),
    TYPE_CLASS_DEF_ITEM(0x0006),
    TYPE_CALL_SITE_ID_ITEM(0x0007),
    TYPE_METHOD_HANDLE_ITEM(0x0008),

    TYPE_MAP_LIST(0x1000),
    TYPE_TYPE_LIST(0x1001),
    TYPE_ANNOTATION_SET_REF_LIST(0x1002),
    TYPE_ANNOTATION_SET_ITEM(0x1003),

    TYPE_CLASS_DATA_ITEM(0x2000),
    TYPE_CODE_ITEM(0x2001),
    TYPE_STRING_DATA_ITEM(0x2002),
    TYPE_DEBUG_INFO_ITEM(0x2003),
    TYPE_ANNOTATION_ITEM(0x2004),
    TYPE_ENCODED_ARRAY_ITEM(0x2005),
    TYPE_ANNOTATIONS_DIRECTORY_ITEM(0x2006),
    TYPE_HIDDENAPI_CLASS_DATA_ITEM(0xF000.toShort());

    companion object {
        fun getTypeCodeByValue(value: u2) = values().find { it.value == value }!!

    }
}