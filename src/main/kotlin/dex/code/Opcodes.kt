package dex.code

enum class Opcodes(val value: Short, val type: OpcodeType, val describe: String) {

    nop(0x00, OpcodeType.T1, "Waste cycles"),

    /*
    * A: destination register (4 bits)
      B: source register (4 bits)
      Move the contents of one non-object register to another.
    * */
    move(0x01, OpcodeType.T2, "move vA, vB"),

    /*Move the contents of one non-object register to another.
        A: destination register (8 bits)
        B: source register (16 bits)
    * */
    moveFrom16(0x02, OpcodeType.T6, "move/from16 vAA, vBBBB"),

    /*
    *   A: destination register (16 bits)
        B: source register (16 bits)
    * */
    move16(0x03, OpcodeType.T10, "move/16 vAAAA, vBBBB"),

    /* Move the contents of one register-pair to another.
    * A: destination register pair (4 bits)
    * B: source register pair (4 bits)
    * */
    moveWide(0x04, OpcodeType.T2, "move-wide vA, vB"),

    /*
    * A: destination register pair (8 bits)
    * B: source register pair (16 bits)
    * */
    moveWideFrom16(0x05, OpcodeType.T6, "move-wide/from16 vAA, vBBBB"),

    /*
      * A: destination register pair (16 bits)
      * B: source register pair (16 bits)
      * */
    moveWide16(0x06, OpcodeType.T10, "move-wide/16 vAAAA, vBBBB"),

    /* Move the contents of one object-bearing register to another.
    *   A: destination register (4 bits)
    *   B: source register (4 bits)
    * */
    moveObject(0x07, OpcodeType.T2, "move-object vA, vB"),

    /*
     *  A: destination register (8 bits)
     *  B: source register (16 bits)
    * */
    moveObjectFrom16(0x08, OpcodeType.T6, "move-object/from16 vAA, vBBBB"),

    /*
      * A: destination register (16 bits)
      * B: source register (16 bits)
      * */
    moveObject16(0x09, OpcodeType.T10, "move-object/16 vAAAA, vBBBB"),

    /*
     * A: destination register (8 bits)
     * Move the single-word non-object result of the most recent invoke-kind into the indicated register.
     * This must be done as the instruction immediately after an invoke-kind whose (single-word, non-object)
     * result is not to be ignored; anywhere else is invalid.
      * */
    moveResult(0x0a, OpcodeType.T3, "move-result vAA"),

    /*
    * Move the double-word result of the most recent invoke-kind into the indicated register pair.
    * This must be done as the instruction immediately after an invoke-kind whose (double-word)
    * result is not to be ignored; anywhere else is invalid.
    * */
    moveWideResult(0x0b, OpcodeType.T3, "move-result-wide vAA"),

    /*
    * Move the object result of the most recent invoke-kind into the indicated register.
    * This must be done as the instruction immediately after an invoke-kind or filled-new-array whose (object)
    * result is not to be ignored; anywhere else is invalid.
    * */
    moveObjectResult(0x0c, OpcodeType.T3, "move-result-object vAA"),

    /*
    * Save a just-caught exception into the given register.
    * This must be the first instruction of any exception handler whose
    * caught exception is not to be ignored, and this instruction must only ever occur as the first instruction of an exception handler;
    * anywhere else is invalid
    * */
    moveException(0x0d, OpcodeType.T3, "move-exception vAA"),


    /*
    *   Return from a void method.
    * */
    returnVoid(0x0e, OpcodeType.T1, "return-void"),

    /*
     *   A: return value register (8 bits)
    *   Return from a single-width (32-bit) non-object value-returning method.
    * */
    return32(0x0f, OpcodeType.T3, "return vAA"),

    /*
    *   Return from a double-width (64-bit) value-returning method.
    * */
    returnWide(0x10, OpcodeType.T3, "return-wide vAA"),

    /*
    *   Return from an object-returning method.
    * */
    returnObject(0x11, OpcodeType.T3, "return-object vAA"),

