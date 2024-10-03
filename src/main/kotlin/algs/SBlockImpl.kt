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
        var key =  C.substring(3..<4)

        var out = ""

        for (i in 0..n_in-1) {
            val q = (i*4) % c + 3
            val tmp = frw_S(data, key, 0)
            val s = utils.block2num(tmp) % 4
            key = utils.addTxt(tmp, C.substring(q-s..<4))

            out = tmp
        }

        return out
    }
}