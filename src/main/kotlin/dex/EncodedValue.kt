package dex

import dex.annotations.EncodedAnnotation
import dex.annotations.ValueType
import java.nio.ByteBuffer
import kotlin.experimental.and

class EncodedValue(dexFile: DexFile, byteBuffer: ByteBuffer) {
    protected val value_type: ValueType  // field_ids
    protected var value = ""

    init {

        val byte = byteBuffer.get()
        var size = ((byte.toInt() shr 5) and 0x07) + 1
        var byteArray = ByteArray(0)
//        println("EncodedValue  byte  0x${byte.toString(16)}  0x${(byte and 0x1F).toString(16)} size $size")
        value_type = ValueType.getValueTypeBy(byte and 0x1F)
        if (value_type != ValueType.VALUE_ARRAY
            && value_type != ValueType.VALUE_ANNOTATION
            && value_type != ValueType.VALUE_NULL
            && value_type != ValueType.VALUE_BOOLEAN
        ) {
            byteArray = ByteArray(size) {
                byteBuffer.get()
            }
        }
        when (value_type) {
            ValueType.VALUE_BYTE -> {
                value = byteArray[0].toString()
            }
            ValueType.VALUE_SHORT -> {
                var sum: Short = 0
                for (it in 0 until size) {
                    sum = (sum + (byteArray[it].toInt() and 0xFF shl (it * 8))).toShort()
                }
                value = sum.toString()
            }
            ValueType.VALUE_CHAR -> {
                var sum: Short = 0
                for (it in 0 until size) {
                    sum = (sum + (byteArray[it].toInt() and 0xFF shl (it * 8))).toShort()
                }
                value = sum.toInt().toChar().toString()
            }

            ValueType.VALUE_INT -> {
                var sum = 0
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = sum.toString()
            }
            ValueType.VALUE_LONG -> {
                var sum = 0L
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = sum.toString()
            }
            ValueType.VALUE_FLOAT -> {
                var sum = 0
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = Float.Companion.fromBits(sum).toString()
            }
            ValueType.VALUE_DOUBLE -> {
                var sum = 0L
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = Double.Companion.fromBits(sum).toString()
            }
            ValueType.VALUE_STRING -> {
                var sum = 0
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF and 0xFF shl (it * 8))
                }
                value = "#${sum} ${dexFile.stringDataItems[sum]}"
            }
            ValueType.VALUE_TYPE -> {
                var sum = 0
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = "#${sum} ${dexFile.typeIdItems[sum]}"
            }
            ValueType.VALUE_FIELD -> {
                var sum = 0
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = "#${sum} ${dexFile.fieldIdItems[sum]}"
            }
            ValueType.VALUE_METHOD -> {
                var sum = 0
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = "#${sum} ${dexFile.methodIdItems[sum]}"
            }
            ValueType.VALUE_ENUM -> {
                var sum = 0
                for (it in 0 until size) {
                    sum = sum + (byteArray[it].toInt() and 0xFF shl (it * 8))
                }
                value = "#${sum} ${dexFile.fieldIdItems[sum]}"
            }
            ValueType.VALUE_ARRAY -> {
                value = EncodedArray(dexFile, byteBuffer).toString()
            }
            ValueType.VALUE_ANNOTATION -> {
                value = EncodedAnnotation(dexFile, byteBuffer).toString()
            }
            ValueType.VALUE_NULL -> {
                value = "null"
            }
            ValueType.VALUE_BOOLEAN -> {
                value = if (size - 1 != 0) "true" else "false"
            }
        }
    }

    override fun toString(): String {
        return "EncodedValue(value_type $value_type value $value)"
    }
}