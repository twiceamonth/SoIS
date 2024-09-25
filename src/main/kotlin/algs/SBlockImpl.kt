package org.example.algs

import org.example.utils.BaseUtils

class SBlockImpl(private val utils: BaseUtils) : SBlock {
    override fun frw_S(blockIn: String, keyIn: String, jIn: Int): String {
        var out = "input error"

        if(blockIn.length != 4) return out

        out = ""
        var tK = "_"
        val K = keyIn.length
        for(i in 0..3) {
            val tI = blockIn[i].toString()
            val q = (i+jIn) % K
            tK = utils.addS(tK, keyIn[q].toString())
            out += utils.addS(tI, tK)
        }
        return utils.frwImproveBlock(out, keyIn, jIn)
    }

    override fun inv_S(blockIn: String, keyIn: String, jIn: Int): String {
        var out = "input error"

        if(blockIn.length != 4) return out

        val imprBlock = utils.invImproveBloc(blockIn, keyIn, jIn)

        out = ""
        var tK = "_"
        val K = keyIn.length
        for(i in 0..3) {
            val tI = imprBlock[i].toString()
            val q = (i+jIn) % K
            tK = utils.addS(tK, keyIn[q].toString())
            out += utils.subS(tI, tK)
        }
        return out
    }
}