package org.example.algs

import org.example.utils.BaseUtils

class EAX_CFB {
    val utils = BaseUtils()
    val spNet = SPNet()

    fun frw_CFB(msg_in: String, iv_in: String, key_in: String, mac_in: Int, r_in: Int): String {
        val m = utils.div(msg_in.length, 16)
        var feedback = iv_in
        var out = ""
        var cont = "________________"

        for (i in 0..<m){
            val inp = msg_in.substring(i*16, i*16 + 16)
            cont = utils.textxor(inp, cont)
            val keystream = spNet.frw_SPNet(feedback, key_in, r_in)
            feedback = utils.textxor(inp, keystream)
            out += feedback
        }

        val keysream = spNet.frw_SPNet(feedback, key_in, r_in)
        val mac = utils.textxor(cont, keysream)

        if(mac_in == 1) return out + mac
        if(mac_in == -1) return mac
        return out
    }

    fun inv_CFB(msg_in: String, iv_in: String, key_in: String, mac_in: Int, r_in: Int): String {
        val m = utils.div(msg_in.length, 16)
        var feedback = iv_in
        var out = ""
        var cont = "________________"

        for (i in 0..m-1-mac_in) {
            val inp = msg_in.substring(i*16, i*16 + 16)
            val keystream = spNet.frw_SPNet(feedback, key_in, r_in)
            feedback = inp
            val text = utils.textxor(inp, keystream)
            cont = utils.textxor(cont, text)
            out += text
        }

        if(mac_in == 0) return out

        val mac = msg_in.substring((m-1)*16, (m-1)*16 + 16)
        val keystream = spNet.frw_SPNet(feedback, key_in, r_in)
        val text = utils.textxor(mac, keystream)
        cont = utils.textxor(cont, text)

        if(mac_in == 1) return out + cont
        return cont
    }

    fun EAX_CFB_frw(packet_in: List<Any>, cmac_in: String, key_in: String, sec_in: String, onlymac: Int, r_in: Int): List<Any> {
        val assdata_in = packet_in[0] as List<String>
        val iv_in = packet_in[1] as String
        val msg_in = packet_in[2] as String
        var tmp = assdata_in[0] + assdata_in[3] + assdata_in[4]
        var msg = ""
        var MAC = ""

        val CIV = frw_CFB((sec_in+ tmp), iv_in, key_in, -1, r_in)
        if(onlymac == 1) {
            tmp = frw_CFB(msg_in, CIV, key_in, -1, r_in)
            MAC = utils.textxor(utils.textxor(tmp, CIV), cmac_in)
            msg = msg_in
        } else {
            tmp = frw_CFB(msg_in, CIV, key_in, 1, r_in)
            val m = tmp.substring(msg_in.length, msg_in.length + 16)
            MAC = utils.textxor(utils.textxor(m, CIV), cmac_in)
            msg = tmp.substring(0, msg_in.length)
        }

        return listOf(
            assdata_in,
            iv_in,
            msg,
            MAC
        )
    }

    fun EAX_CFB_inv(packet_in: List<Any>, cmac_in: String, key_in: String, sec_in: String, onlymac: Int, r_in: Int) : List<Any> {
        val ad_in = packet_in[0] as List<String>
        val iv_in = packet_in[1] as String
        val msg_in = packet_in[2] as String
        val mac_in = packet_in[3] as String

        var tmp = ad_in[0] + ad_in[3] + ad_in[4]
        var data =  ad_in[0] + ad_in[1] + ad_in[2] + ad_in[3] + "____"

        val CMAC = frw_CFB(data, sec_in, key_in, -1, r_in)
        val CIV = frw_CFB((sec_in+tmp), iv_in, key_in, -1, r_in)

        var MAC = ""
        var MSG = ""
        if (onlymac == 1) {
            tmp = frw_CFB(msg_in, CIV, key_in, -1, r_in)
            MAC = utils.textxor(mac_in, utils.textxor(utils.textxor(tmp, CIV), cmac_in))
            MSG = msg_in
        } else {
            val cont = utils.textxor(utils.textxor(mac_in, CIV), cmac_in)
            tmp = inv_CFB((msg_in+cont), CIV, key_in, 1, r_in)
            val m = tmp.substring(msg_in.length, msg_in.length + 16)
            MAC = m
            MSG = tmp.substring(0, msg_in.length)
        }

        return listOf(
            ad_in,
            iv_in,
            MSG,
            MAC
        )
    }
}