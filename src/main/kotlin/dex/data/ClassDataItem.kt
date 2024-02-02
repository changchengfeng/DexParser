package dex.data

import dex.DexFile
import dex.readULeb128
import dex.toPrint
import java.nio.ByteBuffer

class ClassDataItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val static_fields_size: Int
    val instance_fields_size: Int
    val direct_methods_size: Int
    val virtual_methods_size: Int
    var static_fields: Array<EncodedField>? = null
    var instance_fields: Array<EncodedField>? = null
    var direct_methods: Array<EncodedMethod>? = null
    var virtual_methods: Array<EncodedMethod>? = null

    init {
        static_fields_size = byteBuffer.readULeb128()
        instance_fields_size = byteBuffer.readULeb128()
        direct_methods_size = byteBuffer.readULeb128()
        virtual_methods_size = byteBuffer.readULeb128()
        if (static_fields_size != 0) {
            EncodedField.POSITION = 0
            static_fields = Array(static_fields_size) {
                EncodedField(dexFile, byteBuffer)
            }
        }
        if (instance_fields_size != 0) {
            EncodedField.POSITION = 0
            instance_fields = Array(instance_fields_size) {
                EncodedField(dexFile, byteBuffer)
            }
        }
        if (direct_methods_size != 0) {
            EncodedMethod.POSITION = 0
            direct_methods = Array(direct_methods_size) {
                EncodedMethod(dexFile, byteBuffer)
            }
        }
        if (virtual_methods_size != 0) {
            EncodedMethod.POSITION = 0
            virtual_methods = Array(virtual_methods_size) {
                EncodedMethod(dexFile, byteBuffer)
            }
        }
    }

    override fun toString(): String {
        return "\n######################################################################################################################################\n" +
                "ClassDataItem(\n" +
                "       static_fields = \n${static_fields?.toPrint(2) ?: " ==null "}\n" +
                "       instance_fields = \n${instance_fields?.toPrint(2) ?: " ==null "}\n" +
                "       direct_methods = \n${direct_methods?.toPrint(2) ?: " ==null "}\n" +
                "       virtual_methods = \n${virtual_methods?.toPrint(2) ?: " ==null "}\n" +
                ")" +
                "\n######################################################################################################################################\n"
    }
}