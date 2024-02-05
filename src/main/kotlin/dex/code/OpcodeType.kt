package dex.code

enum class OpcodeType(val format: String, val syntax: String, val bytes: Int) {

    /*
     op 代表指令操作码 一个字节大小
    * 一个 Ø ，A, B 代表 4 个bit 两个代表一个字节 四个表示两个字节 ，依此类推
    * v 表示寄存器
    * #+ 表示一个常量
    * + 表示指令偏移量，一般用于 goto 指令
    * @ 表示引用 dex 里面的数据 type field proto string meth site
    * */

    T10x("ØØ|op", "op", 1),
    T12x("B|A|op", "op vA, vB", 1),
    T11n("B|A|op", "op vA, #+B", 1),
    T11x("AA|op", "op vAA", 1),
    T10t("AA|op", "op +AA", 1), // goto
    T20t("ØØ|op AAAA", "op +AAAA", 2), // goto/16
//    T20bc("AA|op BBBB", "op AA, kind@BBBB"),

    T22x("AA|op BBBB", "op vAA, vBBBB", 2),
    T21t("AA|op BBBB", "op vAA, +BBBB", 2),
    T21s("AA|op BBBB", "op vAA, #+BBBB", 2),
    T21h_1("AA|op BBBB", "op vAA, #+BBBB0000", 2),
    T21h_2("AA|op BBBB", " op vAA, #+BBBB000000000000", 2),


    T21c_type("AA|op BBBB", "op vAA, type@BBBB", 2), // check-cast

    T21c_field("AA|op BBBB", "op vAA, field@BBBB", 2), // const-class

    T21c_method_handle("AA|op BBBB", "op vAA, method_handle@BBBB", 2), // const-method-handle

    T21c_proto("AA|op BBBB", "op vAA, proto@BBBB", 2), // const-method-type

    T21c_string("AA|op BBBB", "op vAA, string@BBBB", 2), // const-string

    T23x("AA|op CC|BB", "op vAA, vBB, vCC", 2),
    T22b("AA|op CC|BB", "op vAA, vBB, #+CC", 2),

    T22t("B|A|op CCCC", "op vA, vB, +CCCC", 2),
    T22s("B|A|op CCCC", "op vA, vB, #+CCCC", 2),
    T22c_type("B|A|op CCCC", "op vA, vB, type@CCCC", 2), //instance-of
    T22c_field("B|A|op CCCC", "op vA, vB, field@CCCC", 2), //instance-of

    T30t("ØØ|op AAAAlo AAAAhi", "op +AAAAAAAA", 3), // goto/32
    T32x("ØØ|op AAAA BBBB", "op vAAAA, vBBBB", 3),

    T31i("AA|op BBBBlo BBBBhi", "op vAA, #+BBBBBBBB", 3),
    T31t("AA|op BBBBlo BBBBhi", "op vAA, +BBBBBBBB", 3),
    T31c("AA|op BBBBlo BBBBhi", "op vAA, string@BBBBBBBB", 3), // const-string/jumbo
    T35c(
        "A|G|op BBBB F|E|D|C",
        """
        [A=5] op {vC, vD, vE, vF, vG}, meth@BBBB
        [A=5] op {vC, vD, vE, vF, vG}, site@BBBB
        [A=5] op {vC, vD, vE, vF, vG}, type@BBBB
        [A=4] op {vC, vD, vE, vF}, kind@BBBB
        [A=3] op {vC, vD, vE}, kind@BBBB
        [A=2] op {vC, vD}, kind@BBBB
        [A=1] op {vC}, kind@BBBB
        [A=0] op {}, kind@BBBB
        """, 3
    ),

    /*
    * where NNNN = CCCC+AA-1,
    * that is A determines the count 0..255, and C determines the first register
    *
    * */
    T3rc(
        "AA|op BBBB CCCC",
        "op {vCCCC .. vNNNN}, kind@BBBB", 3
    ),

    T45cc(
        "A|G|op BBBB F|E|D|C HHHH",
        """
         [A=5] op {vC, vD, vE, vF, vG}, meth@BBBB, proto@HHHH
         [A=4] op {vC, vD, vE, vF}, meth@BBBB, proto@HHHH
         [A=3] op {vC, vD, vE}, meth@BBBB, proto@HHHH
         [A=2] op {vC, vD}, meth@BBBB, proto@HHHH
         [A=1] op {vC}, meth@BBBB, proto@HHHH   
        """, 4
    ), // invoke-polymorphic

    /*
    * where NNNN = CCCC+AA-1, that is A determines the count 0..255, and C determines the first register
    * */
    T4rcc(
        "AA|op BBBB CCCC HHHH",
        "op> {vCCCC .. vNNNN}, meth@BBBB, proto@HHHH", 4
    ), // invoke-polymorphic/range
    T51l(
        "AA|op BBBBlo BBBB BBBB BBBBhi",
        "op vAA, #+BBBBBBBBBBBBBBBB", 5
    ) // const-wide
}