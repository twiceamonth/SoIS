
import org.example.algs.SPNet
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SPNetTest {
    var spnet = SPNet()
    @Test
    fun testFrw_SPNet(){
        var input = "КОРЫСТЬ_СЛОНА_ЭХ"
        val key   = "МТВ_ВСЕ_ЕЩЕ_ТЛЕН"

        var res = spnet.frw_SPNet(input,key,8)
        assertEquals("ТЧЗЕАЦЧКХЬЕХВГЗУ",res)

        input = "ЛЕРА_КЛОНКА_КОНЯ"

        res = spnet.frw_SPNet(input,key,8)
        assertEquals("ЮГЛФЙГСЦПДЗЕЕМЕТ",res)
    }
    @Test
    fun testInv_SPNet(){
        var input = "ТЧЗЕАЦЧКХЬЕХВГЗУ"
        val key   = "МТВ_ВСЕ_ЕЩЕ_ТЛЕН"

        var res = spnet.frw_SPNet(input,key,8)
        assertEquals("КОРЫСТЬ_СЛОНА_ЭХ",res)

        input = "ЮГЛФЙГСЦПДЗЕЕМЕТ"

        res = spnet.frw_SPNet(input,key,8)
        assertEquals("ЛЕРА_КЛОНКА_КОНЯ",res)
    }
}