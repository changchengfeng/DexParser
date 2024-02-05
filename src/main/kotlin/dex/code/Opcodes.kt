package dex.code

enum class Opcodes(val value: UByte, val type: OpcodeType, val describe: String) {


    nop(0x0u, OpcodeType.T10x, "Waste cycles"),

    /*
    * A: destination register (4 bits)
      B: source register (4 bits)
      Move the contents of one non-object register to another.
    * */
    move(0x1u, OpcodeType.T12x, "move vA, vB"),

    /*Move the contents of one non-object register to another.
        A: destination register (8 bits)
        B: source register (16 bits)
    * */
    moveFrom16(0x2u, OpcodeType.T22x, "move/from16 vAA, vBBBB"),

    /*
    *   A: destination register (16 bits)
        B: source register (16 bits)
    * */
    move16(0x3u, OpcodeType.T32x, "move/16 vAAAA, vBBBB"),

    /* Move the contents of one register-pair to another.
    * A: destination register pair (4 bits)
    * B: source register pair (4 bits)
    * */
    moveWide(0x4u, OpcodeType.T12x, "move-wide vA, vB"),

    /*
    * A: destination register pair (8 bits)
    * B: source register pair (16 bits)
    * */
    moveWideFrom16(0x05u, OpcodeType.T22x, "move-wide/from16 vAA, vBBBB"),

    /*
      * A: destination register pair (16 bits)
      * B: source register pair (16 bits)
      * */
    moveWide16(0x06u, OpcodeType.T32x, "move-wide/16 vAAAA, vBBBB"),

    /* Move the contents of one object-bearing register to another.
    *   A: destination register (4 bits)
    *   B: source register (4 bits)
    * */
    moveObject(0x07u, OpcodeType.T12x, "move-object vA, vB"),

    /*
     *  A: destination register (8 bits)
     *  B: source register (16 bits)
    * */
    moveObjectFrom16(0x08u, OpcodeType.T22x, "move-object/from16 vAA, vBBBB"),

    /*
      * A: destination register (16 bits)
      * B: source register (16 bits)
      * */
    moveObject16(0x09u, OpcodeType.T32x, "move-object/16 vAAAA, vBBBB"),

    /*
     * A: destination register (8 bits)
     * Move the single-word non-object result of the most recent invoke-kind into the indicated register.
     * This must be done as the instruction immediately after an invoke-kind whose (single-word, non-object)
     * result is not to be ignored; anywhere else is invalid.
      * */
    moveResult(0x0au, OpcodeType.T11x, "move-result vAA"),

    /*
    * Move the double-word result of the most recent invoke-kind into the indicated register pair.
    * This must be done as the instruction immediately after an invoke-kind whose (double-word)
    * result is not to be ignored; anywhere else is invalid.
    * */
    moveWideResult(0x0bu, OpcodeType.T11x, "move-result-wide vAA"),

    /*
    * Move the object result of the most recent invoke-kind into the indicated register.
    * This must be done as the instruction immediately after an invoke-kind or filled-new-array whose (object)
    * result is not to be ignored; anywhere else is invalid.
    * */
    moveObjectResult(0x0cu, OpcodeType.T11x, "move-result-object vAA"),

    /*
    * Save a just-caught exception into the given register.
    * This must be the first instruction of any exception handler whose
    * caught exception is not to be ignored, and this instruction must only ever occur as the first instruction of an exception handler;
    * anywhere else is invalid
    * */
    moveException(0x0du, OpcodeType.T11x, "move-exception vAA"),


    /*
    *   Return from a void method.
    * */
    returnVoid(0x0eu, OpcodeType.T10x, "return-void"),

    /*
     *   A: return value register (8 bits)
    *   Return from a single-width (32-bit) non-object value-returning method.
    * */
    return32(0x0fu, OpcodeType.T11x, "return vAA"),

    /*
    *   Return from a double-width (64-bit) value-returning method.
    * */
    returnWide(0x10u, OpcodeType.T11x, "return-wide vAA"),

    /*
    *   Return from an object-returning method.
    * */
    returnObject(0x11u, OpcodeType.T11x, "return-object vAA"),

    /*  Move the given literal value (sign-extended to 32 bits) into the specified register.
    *   A: destination register (4 bits)
    *   B: signed int (4 bits)
    * */
    const4(0x12u, OpcodeType.T11n, "const/4 vA, #+B"),

    /*  Move the given literal value (sign-extended to 32 bits) into the specified register.
    *  A: destination register (8 bits)
    *  B: signed int (16 bits)
    * */
    const16(0x13u, OpcodeType.T21s, "const/16 vAA, #+BBBB"),

    /*  Move the given literal value into the specified register.
    *  A: destination register (8 bits)
    *  B: arbitrary 32-bit constant
    * */
    const32(0x14u, OpcodeType.T31i, "const vAA, #+BBBBBBBB"),

    /*  Move the given literal value (right-zero-extended to 32 bits) into the specified register.
    *  A: destination register (8 bits)
    *  B: signed int (16 bits)
    * */
    constHigh16(0x15u, OpcodeType.T21h_1, "const/high16 vAA, #+BBBB0000"),

    /*  Move the given literal value (sign-extended to 64 bits) into the specified register-pair.
    *  A: destination register (8 bits)
    *  B: signed int (16 bits)
    * */
    constWide16(0x16u, OpcodeType.T21s, "const-wide/16 vAA, #+BBBB"),

    /* Move the given literal value (sign-extended to 64 bits) into the specified register-pair.
    *  A: destination register (8 bits)
       B: signed int (32 bits)
    * */
    constWide32(0x17u, OpcodeType.T31i, "const-wide/32 vAA, #+BBBBBBBB"),

    /* Move the given literal value into the specified register-pair.
    * A: destination register (8 bits)
      B: arbitrary double-width (64-bit) constant
    * */
    constWide(0x18u, OpcodeType.T51l, "const-wide vAA, #+BBBBBBBBBBBBBBBB"),

    /* Move the given literal value (right-zero-extended to 64 bits) into the specified register-pair.
     *  A: destination register (8 bits)
     *  B: signed int (16 bits)
    * */
    constWideHight16(0x19u, OpcodeType.T21h_2, "const-wide/high16 vAA, #+BBBB000000000000"),

    /* Move a reference to the string specified by the given index into the specified register.
     *  A: destination register (8 bits)
        B: string index
    * */
    constString(0x1au, OpcodeType.T21c_string, "const-string vAA, string@BBBB"),

    /* Move a reference to the string specified by the given index into the specified register.
        A: destination register (8 bits)
        B: string index
    * */
    constStringJumbo(0x1bu, OpcodeType.T31c, "const-string/jumbo vAA, string@BBBBBBBB"),

