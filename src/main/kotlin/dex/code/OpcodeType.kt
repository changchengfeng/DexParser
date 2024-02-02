package dex.code

enum class OpcodeType(val structure: String) {

    T1("ØØ|op"), //10x
    T2("B|A|op"), // 12x 11n
    T3("AA|op"), // 11x 10t
    T4("ØØ|op AAAA"), // 20t
    T5("AA|op BBBB"), // 20bc
    T6("AA|op BBBB"), // 22x 21t 21s 21h 21c
    T7("AA|op CC|BB"), // 23x 22b
    T8("B|A|op CCCC"), //22t 22s 22c 22cs
    T9("ØØ|op AAAAlo AAAAhi"), // 30t
    T10("ØØ|op AAAA BBBB"), // 32x
    T11("AA|op BBBBlo BBBBhi"), // 31i 31t 31c
    T12("A|G|op BBBB F|E|D|C"), // 35c 35ms 35mi
    T13("AA|op BBBB CCCC"), // 3rc 3rms 3rmi
    T14("A|G|op BBBB F|E|D|C HHHH"), // 45cc
    T15("AA|op BBBB CCCC HHHH"), // 4rcc
    T16("AA|op BBBBlo BBBB BBBB BBBBhi"); // 51l
}