package dex.callsite

import dex.u2

enum class MethodhandleTypeCodes(val code: u2) {

    METHOD_HANDLE_TYPE_STATIC_PUT(0x00),// Method handle is a static field setter (accessor)
    METHOD_HANDLE_TYPE_STATIC_GET(0x01),// Method handle is a static field getter (accessor)
    METHOD_HANDLE_TYPE_INSTANCE_PUT(0x02), //    Method handle is an instance field setter (accessor)
    METHOD_HANDLE_TYPE_INSTANCE_GET(0x03), //   Method handle is an instance field getter (accessor)
    METHOD_HANDLE_TYPE_INVOKE_STATIC(0x04), //    Method handle is a static method invoker
    METHOD_HANDLE_TYPE_INVOKE_INSTANCE(0x05), //  Method handle is an instance method invoker
    METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR(0x06),  // Method handle is a constructor method invoker
    METHOD_HANDLE_TYPE_INVOKE_DIRECT(0x07), //  Method handle is a direct method invoker
    METHOD_HANDLE_TYPE_INVOKE_INTERFACE(0x08); // Method handle is an     interface method invoker

    companion object {
        fun getMethodhandleTypeCodeByValue(value: u2) = MethodhandleTypeCodes.values().find { it.code == value }!!
    }
}