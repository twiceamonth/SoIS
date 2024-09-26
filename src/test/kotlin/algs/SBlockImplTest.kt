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

//    @Test
//    fun testFrwS() {
//        assertEquals("Щ_КЙ", sBlock.frw_S("БЛОК", "ЗВЕЗДНАЯ_НОЧЬ", 11))
//        assertEquals("Щ_КВ", sBlock.frw_S("БЛОГ", "ЗВЕЗДНАЯ_НОЧЬ", 11))
//        assertEquals("ЩВКВ", sBlock.frw_S("БООГ", "ЗВЕЗДНАЯ_НОЧЬ", 11))
//        assertEquals("РТСХ", sBlock.frw_S("БЛОК", "ЗВЕЗДНАЯ_НОЧЬ", 10))
//        assertEquals("input error", sBlock.frw_S("БРО", "ЗВЕЗДНАЯ_НОЧЬ",11) )
//    }

//    @Test
//    fun testInvS() {
//        assertEquals("БЛОК", sBlock.inv_S("Щ_КЙ", "ЗВЕЗДНАЯ_НОЧЬ", 11))
//        assertEquals("БЛОГ", sBlock.inv_S("Щ_КВ", "ЗВЕЗДНАЯ_НОЧЬ", 11))
//        assertEquals("БООГ", sBlock.inv_S("ЩВКВ", "ЗВЕЗДНАЯ_НОЧЬ", 11))
//        assertEquals("БЛОК", sBlock.inv_S("РТСХ", "ЗВЕЗДНАЯ_НОЧЬ", 10))
//        assertEquals("input error", sBlock.inv_S("input_error", "ЗВЕЗДНАЯ_НОЧЬ", 11))
//    }
}
