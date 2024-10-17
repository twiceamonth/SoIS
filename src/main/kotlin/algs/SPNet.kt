package org.example.algs

import org.example.utils.BaseUtils

class SPNet {
    val utils = BaseUtils()
    fun frw_MagicSquare(block_in: String, mat_in: List<List<Int>>): String {
        val d = block_in
        val m = mat_in
        var out = ""

        for(i in 0..3) {
            for (j in 0..3) {
                out += d.substring(d.indexOf(d[m[i][j]-1]), d.indexOf(d[m[i][j]-1]) + 1)
            }
        }
        return out
    }

    fun inv_frw_MagicSquare(block_in: String, mat_in: List<List<Int>>): String {
        val d = utils.text2array(block_in)
        val m = mat_in
        val tmp = mutableListOf<Int>()
        for (i in 0..3) {
            for (j in 0..3) {
                tmp += d[4*i+j]
            }
        }
        return utils.array2text(tmp)
    }

    fun binary_shift(array_in: List<Int>, shift_in: Int): List<Int> {
        val s = array_in.size
        val b = shift_in % s

        val out = mutableListOf<Int>()
        if(b > 0) {
            // поменял местами циклы чтобы просто +=
            for (i in 0..<b) {
                out += array_in[s-b+i]
            }
            for (i in b..s) {
                out += array_in[i-b]
            }
        } else {
            for (i in s+b..s) {
                out += array_in[i-s-b]
            }
            for (i in 0..s+b) {
                out += array_in[i-b]
            }
        }
        return out
    }
}