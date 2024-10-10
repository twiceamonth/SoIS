package org.example.algs

import org.example.utils.BaseUtils

class SBlockImpl(private val utils: BaseUtils, private val cesar: Cesar) : SBlock {
    override fun frw_S(blockIn: String, keyIn: String, jIn: Int): String {
        var out = "input error"
        if(blockIn.length != 4) return out
        out = cesar.frw_poly_Cesar(blockIn, keyIn, jIn)
        return utils.frwImproveBlock(out, keyIn, jIn)
    }

    override fun inv_S(blockIn: String, keyIn: String, jIn: Int): String {
        var out = "input error"
        if(blockIn.length != 4) return out
        val impBlock = utils.invImproveBloc(blockIn, keyIn, jIn)
        out = cesar.inv_poly_Cesar(impBlock, keyIn, jIn)
        return out
    }

    /*              One-Side                */

    fun oneside_cesar(block_in: String, const_in: String, n_in: Int): String {
        val data = block_in
        val c = const_in.length
        val C = "ТПУ" + const_in + const_in.substring(0..<4)
        var key =  C.substring(3..<3+4)

        var out = ""

        for (i in 0..n_in-1) {
            val q = (i*4) % c + 3
            val tmp = frw_S(data, key, 0)
            val s = utils.block2num(tmp) % 4
            key = utils.addTxt(tmp, C.substring(q-s..<q-s+4))

            out = tmp
        }

        return out
    }

    fun make_seed(block_in: String): List<String> {
        val str1 = "ПЕРВЫЙ_ГЕНЕРАТОР"
        val str2 = "ВТОРОЙ_ГЕНЕРАТОР"
        val str3 = "ТРЕТИЙ_ГЕНЕРАТОР"

        val out = mutableListOf<String>()
        out += oneside_cesar(block_in, str1, 10)
        out += oneside_cesar(block_in, str2, 10)
        out += oneside_cesar(block_in, str3, 10)

        return out
    }
    fun check_seed(block_in: String): String {
        val C = "ОТВЕТСТВЕННЫЙ_ПОДХОД"
        val T = mutableListOf<String>()

        for (i in 0..3) {
            T += block_in.substring(block_in.indexOf(block_in[4*i]), block_in.indexOf(block_in[4*i]) + 4)
        }

        for (j in 0..2) {
            for (i in j+1..3) {
                if(T[i] == T[j]) {
                    T[i] = oneside_cesar(T[j], C, j+2*i)
                }
            }
        }

        return T[0] + T[1] + T[2] + T[3]
    }
}