    /* Move a reference to the class specified by the given index into the specified register.
       In the case where the indicated type is primitive, this will store a reference to the primitive type's degenerate class.
        A: destination register (8 bits)
        B: type index
    * */
    constClass(0x1cu, OpcodeType.T21c_field, "const-class vAA, type@BBBB"),


    /* Acquire the monitor for the indicated object.
      A: reference-bearing register (8 bits)
    * */
    monitorEnter(0x1du, OpcodeType.T11x, "monitor-enter vAA"),

    /* Release the monitor for the indicated object.
       A: reference-bearing register (8 bits)

       Note: If this instruction needs to throw an exception, it must do so as if the pc has already advanced past the instruction.
       It may be useful to think of this as the instruction successfully executing (in a sense),
       and the exception getting thrown after the instruction but before the next one gets a chance to run.
       This definition makes it possible for a method to use a monitor cleanup catch-all (e.g., finally) block as the monitor cleanup for that block itself,
        as a way to handle the arbitrary exceptions that might get thrown due to the historical implementation of Thread.stop(), while still managing to have proper monitor hygiene.
    * */
    monitorExit(0x1eu, OpcodeType.T11x, "monitor-exit vAA"),

    /*
    *  Throw a ClassCastException if the reference in the given register cannot be cast to the indicated type.
    * A: reference-bearing register (8 bits)
      B: type index (16 bits)

      Note: Since A must always be a reference (and not a primitive value), this will necessarily fail at runtime (that is, it will throw an exception) if B refers to a primitive type.
    * */
    checkCast(0x1fu, OpcodeType.T21c_type, "check-cast vAA, type@BBBB"),

    /* Store in the given destination register 1 if the indicated reference is an instance of the given type, or 0 if not.
    * A: destination register (4 bits)
    * B: reference-bearing register (4 bits)
    * C: type index (16 bits)
    * */
    instanceOf(0x20u, OpcodeType.T22c_type, "instance-of vA, vB, type@CCCC"),

    /*
    * Store in the given destination register the length of the indicated array, in entries
    *  A: destination register (4 bits)
    *  B: array reference-bearing register (4 bits)
    * */
    arrayLength(0x21u, OpcodeType.T12x, "array-length vA, vB"),

    /*
    * Construct a new instance of the indicated type, storing a reference to it in the destination. The type must refer to a non-array class.
    *   A: destination register (8 bits)
    *   B: type index
    * */
    newInstance(0x22u, OpcodeType.T21c_type, "new-instance vAA, type@BBBB"),

    /*
    * Construct a new array of the indicated type and size. The type must be an array type.
    * A: destination register (4 bits)
      B: size register
      C: type index
    * */
    newArray(0x23u, OpcodeType.T22c_type, "new-array vA, vB, type@CCCC"),

    /* Construct an array of the given type and size, filling it with the supplied contents. The type must be an array type. The array's contents must be single-word (that is, no arrays of long or double, but reference types are acceptable). The constructed instance is stored as a "result" in the same way that the method invocation instructions store their results, so the constructed instance must be moved to a register with an immediately subsequent move-result-object instruction (if it is to be used).
    *   A: array size and argument word count (4 bits)
        B: type index (16 bits)
        C…G: argument registers (4 bits each)
    * */
    filledNewArray(0x24u, OpcodeType.T35c, "filled-new-array {vC, vD, vE, vF, vG}, type@BBBB"),

    /* Construct an array of the given type and size, filling it with the supplied contents. Clarifications and restrictions are the same as filled-new-array, described above.
    *   A: array size and argument word count (8 bits)
        B: type index (16 bits)
        C: first argument register (16 bits)
        N = A + C - 1
    * */
    filledNewArrayRange(0x25u, OpcodeType.T3rc, "filled-new-array/range {vCCCC .. vNNNN}, type@BBBB"),

    /*
    * Fill the given array with the indicated data. The reference must be to an array of primitives, and the data table must match it in type and must contain no more elements than will fit in the array. That is, the array may be larger than the table, and if so, only the initial elements of the array are set, leaving the remainder alone.
    * A: array reference (8 bits)
      B: signed "branch" offset to table data pseudo-instruction (32 bits)
    * */
    fillArrayData(
        0x26u,
        OpcodeType.T31t,
        "fill-array-data vAA, +BBBBBBBB (with supplemental data as specified below in fill-array-data-payload Format)"
    ),

    /*Throw the indicated exception.
    * A: exception-bearing register (8 bits)
    * */
    throwV(
        0x27u,
        OpcodeType.T11x,
        "throw vAA"
    ),


    /* Unconditionally jump to the indicated instruction.
        A: signed branch offset (8 bits)
    * */
    goto(
        0x28u,
        OpcodeType.T10t,
        "goto +AA"
    ),

    /*
    *  Unconditionally jump to the indicated instruction.
    *  A: signed branch offset (16 bits)
    * */

    goto16(
        0x29u,
        OpcodeType.T20t,
        "goto/16 +AAAA"
    ),

    /*
    * Unconditionally jump to the indicated instruction.
    * A: signed branch offset (32 bits)
    * */
    goto32(
        0x2au,
        OpcodeType.T30t,
        "goto/32 +AAAAAAAA"
    ),

    /*Jump to a new instruction based on the value in the given register, using a table of offsets corresponding to each value in a particular integral range, or fall through to the next instruction if there is no match.
    * A: register to test
      B: signed "branch" offset to table data pseudo-instruction (32 bits)
    *
    * */
    packedSwitch(
        0x2bu,
        OpcodeType.T31t,
        "packed-switch vAA, +BBBBBBBB (with supplemental data as specified below in packed-switch-payload Format)"
    ),
    /*
    * Jump to a new instruction based on the value in the given register, using an ordered table of value-offset pairs, or fall through to the next instruction if there is no match.
    *
    *  A: register to test
       B: signed "branch" offset to table data pseudo-instruction (32 bits)
    * */

    sparseSwitch(
        0x2cu,
        OpcodeType.T31t,
        "sparse-switch vAA, +BBBBBBBB (with supplemental data as specified below in sparse-switch-payload Format)"
    ),

