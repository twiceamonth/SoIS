package algs

import org.example.algs.EAX_CFB
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFails
import kotlin.test.assertNotEquals

class EAX_CFBTest {
    var utils = BaseUtils()


    /*@Test
    *//*fun testFrwCFB() {
        val msg_in = utils.inputs_array("data/inp.txt")[0]
        val iv_in = "АЛИСА_УМЕЕТ_ПЕТЬ"
        val key_in = utils.produce_round_keys("СЕАНСОВЫЙ_КЛЮЧИК", 8)
        val mac_in = 1
        val r_in = 8


        var frw = EAX_CFB.frw_CFB(msg_in,iv_in,key_in[0],mac_in,r_in)

        var res = eax_cfb.inv_CFB(frw, iv_in,key_in[0],mac_in,r_in)

        assertEquals(msg_in+"________________", res)


        frw = eax_cfb.frw_CFB(msg_in,iv_in,key_in[0],0,r_in)

        res = eax_cfb.inv_CFB(frw, iv_in,key_in[0],0,r_in)
        var res2 = eax_cfb.inv_CFB(frw, iv_in,key_in[0],1,r_in)

        assertEquals(msg_in, res)
        assertNotEquals(msg_in, res2)
*//*


    }
    @Test
    fun testEAX_CFB() {
        val msg_in = utils.inputs_array("data/inp.txt")[0]
        val asdata = utils.assocdata_array("data/ad.txt")[1]
        val pack =  utils.prepare_packet(asdata,"БОБ_НЕМНОГО_ПЬЯН",msg_in)


        val key_in = utils.produce_round_keys("СЕАНСОВЫЙ_КЛЮЧИК", 8)
        val cad = asdata[0]+asdata[1]+asdata[2]+asdata[3]+"____"
        val SEC_IN = "ТОЖЕ_ЕЩЕ_НЕВАЖНО"
        val cadmac = eax_cfb.frw_CFB(cad,SEC_IN,key_in[0],-1,8)
        val frw = eax_cfb.EAX_CFB_frw(pack,cadmac,key_in[0],SEC_IN,0,8)

        val res = eax_cfb.EAX_CFB_inv(frw,cadmac,key_in[0],SEC_IN,0,8)

    }*/

    @Test
    fun testEAX_CFB(){

        val msg_in = utils.inputs_array("data/inp.txt")
        val asdata = utils.assocdata_array("data/ad.txt")[1]
        val key_in = "СЕАНСОВЫЙ_КЛЮЧИК"
        val nonce = "СЕМИХАТОВ_КВАНТЫ"

        var eax_cfb = EAX_CFB(asdata, key_in,nonce)
        val cypher = eax_cfb.EAX_CFB_send(msg_in)
        val res =  eax_cfb.EAX_CFB_reciev(cypher)
        for (i in 0..<res.size){
            println(res[i])
        }

    }


    @Test
    fun dddddddd() {
        // Инициализация параметров
        val key = "СЕАНСОВЫЙ_КЛЮЧИК" // Ключ шифрования (16 символов)
        val nonce = "СЕМИХАТОВ_КВАНТЫ" // Инициализационный вектор (nonce)
        val associatedData = utils.assocdata_array("data/ad.txt")[1] // Ассоциированные данные (AD)

        // Создание объекта шифра
        val eaxCfb = EAX_CFB(associatedData, key, nonce)

        // Сообщение для шифрования
        val message = utils.inputs_array("data/inp.txt")

        // Шифрование сообщения
        val encryptedMessage = eaxCfb.EAX_CFB_send(message)

        println("Encrypted message:")
        encryptedMessage.forEach { println(it) }

        // Расшифровка сообщения
        val receivedData = eaxCfb.EAX_CFB_reciev(encryptedMessage)

        println("\nDecrypted message:")
        val decryptedMessage = receivedData.map { it[0] } // Первый элемент в каждой строке - это сообщение
        println(decryptedMessage.joinToString(" "))

        // Проверка целостности данных
        val isValid = receivedData.all { it[1]=="ОК" } // Второй элемент в каждой строке - результат проверки
        if (isValid) {
            println("\nMessage integrity is valid.")
        } else {
            println("\nMessage integrity is compromised!")
        }

        // Попытка модификации данных
        println("\nTampering with the message...")
        val tamperedMessage = encryptedMessage.toMutableList()
        tamperedMessage[0] = tamperedMessage[0] + 1  // Изменяем первый блок

        // Проверяем модифицированные данные
        val tamperedData = eaxCfb.EAX_CFB_reciev(tamperedMessage)
        val isTamperedValid = tamperedData.all { it[1] == "ОК" }
        if (isTamperedValid) {
            println("\nTampered message passed integrity check (unexpected!).")
        } else {
            println("\nTampered message failed integrity check (as expected).")
        }
    }

}