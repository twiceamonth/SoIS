import org.example.algs.SBlockImpl
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SBlockImplTest {

    private val utils = BaseUtils()
    private val sBlock = SBlockImpl(utils)

    @Test
    fun testFrwS() {
        assertEquals("Щ_КЙ", sBlock.frw_S("БЛОК", "ЗВЁЗДНАЯ_НОЧЬ", 11))
        assertEquals("Щ_КВ", sBlock.frw_S("БЛОГ", "ЗВЁЗДНАЯ_НОЧЬ", 11))
        assertEquals("ЩВКВ", sBlock.frw_S("БООГ", "ЗВЁЗДНАЯ_НОЧЬ", 11))
        assertEquals("РТСХ", sBlock.frw_S("БЛОК", "ЗВЁЗДНАЯ_НОЧЬ", 10))
        assertEquals("input error", sBlock.frw_S("БРО", "ЗВЁЗДНАЯ_НОЧЬ",11) )
    }

    @Test
    fun testInvS() {
        assertEquals("БЛОК", sBlock.inv_S("Щ_КЙ", "ЗВЁЗДНАЯ_НОЧЬ", 11))
        assertEquals("БЛОГ", sBlock.inv_S("Щ_КВ", "ЗВЁЗДНАЯ_НОЧЬ", 11))
        assertEquals("БООГ", sBlock.inv_S("ЩВКВ", "ЗВЁЗДНАЯ_НОЧЬ", 11))
        assertEquals("БЛОК", sBlock.inv_S("РТСХ", "ЗВЁЗДНАЯ_НОЧЬ", 10))
        assertEquals("input error", sBlock.inv_S("input_error", "ЗВЁЗДНАЯ_НОЧЬ", 11))
    }
}
