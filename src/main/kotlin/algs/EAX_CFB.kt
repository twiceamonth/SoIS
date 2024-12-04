package org.example.algs

import org.example.utils.BaseUtils

class EAX_CFB(
    ass_data: List<String>,
    key_in: String,
    nonce: String,
    mtype: String
) {
    private val utils = BaseUtils()
    private val spNet = SPNet()

    val mtype: String
    val sender: String
    val reciever: String
    val transmition: String

    val keyset: List<String>
    val secret: String
    val data: String
    val data_mac: String

    val IV0: String
    val msg_counter: Int

    init {
        this.mtype = ass_data[0]
        this.sender = ass_data[1]
        this.reciever = ass_data[2]
        this.transmition = ass_data[3]
        val t1 = reciever + sender
        val t2 = mtype + transmition + "_____"
        var t3 = ""
        val cad = utils.addTxt(t1, t2)

        if (reciever < sender) {
            t3 = t1
        } else {
            t3 = sender + reciever
        }

        this.msg_counter = -1
        this.IV0 = utils.addTxt(cad, nonce).substring(0, 12)
        this.keyset = utils.produce_round_keys(key_in, 8)
        this.secret = frw_CFB(t3 + t2, key_in, keyset[0] /* ??????????? */, -1, 8)
        this.data = mtype + secret + reciever + transmition + "_____"
        this.data_mac = frw_CFB(data, secret, keyset[0] /* ??????????? */, -1, 8)
    }

    fun EAX_CFB_send(
        msg_array: List<String>
    ): List<Int> {
        var msg_conuter = 0

        for (i in 0..<msg_array.size) {
            val msg_sec = mtype
            msg_conuter++
            val IV = IV0 + utils.num2block(msg_conuter)
            val tmp_packet = utils.prepare_packet(
                listOf(msg_sec, sender, reciever, transmition),
                IV, msg_array[i]
            )

            if (msg_sec == "В_") return utils.transmit(tmp_packet)
            if (msg_sec == "ВА") return utils.transmit(
                EAX_CFB_frw(
                    tmp_packet,
                    data_mac,
                    keyset[0] /*???????*/,
                    secret,
                    1,
                    8
                )
            )
            if (msg_sec == "ВБ") return utils.transmit(
                EAX_CFB_frw(
                    tmp_packet,
                    data_mac,
                    keyset[0] /*???????*/,
                    secret,
                    0,
                    8
                )
            )
        }
        return emptyList()
    }

    fun EAX_CFB_reciev(
        msg_array: List<List<Int>>
    ): List<Any> {
        var last = -1
        for (i in 0..<msg_array.size) {
            val tmp_packet = utils.recieve(msg_array[i])
            val rdata = tmp_packet[0] as List<String>
            val current = utils.block2num((tmp_packet[3] as String)[i].toString().substring(12, 16))

            if (last > current) return emptyList()

            if (rdata[0] == "ВБ") {
                val rec_packet = EAX_CFB_inv(tmp_packet, "ТУТ ЧЕГОТО НЕ ХВАТАЕТ !", keyset[0], secret, 0, 8).toMutableList()
                rec_packet[2] = utils.unpad_message(rec_packet[2] as String)
                if (rec_packet[3] == "_____________") rec_packet[3] = "ОК"
                return rec_packet
            }

            if (rdata[0] == "ВА" && mtype != "ВБ") {
                val rec_packet = EAX_CFB_inv(tmp_packet, "ТУТ ЧЕГОТО НЕ ХВАТАЕТ !",keyset[0], secret, 1, 8).toMutableList()
                rec_packet[2] = utils.unpad_message(rec_packet[2] as String)
                if (rec_packet[3] == "_____________") {
                    last = current
                    rec_packet[3] = "ОК"
                }
                return rec_packet
            }

            if (rdata[0] == "В_" && mtype != "В_") {
                val rec_packet = tmp_packet.toMutableList()
                rec_packet[3] = utils.unpad_message(rec_packet[2] as String)
                if (rec_packet[3] == "") {
                    last = current
                    rec_packet[3] = "N/A"
                }
                return rec_packet
            }

            return tmp_packet
        }
        return emptyList()
    }


    /*    fun init(ass_data: List<String>, msg_array: List<String>, key_in: String, cypher_rounds: Int, nonce: String, type: Int) {
            val mtype = ass_data[0]
            val sender = ass_data[1]
            val reciever = ass_data[2]
            val transmition = ass_data[3]

            val t1 = reciever + sender
            val t2 = mtype + transmition + "_____"
            var t3 = ""

            val cad = utils.addTxt(t1, t2)
            val IV0 = utils.addTxt(cad, nonce).substring(0, 12)

            if(reciever < sender) {
                t3 = t1
            } else {
                t3 = sender + reciever
            }

            var msg_counter = -1

            val keyset = utils.produce_round_keys(key_in, 8)
            val secret = frw_CFB(t3+t2, key_in, keyset[0] *//* ??????????? *//* , -1, 8)
        val data = mtype + secret + reciever + transmition + "_____"
        val data_mac = frw_CFB(data, secret, keyset[0] *//* ??????????? *//*, -1, 8)
    }*/

    fun frw_CFB(msg_in: String, iv_in: String, key_in: String, mac_in: Int, r_in: Int): String {
        val m = utils.div(msg_in.length, 16)
        var feedback = iv_in
        var out = ""
        var cont = "________________"

        for (i in 0..<m) {
            val inp = msg_in.substring(i * 16, i * 16 + 16)
            cont = utils.textxor(inp, cont)
            val keystream = spNet.frw_SPNet(feedback, key_in, r_in)
            feedback = utils.textxor(inp, keystream)
            out += feedback
        }

        val keysream = spNet.frw_SPNet(feedback, key_in, r_in)
        val mac = utils.textxor(cont, keysream)

        if (mac_in == 1) return out + mac
        if (mac_in == -1) return mac
        return out
    }

    fun inv_CFB(msg_in: String, iv_in: String, key_in: String, mac_in: Int, r_in: Int): String {
        val m = utils.div(msg_in.length, 16)
        var feedback = iv_in
        var out = ""
        var cont = "________________"

        for (i in 0..m - 1 - mac_in) {
            val inp = msg_in.substring(i * 16, i * 16 + 16)
            val keystream = spNet.frw_SPNet(feedback, key_in, r_in)
            feedback = inp
            val text = utils.textxor(inp, keystream)
            cont = utils.textxor(cont, text)
            out += text
        }

        if (mac_in == 0) return out

        val mac = msg_in.substring((m - 1) * 16, (m - 1) * 16 + 16)
        val keystream = spNet.frw_SPNet(feedback, key_in, r_in)
        val text = utils.textxor(mac, keystream)
        cont = utils.textxor(cont, text)

        if (mac_in == 1) return out + cont
        return cont
    }

    fun EAX_CFB_frw(
        packet_in: List<Any>,
        cmac_in: String,
        key_in: String,
        sec_in: String,
        onlymac: Int,
        r_in: Int
    ): List<Any> {
        val assdata_in = packet_in[0] as List<String>
        val iv_in = packet_in[1] as String
        val msg_in = packet_in[2] as String
        var tmp = assdata_in[0] + assdata_in[3] + assdata_in[4]
        var msg = ""
        var MAC = ""

        val CIV = frw_CFB((sec_in + tmp), iv_in, key_in, -1, r_in)
        if (onlymac == 1) {
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

    fun EAX_CFB_inv(
        packet_in: List<Any>,
        cmac_in: String,
        key_in: String,
        sec_in: String,
        onlymac: Int,
        r_in: Int
    ): List<Any> {
        val ad_in = packet_in[0] as List<String>
        val iv_in = packet_in[1] as String
        val msg_in = packet_in[2] as String
        val mac_in = packet_in[3] as String

        var tmp = ad_in[0] + ad_in[3] + ad_in[4]
        var data = ad_in[0] + ad_in[1] + ad_in[2] + ad_in[3] + "____"

        val CMAC = frw_CFB(data, sec_in, key_in, -1, r_in)
        val CIV = frw_CFB((sec_in + tmp), iv_in, key_in, -1, r_in)

        var MAC = ""
        var MSG = ""
        if (onlymac == 1) {
            tmp = frw_CFB(msg_in, CIV, key_in, -1, r_in)
            MAC = utils.textxor(mac_in, utils.textxor(utils.textxor(tmp, CIV), cmac_in))
            MSG = msg_in
        } else {
            val cont = utils.textxor(utils.textxor(mac_in, CIV), cmac_in)
            tmp = inv_CFB((msg_in + cont), CIV, key_in, 1, r_in)
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