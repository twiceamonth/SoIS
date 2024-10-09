import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BaseUtilsTest {

    private val utils = BaseUtils()

    @Test
    fun testSym2num_singleSymbol() {
        assertEquals(0, utils.sym2num("_"))
        assertEquals(1, utils.sym2num("А"))
        assertEquals(7, utils.sym2num("Ж"))
        assertEquals(15, utils.sym2num("О"))
        assertEquals(17, utils.sym2num("Р"))
    }

    @Test
    fun testSym2num_invalidInput() {
        var exception = assertThrows<Exception> {
            utils.sym2num("AA")
        }
        assertEquals("Passed more than 1 symbol", exception.message)

        exception = assertThrows<Exception> {
            utils.sym2num("AБББВВВГГГДДA   ПРИВЕТ")
        }
        assertEquals("Passed more than 1 symbol", exception.message)
    }



    @Test
    fun testNum2sym_validNumbers() {
        assertEquals("_", utils.num2sym(0))
        assertEquals("А", utils.num2sym(1))
        assertEquals("Е", utils.num2sym(6))
        assertEquals("Ж", utils.num2sym(7))
        assertEquals("О", utils.num2sym(15))
        assertEquals("Р", utils.num2sym(17))
        assertEquals("Я", utils.num2sym(31))


    }

    @Test
    fun testText2array() {
        assertEquals(listOf(1, 31), utils.text2array("АЯ"))
        assertEquals(listOf(0), utils.text2array("_"))
        assertEquals(listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,0),
            utils.text2array("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ_"))

    }

    @Test
    fun testArray2text() {
        assertEquals("АЯ", utils.array2text(listOf(1, 31)))
        assertEquals("_", utils.array2text(listOf(0)))
        assertEquals("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ_",
            utils.array2text(listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,0)))
    }

    @Test
    fun testAddS() {
        assertEquals("Е", utils.addS("Я", "Ж"))
        assertEquals("_", utils.addS("_", "_"))
    }

    @Test
    fun testSubS() {
        assertEquals("_", utils.subS("А", "А"))
        assertEquals("Я", utils.subS("Е", "Ж"))
    }

    @Test
    fun testAddTxt() {
        assertEquals("ИЖЬЯМАНЕ", utils.addTxt("ЕЖИК", "В_ТУМАНЕ"))
        assertEquals("БАРОН", utils.addTxt("Я__Н_", "ВАРАН"))
    }

    @Test
    fun testSubTxt() {
        assertEquals("__", utils.subTxt("АА", "АА"))
        assertEquals("_Ю", utils.subTxt("АЯ", "АА"))
        assertEquals("Я__Н_", utils.subTxt("БАРОН", "ВАРАН"))
        assertEquals("ЕЖИК____", utils.subTxt("ИЖЬЯМАНЕ", "В_ТУМАНЕ"))
    }

    @Test
    fun testFrwImproveBlock() {
        assertEquals("ЬООЫ", utils.frwImproveBlock("АТОЛ", "ГОРАЦИО", 2))
        assertEquals("ЬЯОЫ", utils.frwImproveBlock("АВОЛ", "ГОРАЦИО", 2))
        assertEquals("АУВО", utils.frwImproveBlock("АТОЛ", "ГОРАЦИО", 3))
    }

    @Test
    fun testInvImproveBlock() {
        assertEquals("АТОЛ", utils.invImproveBloc("ЬООЫ", "ГОРАЦИО", 2))
        assertEquals("АВОЛ", utils.invImproveBloc("ЬЯОЫ", "ГОРАЦИО", 2))
        assertEquals("АТОЛ", utils.invImproveBloc("АУВО", "ГОРАЦИО", 3))

    }
    @Test
    fun testBlock2Num() {
        assertEquals(34916, utils.block2num("АБВГ"))
        assertEquals(32028, utils.block2num("_ЯЗЬ"))
        assertEquals(1048575, utils.block2num("ЯЯЯЯ"))

    }


    @Test
    fun testNum2Block() {
        assertEquals("АБВГ", utils.num2block(34916))
        assertEquals("_ЯЗЬ", utils.num2block(32028))
        assertEquals("ЯЯЯЯ", utils.num2block(1048575))

    }
    @Test
    fun testDec2Bin() {
        assertEquals( mutableListOf<Int>(0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,0,0,1,0,0), utils.dec2bin(34916))
        assertEquals( mutableListOf<Int>(0,0,0,0,0,1,1,1,1,1,0,1,0,0,0,1,1,1,0,0), utils.dec2bin(32028))
        assertEquals( mutableListOf<Int>(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1), utils.dec2bin(1048575))

    }
    @Test
    fun testBin2Dec() {
        assertEquals(34916, utils.bin2dec( mutableListOf<Int>(0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,0,0,1,0,0)))
        assertEquals(32028, utils.bin2dec( mutableListOf<Int>(0,0,0,0,0,1,1,1,1,1,0,1,0,0,0,1,1,1,0,0)))
        assertEquals(1048575, utils.bin2dec( mutableListOf<Int>(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)))
    }
    @Test
    fun testCountUnityBits() {
        assertEquals(7, utils.count_unity_bits(1231))
        assertEquals(8, utils.count_unity_bits(723482))
    }
    @Test
    fun testComposeNum() {
        val tst1 = 1231
        val tst2 = 723482

        assertEquals(tst1, utils.compose_num(tst1,tst2,0))
        assertEquals(tst2, utils.compose_num(tst2,tst1,0))
        assertEquals(tst2, utils.compose_num(tst1,tst2,20))
        assertEquals(tst1, utils.compose_num(tst2,tst1,20))
        assertEquals(723151, utils.compose_num(tst2,tst1,10))
        assertEquals(1562, utils.compose_num(tst1,tst2,10))
        assertEquals(1242, utils.compose_num(tst1,tst2,14))
        assertEquals(723471, utils.compose_num(tst2,tst1,14))
    }
}
