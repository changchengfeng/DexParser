package dex.annotations

import dex.DexFile
import dex.toPrint
import java.nio.ByteBuffer

class AnnotationsDirectoryItem(dexFile: DexFile, byteBuffer: ByteBuffer) {
    val class_annotations_off: Int
    val fields_size: Int
    val annotated_methods_size: Int
    val annotated_parameters_size: Int

    var field_annotations: Array<FieldAnnotation>? = null
    var method_annotations: Array<MethodAnnotation>? = null
    var parameter_annotations: Array<ParameterAnnotation>? = null
    var annotation_set_item: AnnotationSetItem? = null

    init {
        class_annotations_off = byteBuffer.int
        fields_size = byteBuffer.int
        annotated_methods_size = byteBuffer.int
        annotated_parameters_size = byteBuffer.int

        val position = byteBuffer.position()

        if (fields_size != 0) {
            field_annotations = Array(fields_size) {
                byteBuffer.position(position + it * 0x08)
                FieldAnnotation(dexFile, byteBuffer)
            }
        }
        if (annotated_methods_size != 0) {
            method_annotations = Array(annotated_methods_size) {
                byteBuffer.position(position + it * 0x08 + fields_size * 0x08)
                MethodAnnotation(dexFile, byteBuffer)
            }
        }
        if (annotated_parameters_size != 0) {
            parameter_annotations = Array(annotated_parameters_size) {
                byteBuffer.position(position + it * 0x08 + fields_size * 0x08 + annotated_methods_size * 0x08)
                ParameterAnnotation(dexFile, byteBuffer)
            }
        }
        if (class_annotations_off != 0) {
            byteBuffer.position(class_annotations_off)
            annotation_set_item = AnnotationSetItem(dexFile, byteBuffer)
        }
    }

    override fun toString(): String {
        return "AnnotationsDirectoryItem(" +
                (field_annotations?.toPrint() ?: "field_annotations == null\n") +
                (method_annotations?.toPrint() ?: " method_annotations == null\n") +
                (parameter_annotations?.toPrint() ?: " parameter_annotations == null\n") +
                (annotation_set_item?.also { } ?: " annotation_set_item == null\n") +
                ")"
    }
}