    /*Perform the indicated floating point or long comparison, setting a to 0 if b == c, 1 if b > c, or -1 if b < c. The "bias" listed for the floating point operations indicates how NaN comparisons are treated: "gt bias" instructions return 1 for NaN comparisons, and "lt bias" instructions return -1.
      For example, to check to see if floating point x < y it is advisable to use cmpg-float; a result of -1 indicates that the test was true, and the other values indicate it was false either due to a valid comparison or because one of the values was NaN.
    * A: destination register (8 bits)
      B: first source register or pair
      C: second source register or pair
    *
    * */
    cmplFloat(
        0x2du,
        OpcodeType.T23x,
        "cmpl-float (lt bias) cmpkind vAA, vBB, vCC"
    ),
    cmpgFloat(
        0x2eu,
        OpcodeType.T23x,
        "cmpg-float (gt bias) cmpkind vAA, vBB, vCC"
    ),
    cmplDouble(
        0x2fu,
        OpcodeType.T23x,
        "cmpg-float (gt bias) cmpkind vAA, vBB, vCC"
    ),
    cmpgDouble(
        0x30u,
        OpcodeType.T23x,
        "cmpg-double (gt bias) cmpkind vAA, vBB, vCC"
    ),
    cmpLong(
        0x31u,
        OpcodeType.T23x,
        "cmp-long cmpkind vAA, vBB, vCC"
    ),

    /*
    * Branch to the given destination if the given two registers' values compare as specified.
    * A: first register to test (4 bits)
      B: second register to test (4 bits)
      C: signed branch offset (16 bits)
      *
      *
    * */
    /*
    *  if-eq（If Equal）:
       格式: if-eq vA, vB, :label
       功能: 如果寄存器vA中的值等于寄存器vB中的值，则跳转到:label标签处。
    * */
    ifEq(
        0x32u,
        OpcodeType.T22t,
        "if-eq if-test vA, vB, +CCCC"
    ),

    /*
    *   if-ne（If Not Equal）:
        格式: if-ne vA, vB, :label
        功能: 如果寄存器vA中的值不等于寄存器vB中的值，则跳转到:label标签处。
    * */
    ifNe(
        0x33u,
        OpcodeType.T22t,
        "if-ne if-test vA, vB, +CCCC"
    ),

    /*
    *  if-lt（If Less Than）:
       格式: if-lt vA, vB, :label
       功能: 如果寄存器vA中的值小于寄存器vB中的值，则跳转到:label标签处。
    * */
    ifLt(
        0x34u,
        OpcodeType.T22t,
        "if-lt if-test vA, vB, +CCCC"
    ),

    /*
    *  if-ge（If Greater or Equal）:
        格式: if-ge vA, vB, :label
        功能: 如果寄存器vA中的值大于或等于寄存器vB中的值，则跳转到:label标签处。
    * */
    ifGe(
        0x35u,
        OpcodeType.T22t,
        "if-ge if-test vA, vB, +CCCC"
    ),

    /*
    * if-gt（If Greater Than）:
      格式: if-gt vA, vB, :label
      功能: 如果寄存器vA中的值大于寄存器vB中的值，则跳转到:label标签处。
    * */
    ifGt(
        0x36u,
        OpcodeType.T22t,
        "if-gt if-test vA, vB, +CCCC"
    ),

    /*
    *  if-le（If Less or Equal）:
        格式: if-le vA, vB, :label
        功能: 如果寄存器vA中的值小于或等于寄存器vB中的值，则跳转到:label标签处。
    *
    * */
    ifLe(
        0x37u,
        OpcodeType.T22t,
        "if-le if-test vA, vB, +CCCC"
    ),

    /*
    *  Branch to the given destination if the given register's value compares with 0 as specified.
    *
    *  A: register to test (8 bits)
       B: signed branch offset (16 bits)
    * */
    // （If Equal to Zero）
    ifEqz(
        0x38u,
        OpcodeType.T21t,
        "if-eqz if-testz vAA, +BBBB"
    ),

    // （If Not Equal to Zero）
    ifNez(
        0x39u,
        OpcodeType.T21t,
        "if-nez if-testz vAA, +BBBB"
    ),

    // （If Less Than Zero）
    ifLtz(
        0x3au,
        OpcodeType.T21t,
        "if-ltz if-testz vAA, +BBBB"
    ),

    // If Greater or Equal to Zero
    ifGez(
        0x3bu,
        OpcodeType.T21t,
        "if-Gez if-testz vAA, +BBBB"
    ),

    // If Greater Than Zero
    ifGtz(
        0x3cu,
        OpcodeType.T21t,
        "if-gtz if-testz vAA, +BBBB"
    ),

    // If Less or Equal to Zero
    ifLez(
        0x3du,
        OpcodeType.T21t,
        "if-lez if-testz vAA, +BBBB"
    ),

    // 3e..43 10x	(unused)	 	(unused)
    /*
    *  Perform the identified array operation at the identified index of the given array, loading or storing into the value register.
    *   A: value register or pair; may be source or dest (8 bits)
        B: array register (8 bits)
        C: index register (8 bits)
    * */

    /*
    * 格式：aget <dest>, <array>, <index>
      功能：从指定索引（index）处的数组中读取元素，并将其值存储到目标寄存器 <dest> 中。
    * */
    aGet(
        0x44u,
        OpcodeType.T23x,
        "aget arrayop vAA, vBB, vCC"
    ),

    /*
    * 格式：aget-wide <dest1>, <dest2>, <array>, <index>
      功能：从指定索引处的长整数数组中读取元素，并将其值存储到两个目标寄存器 <dest1> 和 <dest2> 中。
    * */
    aGetWide(
        0x45u,
        OpcodeType.T23x,
        "aget-wide arrayop vAA, vBB, vCC"
    ),

    /*
    *  格式：aget-object <dest>, <array>, <index>
       功能：从指定索引处的对象数组中读取元素，并将其引用存储到目标寄存器 <dest> 中。
    * */
    aGetObject(
        0x46u,
        OpcodeType.T23x,
        "aget-object arrayop vAA, vBB, vCC"
    ),

    /*
    *  格式：aget-boolean <dest>, <array>, <index>
       功能：从指定索引处的布尔数组中读取元素，并将其值存储到目标寄存器 <dest> 中。
    * */
    aGetBoolean(
        0x47u,
        OpcodeType.T23x,
        "aget-boolean arrayop vAA, vBB, vCC"
    ),

    /*
    *  格式：aget-byte <dest>, <array>, <index>
       功能：从指定索引处的字节数组中读取元素，并将其值存储到目标寄存器 <dest> 中。
    * */
    aGetByte(
        0x48u,
        OpcodeType.T23x,
        "aget-byte arrayop vAA, vBB, vCC"
    ),

    /* 格式：aget-char <dest>, <array>, <index>
       功能：从指定索引处的字符数组中读取元素，并将其值存储到目标寄存器 <dest> 中。
    *
    * */
    aGetChar(
        0x49u,
        OpcodeType.T23x,
        "aget-char arrayop vAA, vBB, vCC"
    ),

    /*
    *  格式：aget-short <dest>, <array>, <index>
       功能：从指定索引处的短整数数组中读取元素，并将其值存储到目标寄存器 <dest> 中。
    * */
    aGetShort(
        0x4au,
        OpcodeType.T23x,
        "aget-short arrayop vAA, vBB, vCC"
    ),

