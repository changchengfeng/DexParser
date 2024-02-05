package dex.annotations

import dex.u1

enum class ValueType(val value: u1) {
    VALUE_BYTE(0x00), // signed one-byte integer value (none; must be 0)
    VALUE_SHORT(0x02), // signed two-byte integer value, sign-extended
    VALUE_CHAR(0x03), // unsigned two-byte integer value, zero-extended
    VALUE_INT(0x04), //signed four-byte integer value, sign-extended
    VALUE_LONG(0x06), // signed eight-byte integer value, sign-extended
    VALUE_FLOAT(0x10),
    VALUE_DOUBLE(0x11),
    VALUE_STRING(0x17),
    VALUE_TYPE(0x18),
    VALUE_FIELD(0x19),
    VALUE_METHOD(0x1a),
    VALUE_ENUM(0x1b),
    VALUE_ARRAY(0x1c), // 	(none; must be 0)
    VALUE_ANNOTATION(0x1d), // (none; must be 0)
    VALUE_NULL(0x1e), // null reference value (none; must be 0)
    VALUE_BOOLEAN(0x1f); // boolean (0…1) one-bit value; 0 for false and 1 for true. The bit is represented in the value_arg.

    companion object {
        fun getValueTypeBy(value: u1) = values().find { it.value == value }!!
    }
}