    /*  Move the given literal value (sign-extended to 32 bits) into the specified register.
    *   A: destination register (4 bits)
    *   B: signed int (4 bits)
    * */
    const4(0x12, OpcodeType.T2, "const/4 vA, #+B"),

    /*  Move the given literal value (sign-extended to 32 bits) into the specified register.
    *  A: destination register (8 bits)
    *  B: signed int (16 bits)
    * */
    const16(0x13, OpcodeType.T6, "const/16 vAA, #+BBBB"),

    /*  Move the given literal value into the specified register.
    *  A: destination register (8 bits)
    *  B: arbitrary 32-bit constant
    * */
    const32(0x14, OpcodeType.T11, "const vAA, #+BBBBBBBB"),

    /*  Move the given literal value (right-zero-extended to 32 bits) into the specified register.
    *  A: destination register (8 bits)
    *  B: signed int (16 bits)
    * */
    constHigh16(0x15, OpcodeType.T6, "const/high16 vAA, #+BBBB0000"),

    /*  Move the given literal value (sign-extended to 64 bits) into the specified register-pair.
    *  A: destination register (8 bits)
    *  B: signed int (16 bits)
    * */
    constWide16(0x16, OpcodeType.T6, "const-wide/16 vAA, #+BBBB"),

    /* Move the given literal value (sign-extended to 64 bits) into the specified register-pair.
    *  A: destination register (8 bits)
       B: signed int (32 bits)
    * */
    constWide32(0x17, OpcodeType.T11, "const-wide/32 vAA, #+BBBBBBBB"),

    /* Move the given literal value into the specified register-pair.
    * A: destination register (8 bits)
      B: arbitrary double-width (64-bit) constant
    * */
    constWide(0x18, OpcodeType.T16, "const-wide vAA, #+BBBBBBBBBBBBBBBB"),

    /* Move the given literal value (right-zero-extended to 64 bits) into the specified register-pair.
     *  A: destination register (8 bits)
     *  B: signed int (16 bits)
    * */
    constWideHight16(0x19, OpcodeType.T6, "const-wide/high16 vAA, #+BBBB000000000000"),

    /* Move a reference to the string specified by the given index into the specified register.
     *  A: destination register (8 bits)
        B: string index
    * */
    constString(0x1a, OpcodeType.T6, "const-string vAA, string@BBBB"),

    /* Move a reference to the string specified by the given index into the specified register.
        A: destination register (8 bits)
        B: string index
    * */
    constStringJumbo(0x1b, OpcodeType.T11, "const-string/jumbo vAA, string@BBBBBBBB"),

    /* Move a reference to the class specified by the given index into the specified register.
       In the case where the indicated type is primitive, this will store a reference to the primitive type's degenerate class.
        A: destination register (8 bits)
        B: type index
    * */
    constClass(0x1c, OpcodeType.T6, "const-class vAA, type@BBBB"),


    /* Acquire the monitor for the indicated object.
      A: reference-bearing register (8 bits)
    * */
    monitorEnter(0x1d, OpcodeType.T3, "monitor-enter vAA"),

    /* Release the monitor for the indicated object.
       A: reference-bearing register (8 bits)

       Note: If this instruction needs to throw an exception, it must do so as if the pc has already advanced past the instruction.
       It may be useful to think of this as the instruction successfully executing (in a sense),
       and the exception getting thrown after the instruction but before the next one gets a chance to run.
       This definition makes it possible for a method to use a monitor cleanup catch-all (e.g., finally) block as the monitor cleanup for that block itself,
        as a way to handle the arbitrary exceptions that might get thrown due to the historical implementation of Thread.stop(), while still managing to have proper monitor hygiene.
    * */
    monitorExit(0x1e, OpcodeType.T3, "monitor-exit vAA"),

    /*
    *  Throw a ClassCastException if the reference in the given register cannot be cast to the indicated type.
    * A: reference-bearing register (8 bits)
      B: type index (16 bits)

      Note: Since A must always be a reference (and not a primitive value), this will necessarily fail at runtime (that is, it will throw an exception) if B refers to a primitive type.
    * */
    checkCast(0x1f, OpcodeType.T6, "check-cast vAA, type@BBBB"),
}