    /*
    *   格式：aput <src>, <array>, <index>
        功能：将源寄存器 <src> 中的值写入到指定索引处的数组中。
    * */
    aPut(
        0x4bu,
        OpcodeType.T23x,
        "aput arrayop vAA, vBB, vCC"
    ),

    /*
    *  格式：aput-wide <src1>, <src2>, <array>, <index>
       功能：将两个源寄存器 <src1> 和 <src2> 中的值写入到指定索引处的长整数数组中。
    * */
    aPutWide(
        0x4cu,
        OpcodeType.T23x,
        "aput-wide arrayop vAA, vBB, vCC"
    ),

    /*
      格式：aput-object <src>, <array>, <index>
      功能：将源寄存器 <src> 中的引用写入到指定索引处的对象数组中
    * */
    aPutObject(
        0x4du,
        OpcodeType.T23x,
        "aget-object arrayop vAA, vBB, vCC"
    ),

    /*
    * 格式：aput-boolean <src>, <array>, <index>
      功能：将源寄存器 <src> 中的布尔值写入到指定索引处的布尔数组中。
    * */
    aPutBoolean(
        0x4eu,
        OpcodeType.T23x,
        "aput-boolean arrayop vAA, vBB, vCC"
    ),

    /*
    * 格式：aput-byte <src>, <array>, <index>
      功能：将源寄存器 <src> 中的字节值写入到指定索引处的字节数组中。
    * */
    aPutByte(
        0x4fu,
        OpcodeType.T23x,
        "aput-byte arrayop vAA, vBB, vCC"
    ),

    /*
    * 格式：aput-char <src>, <array>, <index>
      功能：将源寄存器 <src> 中的字符值写入到指定索引处的字符数组中。
    * */
    aPutChar(
        0x50u,
        OpcodeType.T23x,
        "aput-char arrayop vAA, vBB, vCC"
    ),

    /*
    *  格式：aput-short <src>, <array>, <index>
       功能：将源寄存器 <src> 中的短整数值写入到指定索引处的短整数数组中。
    * */
    aPutShort(
        0x51u,
        OpcodeType.T23x,
        "aput-short arrayop vAA, vBB, vCC"
    ),

    /*
    *  Perform the identified object instance field operation with the identified field, loading or storing into the value register.
    *  A: value register or pair; may be source or dest (4 bits)
       B: object register (4 bits)
       C: instance field reference index (16 bits)
    * */

