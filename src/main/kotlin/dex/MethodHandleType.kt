package dex

import dex.u2

enum class MethodHandleType (val value :u2){
    METHOD_HANDLE_TYPE_STATIC_PUT         ( 0x0000),  // a setter for a given static field.
    METHOD_HANDLE_TYPE_STATIC_GET         ( 0x0001),  // a getter for a given static field.
    METHOD_HANDLE_TYPE_INSTANCE_PUT       ( 0x0002),  // a setter for a given instance field.
    METHOD_HANDLE_TYPE_INSTANCE_GET       ( 0x0003),  // a getter for a given instance field.
    METHOD_HANDLE_TYPE_INVOKE_STATIC      ( 0x0004),  // an invoker for a given static method.
    METHOD_HANDLE_TYPE_INVOKE_INSTANCE    ( 0x0005),  // invoke_instance : an invoker for a given instance method. This
    // can be any non-static method on any class (or interface) except
    // for “<init>”.
    METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR ( 0x0006),  // an invoker for a given constructor.
    METHOD_HANDLE_TYPE_INVOKE_DIRECT ( 0x0007),  // Method handle is a direct method invoker.
    METHOD_HANDLE_TYPE_INVOKE_INTERFACE ( 0x0008);  // Method handle is an interface method invoker.
}