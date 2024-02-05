package dex

import dex.annotations.AnnotationsDirectoryItem
import dex.data.ClassDataItem
import java.nio.ByteBuffer

class ClassDef(val dexFile: DexFile, byteBuffer: ByteBuffer) {

    val class_idx: Int
    val access_flags: Int
    val superclass_idx: Int
    val interfaces_off: Int //
    val source_file_idx: Int
    val annotations_off: Int //
    val class_data_off: Int  //
    val static_values_off: Int //

    var interfacesList: TypeList? = null
    var annotationsDirectoryItem: AnnotationsDirectoryItem? = null
    var classDataItem: ClassDataItem? = null
    var encodedArrayItem: EncodedArrayItem? = null

    init {
        class_idx = byteBuffer.int
        access_flags = byteBuffer.int
        superclass_idx = byteBuffer.int
        interfaces_off = byteBuffer.int
        source_file_idx = byteBuffer.int
        annotations_off = byteBuffer.int
        class_data_off = byteBuffer.int
        static_values_off = byteBuffer.int

        if (interfaces_off != 0) {
            byteBuffer.position(interfaces_off)
            interfacesList = TypeList(dexFile, byteBuffer)
        }
        if (annotations_off != 0) {
            byteBuffer.position(annotations_off)
            annotationsDirectoryItem = AnnotationsDirectoryItem(dexFile, byteBuffer)
        }
        if (class_data_off != 0) {
            byteBuffer.position(class_data_off)
            classDataItem = ClassDataItem(dexFile, byteBuffer)
        }
        if (static_values_off != 0) {
            byteBuffer.position(static_values_off)
            encodedArrayItem = EncodedArrayItem(dexFile, byteBuffer)
        }

    }

    override fun toString(): String {
        return """
            
*****************************************************************************************************************************************************************************"
ClassDef(
    class_idx = #$class_idx  ${dexFile.typeIdItems[class_idx]}
    access_flags #$access_flags
    superclass_idx #${superclass_idx} ${if (superclass_idx >= 0) dexFile.typeIdItems[superclass_idx] else ""}
    source_file_idx #${source_file_idx} ${if (source_file_idx >= 0) dexFile.stringDataItems[source_file_idx] else ""}
    interfacesList = ${interfacesList?.also { } ?: " null"}
    annotationsDirectoryItem ${annotationsDirectoryItem?.also { } ?: " null"}
    classDataItem = ${classDataItem?.also { } ?: " null"}
    static_values = ${encodedArrayItem?.also { } ?: " null"}
)
"*****************************************************************************************************************************************************************************"
            
        """
    }

}