    /*
    * 格式：iget <dest>, <object>, <field>
      功能：从指定对象 (<object>) 的实例字段 (<field>) 中读取值，并将其存储到目标寄存器 <dest> 中。
    * */
    iGet(
        0x52u,
        OpcodeType.T22c_field,
        "iget iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iget-wide <dest1>, <dest2>, <object>, <field>
       功能：从指定对象的实例字段中读取长整数值，并将其存储到两个目标寄存器 <dest1> 和 <dest2> 中。
    * */
    iGetWide(
        0x53u,
        OpcodeType.T22c_field,
        "iget-wide iinstanceop vA, vB, field@CCCC"
    ),

    /*
    * 格式：iget-object <dest>, <object>, <field>
      功能：从指定对象的实例字段中读取对象引用，并将其存储到目标寄存器 <dest> 中。
    * */
    iGetObject(
        0x54u,
        OpcodeType.T22c_field,
        "iget-object iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iget-boolean <dest>, <object>, <field>
       功能：从指定对象的实例字段中读取布尔值，并将其存储到目标寄存器 <dest> 中。
    * */
    iGetBoolean(
        0x55u,
        OpcodeType.T22c_field,
        "iget-boolean iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iget-byte <dest>, <object>, <field>
       功能：从指定对象的实例字段中读取字节值，并将其存储到目标寄存器 <dest> 中。
    * */
    iGetByte(
        0x56u,
        OpcodeType.T22c_field,
        "iget-byte iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *   格式：iget-char <dest>, <object>, <field>
        功能：从指定对象的实例字段中读取字符值，并将其存储到目标寄存器 <dest> 中。
    * */
    iGetChar(
        0x57u,
        OpcodeType.T22c_field,
        "iget-Char iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iget-short <dest>, <object>, <field>
       功能：从指定对象的实例字段中读取短整数值，并将其存储到目标寄存器 <dest> 中。
    * */
    iGetShort(
        0x58u,
        OpcodeType.T22c_field,
        "iget-Short iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iput <src>, <object>, <field>
       功能：将源寄存器 <src> 的值写入到指定对象 (<object>) 的实例字段 (<field>) 中。
    * */
    iPut(
        0x59u,
        OpcodeType.T22c_field,
        "iput iinstanceop vA, vB, field@CCCC"
    ),

    /*
    * 格式：iput-wide <src1>, <src2>, <object>, <field>
      功能：将两个源寄存器 <src1> 和 <src2> 的值写入到指定对象的实例字段中。
    * */
    iputWide(
        0x5au,
        OpcodeType.T22c_field,
        "iput-wide iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iput-object <src>, <object>, <field>
       功能：将源寄存器 <src> 中的对象引用写入到指定对象的实例字段中。
    * */
    iputObject(
        0x5bu,
        OpcodeType.T22c_field,
        "iput-object iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iput-boolean <src>, <object>, <field>
       功能：将源寄存器 <src> 中的布尔值写入到指定对象的实例字段中。
    *
    * */
    iputBoolean(
        0x5cu,
        OpcodeType.T22c_field,
        "iput-boolean iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iput-byte <src>, <object>, <field>
       功能：将源寄存器 <src> 中的字节值写入到指定对象的实例字段中。
    * */
    iputByte(
        0x5du,
        OpcodeType.T22c_field,
        "iput-byte iinstanceop vA, vB, field@CCCC"
    ),

    /*
    * 格式：iput-char <src>, <object>, <field>
      功能：将源寄存器 <src> 中的字符值写入到指定对象的实例字段中。
    * */
    iputChar(
        0x5eu,
        OpcodeType.T22c_field,
        "iput-char iinstanceop vA, vB, field@CCCC"
    ),

    /*
    *  格式：iput-short <src>, <object>, <field>
       功能：将源寄存器 <src> 中的短整数值写入到指定对象的实例字段中。
    * */
    iputShort(
        0x5fu,
        OpcodeType.T22c_field,
        "iput-short iinstanceop vA, vB, field@CCCC"
    ),


    /*
        格式：sget <dest>, <field>
        功能：从指定类的静态字段 (<field>) 中读取值，并将其存储到目标寄存器 <dest> 中。
* */
    sGet(
        0x60u,
        OpcodeType.T22c_field,
        "iget iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sget-wide <dest1>, <dest2>, <field>
        功能：从指定类的静态长整数字段中读取值，并将其存储到两个目标寄存器 <dest1> 和 <dest2> 中。
    * */
    sGetWide(
        0x61u,
        OpcodeType.T22c_field,
        "iget-wide iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sget-object <dest>, <field>
        功能：从指定类的静态对象字段中读取值，并将其引用存储到目标寄存器 <dest> 中。
    * */
    sGetObject(
        0x62u,
        OpcodeType.T22c_field,
        "iget-object iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sget-boolean <dest>, <field>
        功能：从指定类的静态布尔字段中读取值，并将其存储到目标寄存器 <dest> 中。
    * */
    sGetBoolean(
        0x63u,
        OpcodeType.T22c_field,
        "iget-boolean iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sget-byte <dest>, <field>
        功能：从指定类的静态字节字段中读取值，并将其存储到目标寄存器 <dest> 中。
    * */
    sGetByte(
        0x64u,
        OpcodeType.T22c_field,
        "iget-byte iinstanceop vA, vB, field@CCCC"
    ),

    /*
       格式：sget-char <dest>, <field>
       功能：从指定类的静态字符字段中读取值，并将其存储到目标寄存器 <dest> 中。
    * */
    sGetChar(
        0x65u,
        OpcodeType.T22c_field,
        "iget-Char iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sget-short <dest>, <field>
        功能：从指定类的静态短整数字段中读取值，并将其存储到目标寄存器 <dest> 中。
    * */
    sGetShort(
        0x66u,
        OpcodeType.T22c_field,
        "iget-Short iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sput <src>, <field>
        功能：将源寄存器 <src> 的值写入到指定类的静态字段 (<field>) 中。
    * */
    sPut(
        0x67u,
        OpcodeType.T22c_field,
        "iput iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sput-wide <src1>, <src2>, <field>
        功能：将两个源寄存器 <src1> 和 <src2> 的值写入到指定类的静态长整数字段中。
    * */
    sPutWide(
        0x68u,
        OpcodeType.T22c_field,
        "iput-wide iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sput-object <src>, <field>
        功能：将源寄存器 <src> 中的对象引用写入到指定类的静态对象字段中。
    * */
    sPutObject(
        0x69u,
        OpcodeType.T22c_field,
        "iput-object iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sput-boolean <src>, <field>
        功能：将源寄存器 <src> 中的布尔值写入到指定类的静态布尔字段中。
    *
    * */
    sPutBoolean(
        0x6au,
        OpcodeType.T22c_field,
        "iput-boolean iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sput-byte <src>, <field>
        功能：将源寄存器 <src> 中的字节值写入到指定类的静态字节字段中。
    * */
    sPutByte(
        0x6bu,
        OpcodeType.T22c_field,
        "iput-byte iinstanceop vA, vB, field@CCCC"
    ),

    /*
        格式：sput-char <src>, <field>
        功能：将源寄存器 <src> 中的字符值写入到指定类的静态字符字段中。
    * */
    sPutChar(
        0x6cu,
        OpcodeType.T22c_field,
        "iput-char iinstanceop vA, vB, field@CCCC"
    ),

    /*
    * 格式：sput-short <src>, <field>
      功能：将源寄存器 <src> 中的短整数值写入到指定类的静态短整数字段中。
    * */
    sPutShort(
        0x6du,
        OpcodeType.T22c_field,
        "iput-short iinstanceop vA, vB, field@CCCC"
    ),


    /*
    * A: argument word count (4 bits)
      B: method reference index (16 bits)
      C...G: argument registers (4 bits each)
    * */
    /*
    * 格式：invoke-virtual {parameters}, <method>
      功能：调用对象的虚拟方法。方法调用的目标在运行时动态确定，通常基于对象的实际类型。参数通过大括号内的寄存器列表传递。
    *
    * */
    invokeVirtual(
        0x6eu,
        OpcodeType.T35c,
        "invoke-virtual invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB"
    ),

    /*
    *  格式：invoke-super {parameters}, <method>
       功能：调用超类的实例方法。与虚拟方法调用类似，但调用的是超类中的方法。
    * */
    invokeSuper(
        0x6fu,
        OpcodeType.T35c,
        "invoke-super invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB"
    ),

    /*
    *  格式：invoke-direct {parameters}, <method>
       功能：调用类的直接方法，即私有方法或构造方法。与虚拟方法调用不同，它不会考虑继承关系，直接调用指定类中的方法。
    * */
    invokeDirect(
        0x70u,
        OpcodeType.T35c,
        "invoke-direct invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB"
    ),

    /*
    *  格式：invoke-static {parameters}, <method>
       功能：调用静态方法，该方法属于类而不是实例。不需要创建类的实例，直接使用类名调用方法。
    * */
    invokeStatic(
        0x71u,
        OpcodeType.T35c,
        "invoke-static invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB"
    ),

    /*
    *  格式：invoke-interface {parameters}, <interface-method>
       功能：调用接口中的方法。与虚拟方法调用类似，但用于接口中的方法。
    * */
    invokeInterface(
        0x72u,
        OpcodeType.T35c,
        "invoke-interface invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB"
    ),

    // 73 10x	(unused)	 	(unused)

    /*
    * A: argument word count (4 bits)
      B: method reference index (16 bits)
      C...G: argument registers (4 bits each)
    * */
    /*
    *  格式：invoke-virtual/range {parameters}, <method>
       功能：与 invoke-virtual 类似，调用对象的虚拟方法。方法调用的目标在运行时动态确定，参数通过范围内的寄存器列表传递。
    * */
    invokeVirtualRange(
        0x74u,
        OpcodeType.T3rc,
        "invoke-virtual/range invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB"
    ),

    /*
    *   格式：invoke-super/range {parameters}, <method>
        功能：与 invoke-super 类似，调用超类的实例方法。与 invoke-virtual/range 一样，使用范围内的寄存器列表传递参数。
    * */
    invokeSuperRange(
        0x75u,
        OpcodeType.T3rc,
        "invoke-super/range invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB"
    ),

    /*
    *  格式：invoke-direct/range {parameters}, <method>
       功能：与 invoke-direct 类似，调用类的直接方法。参数通过范围内的寄存器列表传递，不考虑继承关系。
    * */
    invokeDirectRange(
        0x76u,
        OpcodeType.T3rc,
        "invoke-direct/range invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB"
    ),

    /*
    *  格式：invoke-static/range {parameters}, <method>
       功能：与 invoke-static 类似，调用静态方法。不需要创建类的实例，参数通过范围内的寄存器列表传递。
    * */
    invokeStaticRange(
        0x77u,
        OpcodeType.T3rc,
        "invoke-static/range invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB"
    ),

    /*
    *  格式：invoke-interface/range {parameters}, <interface-method>
       功能：与 invoke-interface 类似，调用接口中的方法。参数通过范围内的寄存器列表传递。
    * */
    invokeInterfaceRange(
        0x78u,
        OpcodeType.T3rc,
        "invoke-interface/range invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB"
    ),

    // 79..7a 10x	(unused)	 	(unused)

    // 以下 这些 Dalvik 字节码指令是用于执行不同类型数据的数学和类型转换操作的指令
    /*
    *  Perform the identified unary operation on the source register, storing the result in the destination register.
    *   A: destination register or pair (4 bits)
        B: source register or pair (4 bits)
    * */
    //  Negation (neg) 和 Logical NOT (not) 操作
    /*
      格式：neg-int <dest>, <src>
      功能：将源寄存器 <src> 中的整数取反（负数）并将结果存储到目标寄存器 <dest> 中。
    * */
    negInt(0x7bu, OpcodeType.T12x, "neg-int  unop vA, vB"),

    /*
    * 格式：not-int <dest>, <src>
      功能：将源寄存器 <src> 中的整数进行按位取反，并将结果存储到目标寄存器 <dest> 中。
    * */
    notInt(0x7cu, OpcodeType.T12x, "not-int  unop vA, vB"),

    /*
    * 格式：neg-long <dest1>, <dest2>, <src1>, <src2>
      功能：将源寄存器 <src1> 和 <src2> 中的长整数取反（负数），并将结果存储到两个目标寄存器 <dest1> 和 <dest2> 中。
    * */
    negLong(0x7du, OpcodeType.T12x, "neg-long  unop vA, vB"),

    /*
    * 格式：not-long <dest1>, <dest2>, <src1>, <src2>
      功能：将源寄存器 <src1> 和 <src2> 中的长整数进行按位取反，并将结果存储到两个目标寄存器 <dest1> 和 <dest2> 中。
    * */
    notLong(0x7eu, OpcodeType.T12x, "not-long  unop vA, vB"),

    /*
    * 格式：neg-float <dest>, <src>
      功能：将源寄存器 <src> 中的浮点数取反（负数），并将结果存储到目标寄存器 <dest> 中。
    * */
    negFloat(0x7fu, OpcodeType.T12x, "neg-float  unop vA, vB"),

    /*
    * 格式：neg-double <dest1>, <dest2>, <src1>, <src2>
      功能：将源寄存器 <src1> 和 <src2> 中的双精度浮点数取反，并将结果存储到两个目标寄存器 <dest1> 和 <dest2> 中。
    * */
    negDouble(0x80u, OpcodeType.T12x, "neg-Double  unop vA, vB"),

    intToLong(0x81u, OpcodeType.T12x, "int-to-long  unop vA, vB"),
    intToFloat(0x82u, OpcodeType.T12x, "int-to-float  unop vA, vB"),
    intToDouble(0x83u, OpcodeType.T12x, "int-to-double  unop vA, vB"),

    longToInt(0x84u, OpcodeType.T12x, "long-to-int  unop vA, vB"),
    longToFloat(0x85u, OpcodeType.T12x, "long-to-float unop vA, vB"),
    longToDouble(0x86u, OpcodeType.T12x, "long-to-double  unop vA, vB"),

    floatToInt(0x87u, OpcodeType.T12x, "float-to-int  unop vA, vB"),
    floatToLong(0x88u, OpcodeType.T12x, "float-to-long  unop vA, vB"),
    floatToDouble(0x89u, OpcodeType.T12x, "float-to-double  unop vA, vB"),

    doubleToInt(0x8au, OpcodeType.T12x, "double-to-int unop vA, vB"),
    doubleToLong(0x8bu, OpcodeType.T12x, "double-to-long  unop vA, vB"),
    doubleToFloat(0x8cu, OpcodeType.T12x, "double-to-float  unop vA, vB"),

    intToByte(0x8du, OpcodeType.T12x, "int-to-byte  unop vA, vB"),
    intToChar(0x8eu, OpcodeType.T12x, "int-to-char  unop vA, vB"),
    intToShort(0x8fu, OpcodeType.T12x, "int-to-short  unop vA, vB"),

    // 以下 这些 Dalvik 字节码指令用于执行整数和浮点数的基本算术运算
    /*
       Perform the identified binary operation on the two source registers, storing the result in the destination register.

    *  A: destination register or pair (8 bits)
       B: first source register or pair (8 bits)
       C: second source register or pair (8 bits)
    * */
    // 加
    /*
     add-int：
        格式：add-int <dest>, <src1>, <src2>
        功能：将源寄存器 <src1> 和 <src2> 中的整数相加，并将结果存储到目标寄存器 <dest> 中。
    * */
    addInt(0x90u, OpcodeType.T23x, "add-int  binop vAA, vBB, vCC"),

    // 减
    subInt(0x91u, OpcodeType.T23x, "sub-int  binop vAA, vBB, vCC"),

    // 乘
    mulInt(0x92u, OpcodeType.T23x, "mul-int  binop vAA, vBB, vCC"),

    // 除
    divInt(0x93u, OpcodeType.T23x, "div-int  binop vAA, vBB, vCC"),

    // 取余
    remInt(0x94u, OpcodeType.T23x, "rem-int  binop vAA, vBB, vCC"),

    // 与
    andInt(0x95u, OpcodeType.T23x, "and-int  binop vAA, vBB, vCC"),

    // 或
    orInt(0x96u, OpcodeType.T23x, "or-int  binop vAA, vBB, vCC"),

    // 异或
    xorInt(0x97u, OpcodeType.T23x, "xor-int  binop vAA, vBB, vCC"),

    // 左移
    shlInt(0x98u, OpcodeType.T23x, "shl-int  binop vAA, vBB, vCC"),

    // 右移
    shrInt(0x99u, OpcodeType.T23x, "shr-int  binop vAA, vBB, vCC"),

    // 无符号右移
    ushrInt(0x9au, OpcodeType.T23x, "ushr-int  binop vAA, vBB, vCC"),

    addLong(0x9bu, OpcodeType.T23x, "add-long  binop vAA, vBB, vCC"),
    subLong(0x9cu, OpcodeType.T23x, "sub-long  binop vAA, vBB, vCC"),
    mulLong(0x9du, OpcodeType.T23x, "mul-long  binop vAA, vBB, vCC"),
    divLong(0x9eu, OpcodeType.T23x, "div-long  binop vAA, vBB, vCC"),
    remLong(0x9fu, OpcodeType.T23x, "rem-long  binop vAA, vBB, vCC"),

    andLong(0xa0u, OpcodeType.T23x, "and-long  binop vAA, vBB, vCC"),
    orLong(0xa1u, OpcodeType.T23x, "or-long  binop vAA, vBB, vCC"),
    xorLong(0xa2u, OpcodeType.T23x, "xor-long  binop vAA, vBB, vCC"),
    shlLong(0xa3u, OpcodeType.T23x, "shl-long  binop vAA, vBB, vCC"),
    shrLong(0xa4u, OpcodeType.T23x, "shr-long  binop vAA, vBB, vCC"),
    ushrLong(0xa5u, OpcodeType.T23x, "ushr-long  binop vAA, vBB, vCC"),

    addFloat(0xa6u, OpcodeType.T23x, "add-float  binop vAA, vBB, vCC"),
    subFloat(0xa7u, OpcodeType.T23x, "sub-float  binop vAA, vBB, vCC"),
    mulFloat(0xa8u, OpcodeType.T23x, "mul-float  binop vAA, vBB, vCC"),
    divFloat(0xa9u, OpcodeType.T23x, "div-float  binop vAA, vBB, vCC"),
    remFloat(0xaau, OpcodeType.T23x, "rem-float  binop vAA, vBB, vCC"),

    addDouble(0xabu, OpcodeType.T23x, "add-double  binop vAA, vBB, vCC"),
    subDouble(0xacu, OpcodeType.T23x, "sub-double  binop vAA, vBB, vCC"),
    mulDouble(0xadu, OpcodeType.T23x, "mul-double  binop vAA, vBB, vCC"),
    divDouble(0xaeu, OpcodeType.T23x, "div-double  binop vAA, vBB, vCC"),
    remDouble(0xafu, OpcodeType.T23x, "rem-double  binop vAA, vBB, vCC"),


    /*
    *  以下这些 Dalvik 字节码指令是二地址（2addr）版本的整数和浮点数基本算术运算指令。在这些指令中，结果存储在一个源寄存器中，而不是在一个额外的目标寄存器中
    * */
    /*
      Perform the identified binary operation on the two source registers, storing the result in the first source register.

      A: destination and first source register or pair (4 bits)
      B: second source register or pair (4 bits)
    * */
    // 加
    /*
    * add-int/2addr：
      格式：add-int/2addr <src1>, <src2>
      功能：将源寄存器 <src1> 中的整数与 <src2> 中的整数相加，并将结果存储回源寄存器 <src1>。
    * */
    addInt2addr(0xb0u, OpcodeType.T12x, "add-int  binop/2addr vAA, vBB, vCC"),

    // 减
    subInt2addr(0xb1u, OpcodeType.T12x, "sub-int  binop/2addr vAA, vBB, vCC"),

    // 乘
    mulInt2addr(0xb2u, OpcodeType.T12x, "mul-int  binop/2addr vAA, vBB, vCC"),

    // 除
    divInt2addr(0xb3u, OpcodeType.T12x, "div-int  binop/2addr vAA, vBB, vCC"),

    // 取余
    remInt2addr(0xb4u, OpcodeType.T12x, "rem-int  binop/2addr vAA, vBB, vCC"),

    // 与
    andInt2addr(0xb5u, OpcodeType.T12x, "and-int  binop/2addr vAA, vBB, vCC"),

    // 或
    orInt2addr(0xb6u, OpcodeType.T12x, "or-int  binop/2addr vAA, vBB, vCC"),

    // 异或
    xorInt2addr(0xb7u, OpcodeType.T12x, "xor-int  binop/2addr vAA, vBB, vCC"),

    // 左移
    shlInt2addr(0xb8u, OpcodeType.T12x, "shl-int  binop/2addr vAA, vBB, vCC"),

    // 右移
    shrInt2addr(0xb9u, OpcodeType.T12x, "shr-int  binop/2addr vAA, vBB, vCC"),

    // 无符号右移
    ushrInt2addr(0xbau, OpcodeType.T12x, "ushr-int  binop/2addr vAA, vBB, vCC"),

    addLong2addr(0xbbu, OpcodeType.T12x, "add-long  binop/2addr vAA, vBB, vCC"),
    subLong2addr(0xbcu, OpcodeType.T12x, "sub-long  binop/2addr vAA, vBB, vCC"),
    mulLong2addr(0xbdu, OpcodeType.T12x, "mul-long  binop/2addr vAA, vBB, vCC"),
    divLong2addr(0xbeu, OpcodeType.T12x, "div-long  binop/2addr vAA, vBB, vCC"),
    remLong2addr(0xbfu, OpcodeType.T12x, "rem-long  binop/2addr vAA, vBB, vCC"),

    andLong2addr(0xc0u, OpcodeType.T12x, "and-long  binop/2addr vAA, vBB, vCC"),
    orLong2addr(0xc1u, OpcodeType.T12x, "or-long  binop/2addr vAA, vBB, vCC"),
    xorLong2addr(0xc2u, OpcodeType.T12x, "xor-long  binop/2addr vAA, vBB, vCC"),
    shlLong2addr(0xc3u, OpcodeType.T12x, "shl-long  binop/2addr vAA, vBB, vCC"),
    shrLong2addr(0xc4u, OpcodeType.T12x, "shr-long  binop/2addr vAA, vBB, vCC"),
    ushrLong2addr(0xc5u, OpcodeType.T12x, "ushr-long  binop/2addr vAA, vBB, vCC"),

    addFloat2addr(0xc6u, OpcodeType.T12x, "add-float  binop vAA, vBB, vCC"),
    subFloat2addr(0xc7u, OpcodeType.T12x, "sub-float  binop vAA, vBB, vCC"),
    mulFloat2addr(0xc8u, OpcodeType.T12x, "mul-float  binop vAA, vBB, vCC"),
    divFloat2addr(0xc9u, OpcodeType.T12x, "div-float  binop vAA, vBB, vCC"),
    remFloat2addr(0xcau, OpcodeType.T12x, "rem-float  binop vAA, vBB, vCC"),

    addDouble2addr(0xcbu, OpcodeType.T12x, "add-double  binop vAA, vBB, vCC"),
    subDouble2addr(0xccu, OpcodeType.T12x, "sub-double  binop vAA, vBB, vCC"),
    mulDouble2addr(0xcdu, OpcodeType.T12x, "mul-double  binop vAA, vBB, vCC"),
    divDouble2addr(0xceu, OpcodeType.T12x, "div-double  binop vAA, vBB, vCC"),
    remDouble2addr(0xcfu, OpcodeType.T12x, "rem-double  binop vAA, vBB, vCC"),

    /*
    * Perform the indicated binary op on the indicated register (first argument) and literal value (second argument), storing the result in the destination register.
    * A: destination register (4 bits)
      B: source register (4 bits)
      C: signed int constant (16 bits)
    * */
    // 以下这些 Dalvik 字节码指令主要用于执行与常量（literal）进行的整数运算
    /*
    * add-int/lit16：
      格式：add-int/lit16 <dest>, <src>, <lit16>
      功能：将源寄存器 <src> 中的整数与常量 <lit16> 相加，并将结果存储到目标寄存器 <dest> 中。
    * */
    addIntLit16(0xd0u, OpcodeType.T22s, "add-int/lit16  binop/lit16 vA, vB, #+CCCC"),

    /*
    * rsub-int (reverse subtract)：
      格式：rsub-int <dest>, <src>, <lit16>
      功能：将常量 <lit16> 减去源寄存器 <src> 中的整数，并将结果存储到目标寄存器 <dest> 中。这是减法的变种，即 <dest> = <lit16> - <src>。
    * */
    rsubIntLit16(0xd1u, OpcodeType.T22s, "rsub-int (reverse subtract)  binop/lit16 vA, vB, #+CCCC"),
    mulIntLit16(0xd2u, OpcodeType.T22s, "mul-int/lit16  binop/lit16 vA, vB, #+CCCC"),
    divIntLit16(0xd3u, OpcodeType.T22s, "div-int/lit16  binop/lit16 vA, vB, #+CCCC"),
    remIntLit16(0xd4u, OpcodeType.T22s, "rem-int/lit16  binop/lit16 vA, vB, #+CCCC"),
    andIntLit16(0xd5u, OpcodeType.T22s, "and-int/lit16 binop/lit16 vA, vB, #+CCCC"),
    orIntLit16(0xd6u, OpcodeType.T22s, "or-int/lit16 binop/lit16 vA, vB, #+CCCC"),
    xorIntLit16(0xd7u, OpcodeType.T22s, "xor-int/lit16  binop/lit16 vA, vB, #+CCCC"),


    /*
    Perform the indicated binary op on the indicated register (first argument) and literal value (second argument), storing the result in the destination register.
    A: destination register (8 bits)
    B: source register (8 bits)
    C: signed int constant (8 bits)
    */
    //以下这些 Dalvik 字节码指令主要用于执行与常量（literal）进行的整数运算，其中常量为8位
    /*
    add-int/lit8：
    格式：add-int/lit8 <dest>, <src>, <lit8>
    功能：将源寄存器 <src> 中的整数与常量 <lit8> 相加，并将结果存储到目标寄存器 <dest> 中。
    * */
    addIntLit8(0xd8u, OpcodeType.T22b, "add-int/lit8  binop/lit8 vAA, vBB, #+CC"),
    rsubIntLit8(0xd9u, OpcodeType.T22b, "rsub-int/lit8  binop/lit8 vA, vB, #+CCCC"),
    mulIntLit8(0xdau, OpcodeType.T22b, "mul-int/lit8  binop/lit8 vA, vB, #+CCCC"),
    divIntLit8(0xdbu, OpcodeType.T22b, "div-int/lit8  binop/lit8 vA, vB, #+CCCC"),
    remIntLit8(0xdcu, OpcodeType.T22b, "rem-int/lit8  binop/lit8 vA, vB, #+CCCC"),
    andIntLit8(0xddu, OpcodeType.T22b, "and-int/lit8 binop/lit8 vA, vB, #+CCCC"),
    orIntLit8(0xdeu, OpcodeType.T22b, "or-int/lit8 binop/lit8 vA, vB, #+CCCC"),
    xorInLit8(0xdfu, OpcodeType.T22b, "xor-int/lit8  binop/lit8 vA, vB, #+CCCC"),
    shlIntLit8(0xe0u, OpcodeType.T22b, "and-int/lit8 binop/lit8 vA, vB, #+CCCC"),
    shrintLit8(0xe1u, OpcodeType.T22b, "or-int/lit8 binop/lit8 vA, vB, #+CCCC"),
    ushrInLit8(0xe2u, OpcodeType.T22b, "xor-int/lit8  binop/lit8 vA, vB, #+CCCC"),

    // e3...f9 10x	(unused)	 	(unused)

    /*
    * Invoke the indicated signature polymorphic method. The result (if any) may be stored with an appropriate move-result* variant as the immediately subsequent instruction.
    * */
    /*
    *  A: argument word count (4 bits)
       B: method reference index (16 bits)
       C: receiver (4 bits)
       D...G: argument registers (4 bits each)
       H: prototype reference index (16 bits)
    * */
    /*
    *
    Dalvik 字节码中的 invoke-polymorphic 指令是用于调用多态方法（polymorphic methods）的指令。多态方法是一种可以接受不同类型参数的方法。invoke-polymorphic 指令允许在运行时决定调用哪个方法，这对于支持通用类型或泛型编程非常有用。
    *
    * */
    invokePolymorphic(0xfau, OpcodeType.T45cc, "invoke-polymorphic {vC, vD, vE, vF, vG}, meth@BBBB, proto@HHHH"),

    /*
    * Invoke the indicated method handle. See the invoke-polymorphic description above for details.
      Present in Dex files from version 038 onwards.
      * A: argument word count (8 bits)
        B: method reference index (16 bits)
        C: receiver (16 bits)
        H: prototype reference index (16 bits)
        N = A + C - 1
    * */
    invokePolymorphicRange(0xfbu, OpcodeType.T4rcc, "invoke-polymorphic/range {vCCCC .. vNNNN}, meth@BBBB, proto@HHHH"),

    /* Resolves and invokes the indicated call site. The result from the invocation (if any) may be stored with an appropriate move-result* variant as the immediately subsequent instruction.
    *  A: argument word count (4 bits)
       B: call site reference index (16 bits)
       C...G: argument registers (4 bits each)
    * */
    invokeCustom(0xfcu, OpcodeType.T35c, "invoke-custom {vC, vD, vE, vF, vG}, call_site@BBBB"),

    /*
    * Resolve and invoke a call site. See the invoke-custom description above for details.
    *  A: argument word count (8 bits)
       B: call site reference index (16 bits)
       C: first argument register (16-bits)
       N = A + C - 1
    * */
    invokeCustomRange(0xfdu, OpcodeType.T3rc, "invoke-custom/range {vCCCC .. vNNNN}, call_site@BBBB"),

    /*
    *  Move a reference to the method handle specified by the given index into the specified register.
    *   A: destination register (8 bits)
        B: method handle index (16 bits)
    * */
    constMethodHandle(0xfeu, OpcodeType.T21c_method_handle, "const-method-handle vAA, method_handle@BBBB"),

    /*
    *  Move a reference to the method prototype specified by the given index into the specified register.
    *  A: destination register (8 bits)
       B: method prototype reference (16 bits)
    * */
    constMethodType(0xffu, OpcodeType.T21c_proto, "const-method-type vAA, proto@BBBB");


    override fun toString(): String {
        return "$name(${value.toString(16)},$type,$describe)"
    }

    companion object {
        fun getOpcodesTypeBy(value: UByte) = Opcodes.values().find { it.value == value }!!
    }
}