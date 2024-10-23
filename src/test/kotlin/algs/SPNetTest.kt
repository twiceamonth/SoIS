
import org.example.algs.SPNet
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SPNetTest {
    var spnet = SPNet()
    var utils = BaseUtils()
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

    @Test
    fun testFrw_MagicSquare() {
        val input = "АБВГДЕЖЗИЙКЛМНОП"
        val out1 = spnet.frw_MagicSquare(input, spnet.M[0])
        val out2 = spnet.frw_MagicSquare(input, spnet.M[1])
        val out3 = spnet.frw_MagicSquare(input, spnet.M[2])

        assertEquals("ПВБМДЙКЗИЕЖЛГОНА", out1)
        assertEquals("ЖНГИЛАОЕМЗЙВБКДП", out2)
        assertEquals("ГНОАИЖЕЛДКЙЗПБВМ", out3)
    }

    @Test
    fun testInv_MagicSquare() {
        val input = "АБВГДЕЖЗИЙКЛМНОП"
        val out1 = spnet.inv_MagicSquare(spnet.frw_MagicSquare(input, spnet.M[0]), spnet.M[0])
        val out2 = spnet.inv_MagicSquare(spnet.frw_MagicSquare(input, spnet.M[1]), spnet.M[1])
        val out3 = spnet.inv_MagicSquare(spnet.frw_MagicSquare(input, spnet.M[2]), spnet.M[2])

        assertEquals(input, out1)
        assertEquals(input, out2)
        assertEquals(input, out3)
    }
    @Test
    fun testBinaryShift(){
        val input1 =utils.dec2bin(utils.block2num("ГОЛД"))
        val input2 =utils.dec2bin(utils.block2num("ЯРУС"))

        val out1 = spnet.binary_shift(input1,1)
        val lout1 = spnet.binary_shift(out1,-1)
        val out2 = spnet.binary_shift(input2,1)
        val lout2 = spnet.binary_shift(out2,-1)
        assertEquals(listOf(1,0,0,1,0,0,0,1,1,1,1,0,1,1,0,0,0,0,1,0),out1)
        assertEquals(listOf(0,0,1,0,0,0,1,1,1,1,0,1,1,0,0,0,0,1,0,1),lout1)
        assertEquals(listOf(0,1,1,1,1,1,1,0,0,0,1,1,0,1,0,0,1,0,0,1),out2)
        assertEquals(listOf(1,1,1,1,1,1,0,0,0,1,1,0,1,0,0,1,0,0,1,0),lout2)
        assertEquals("ГОЛД",utils.num2block(utils.bin2dec(lout1)))
        assertEquals("ЯРУС",utils.num2block(utils.bin2dec(lout2)))
    }
    @Test
    fun testLB2BandB2LB(){
        val input1 ="ЗОЛОТАЯ_СЕРЕДИНА"
        val temp = spnet.LB2B(input1)
        val res = spnet.B2LB(temp)
        assertEquals(input1,res)
    }

    @Test
    fun testFrw_P_round(){
        val input1 ="ЗОЛОТАЯ_СЕРЕДИНА"
        val input2 ="АБВГДЕЖЗИЙКЛМНОП"

        assertEquals("ПЯУЦШВГЖ_СПВЕЖЧШ",spnet.frw_P_round(input1,1))
        assertEquals("ЛДОИНЗСЯАЕТРЕ_АО",spnet.frw_P_round(input1,2))
        assertEquals("ОЩКЬРСВПИЗАТВЬЛЧ",spnet.frw_P_round(input1,4))
        assertEquals("ЧВЦБГХ_ЦТЕУДАРДС",spnet.frw_P_round(input2,1))
        assertEquals("ВМГНОАИЖЕЛДКЙЗПБ",spnet.frw_P_round(input2,2))
        assertEquals("АЫРБК_КШТЙБПЧСШЛ",spnet.frw_P_round(input2,4))
    }
    @Test
    fun testInv_P_round(){
        val input1 ="ЗОЛОТАЯ_СЕРЕДИНА"
        val input2 ="АБВГДЕЖЗИЙКЛМНОП"

        assertEquals(input1,spnet.inv_P_round("ПЯУЦШВГЖ_СПВЕЖЧШ",1))
        assertEquals(input1,spnet.inv_P_round("ЛДОИНЗСЯАЕТРЕ_АО",2))
        assertEquals(input1,spnet.inv_P_round("ОЩКЬРСВПИЗАТВЬЛЧ",4))
        assertEquals(input2,spnet.inv_P_round("ЧВЦБГХ_ЦТЕУДАРДС",1))
        assertEquals(input2,spnet.inv_P_round("ВМГНОАИЖЕЛДКЙЗПБ",2))
        assertEquals(input2,spnet.inv_P_round("АЫРБК_КШТЙБПЧСШЛ",4))
    }

    @Test
    fun testFrw_round_SP(){
        val input1 = "КОРЫСТЬ_СЛОНА_ЭХ"
        val input2 = "КОРЫСТЬ_СЛОН__ЭХ"
        val key   = "МТВ_ВСЕ_ЕЩЕ_ТЛЕН"

        val out1 = spnet.frw_round_SP(input1,key,0)
        val out2 = spnet.frw_round_SP(input2,key,0)
        assertEquals("ЯЭЙООРКНЮУДЩЬЫРК", out1)
        assertEquals("ЯЭЙОЖРКНЮУДЩЬЫЗК", out1)

    }
    @Test
    fun testInv_round_SP(){
        val input1 = "ЯЭЙООРКНЮУДЩЬЫРК"
        val input2 = "ЯЭЙОЖРКНЮУДЩЬЫЗК"
        val key   = "МТВ_ВСЕ_ЕЩЕ_ТЛЕН"

        val out1 = spnet.inv_round_SP(input1,key,0)
        val out2 = spnet.inv_round_SP(input2,key,0)
        assertEquals("КОРЫСТЬ_СЛОНА_ЭХ", out1)
        assertEquals("КОРЫСТЬ_СЛОН__ЭХ", out1)

    }
}