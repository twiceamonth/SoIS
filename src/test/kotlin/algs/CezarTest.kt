import org.example.algs.Cesar
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CesarTest {

    private val utils = BaseUtils()
    private val cesar = Cesar(utils)

    @Test
    fun testFrwCesar() {
        assertEquals("ОЛОЛО_КРИНЖ", cesar.frw_Cesar("ОЛОЛО_КРИНЖ", "_"))
        assertEquals("ДБДБДХАЖЯГЭ", cesar.frw_Cesar("ОЛОЛО_КРИНЖ", "Х"))
        assertThrows<Error> { cesar.frw_Cesar("ААА", "ДВ") }
    }

    @Test
    fun testInvCesar() {
        assertEquals("ОЛОЛО_КРИНЖ", cesar.inv_Cesar("ОЛОЛО_КРИНЖ", "_"))
        assertEquals("ОЛОЛО_КРИНЖ", cesar.inv_Cesar("ДБДБДХАЖЯГЭ", "Х"))
        assertThrows<Error> { cesar.inv_Cesar("ААА", "ДВ") }
    }

    @Test
    fun testFrwPolyCesar() {
        assertEquals("ДЧРГЭГДАОЙШ", cesar.frw_poly_Cesar("ОЛОЛО_КРИНЖ", "Х", 0))
        assertEquals("ЯЭНЮЖЖ_ХОБН", cesar.frw_poly_Cesar("ОЛОЛО_КРИНЖ", "ПАНТЕОН", 0))
        assertEquals("ДЧРГЭГДАОЙШ", cesar.frw_poly_Cesar("ОЛОЛО_КРИНЖ", "Х", 1))
        assertEquals("ПЫРУЕД_ЖМДГ", cesar.frw_poly_Cesar("ОЛОЛО_КРИНЖ", "ПАНТЕОН", 1))
    }

    @Test
    fun testInvPolyCesar() {
        assertEquals("ОЛОЛО_КРИНЖ", cesar.inv_poly_Cesar("ДЧРГЭГДАОЙШ", "Х", 0))
        assertEquals("ОЛОЛО_КРИНЖ", cesar.inv_poly_Cesar("ЯЭНЮЖЖ_ХОБН", "ПАНТЕОН", 0))
        assertEquals("ОЛОЛО_КРИНЖ", cesar.inv_poly_Cesar("ДЧРГЭГДАОЙШ", "Х", 1))
        assertEquals("ОЛОЛО_КРИНЖ", cesar.inv_poly_Cesar("ПЫРУЕД_ЖМДГ", "ПАНТЕОН", 1))
    }
}
