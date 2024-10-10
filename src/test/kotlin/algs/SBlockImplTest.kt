import org.example.algs.Cesar
import org.example.algs.SBlockImpl
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SBlockImplTest {

    private val utils = BaseUtils()
    private val cesar = Cesar(utils)
    private val sBlock = SBlockImpl(utils, cesar)
    private val block_in1 = "КРОТ"
    private val block_in2 = "КРУТ"
    private val block_in3 = "ТОРК"
    private val block_in4 = "РОКТ"
    private val block_in5 = "ГРОТ"
    private val key1 = "РОЗА"
    private val key2 = "ЯДРО"


    @Test
    fun testFirstChain() {
        var b1_k1 = sBlock.frw_S(block_in1, key1,0)
        var b1_k1k2 = sBlock.frw_S(b1_k1, key2,0)
        var b1_k1k2_k2 = sBlock.inv_S(b1_k1k2, key2,0)
        var b1_k1k2_k2K1 = sBlock.inv_S(b1_k1k2_k2, key1,0)
        assertEquals("КРОТ", b1_k1k2_k2K1)


        var b1_k1k2_k1 = sBlock.inv_S(b1_k1k2, key1,0)
        var b1_k1k2_k1k2 = sBlock.inv_S(b1_k1k2_k1, key2,0)
        assertEquals("ХЫЛЕ", b1_k1k2_k1k2)


    }

    @Test
    fun testSecondChain() {
        assertEquals("_РЗГ", sBlock.frw_S(block_in1, key1, 0))
        assertEquals("_ОЗЬ", sBlock.frw_S(block_in3, key1, 0))
        assertEquals("_ОБЮ", sBlock.frw_S(block_in4, key1, 0))
    }


    @Test
    fun testThirdChain() {
        assertEquals("_РЗГ", sBlock.frw_S(block_in1, key1, 0))
        assertEquals("ДРМИ", sBlock.frw_S(block_in2, key1, 0))
        assertEquals("ШРЗГ", sBlock.frw_S(block_in5, key1, 0))
    }
    @Test
    fun testOnesideCesarBasicCase() {
        val block_in = "ВАСЯ"
        val const_in1 = "____"
        val const_in2 = "АААА"
        val const_in3 = "ББББ"
        val expected1 = "ЙКЮЩ"
        val expected2 = "ГУВЕ"
        val expected3 = "УЧШЙ"
        val n_in = 6

        val result1 = sBlock.oneside_cesar(block_in, const_in1, n_in)
        assertEquals(expected1, result1)

        val result3 = sBlock.oneside_cesar(block_in, const_in3, n_in)
        assertEquals(expected3, result3)

        val result2 = sBlock.oneside_cesar(block_in, const_in2, n_in)
        assertEquals(expected2, result2)
    }

    @Test
    fun testOnesideCesarSameBlockin() {
        val block_1 = "____"
        val block_2 = "___А"
        val block_3 = "Б___"
        val const_in = "ЗЕЛЕНЫЙ_ШАР"
        val n_in = 4

        val expected1 = "ЧУЕП"
        val expected2 = "ЧФХШ"
        val expected3 = "ПЗРЯ"


        val result1 = sBlock.oneside_cesar(block_1, const_in, n_in)
        assertEquals(expected1, result1)

        val result3 = sBlock.oneside_cesar(block_3, const_in, n_in)
        assertEquals(expected3, result3)

        val result2 = sBlock.oneside_cesar(block_2, const_in, n_in)
        assertEquals(expected2, result2)
    }
    @Test
    fun testMakeSeed() {
        assertEquals(mutableListOf<String>("ЗЗЕП","ТОБУ","ИВТС"), sBlock.make_seed("КОЛА"))
    }
    @Test
    fun testCheckSeed() {
        assertEquals("ААААГБЗЧББББЧККМ", sBlock.check_seed("ААААААААББББББББ"))
        assertEquals("ВВВВГГГГААААФПЬЬ", sBlock.check_seed("ВВВВГГГГАААААААА"))
        assertEquals("ААААГБЗЧЬЯГКДЖЮЗ", sBlock.check_seed("АААААААААААААААА"))
        assertEquals("____МЕУЬТНХТЖЛЦЫ", sBlock.check_seed("________________"))
    }
}
