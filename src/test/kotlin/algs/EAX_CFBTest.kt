package algs

import org.example.algs.EAX_CFB
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNotEquals

class EAX_CFBTest {
    val eax_cfb = EAX_CFB()



    @Test
    fun testFrwCFB() {
        val msg_in = eax_cfb.utils.inputs_array("data/inp.txt")[0]
        val iv_in = "АЛИСА_УМЕЕТ_ПЕТЬ"
        val key_in = eax_cfb.utils.produce_round_keys("СЕАНСОВЫЙ_КЛЮЧИК", 8)
        val mac_in = 1
        val r_in = 8


        var frw = eax_cfb.frw_CFB(msg_in,iv_in,key_in[0],mac_in,r_in)

        var res = eax_cfb.inv_CFB(frw, iv_in,key_in[0],mac_in,r_in)

        assertEquals(msg_in+"________________", res)


        frw = eax_cfb.frw_CFB(msg_in,iv_in,key_in[0],0,r_in)

        res = eax_cfb.inv_CFB(frw, iv_in,key_in[0],0,r_in)
        var res2 = eax_cfb.inv_CFB(frw, iv_in,key_in[0],1,r_in)

        assertEquals(msg_in, res)
        assertNotEquals(msg_in, res2)



    }
    /*@Test
    fun testFrwEAX_CFB() {
        val packer



        frw = eax_cfb.EAX_CFB_frw()

    }*/
}