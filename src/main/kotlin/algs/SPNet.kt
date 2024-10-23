package org.example.algs

import org.example.utils.BaseUtils

class SPNet {
    private val utils = BaseUtils()
    private val cesar = Cesar(utils)
    val M = listOf(
        listOf(
            listOf(16,3,2,13),
            listOf(5,10,11,8),
            listOf(9,6,7,12),
            listOf(4,15,14,1)
        ),
        listOf(
            listOf(7,14,4,9),
            listOf(12,1,15,6),
            listOf(13,8,10,3),
            listOf(2,11,5,16)
        ),
        listOf(
            listOf(4,14,15,1),
            listOf(9,7,6,12),
            listOf(5,11,10,8),
            listOf(16,2,3,13)
        ),
    )

    fun frw_SPNet(block_in: String, key_in: String, r_in: Int ): String {
        val key_set = utils.produce_round_keys(key_in, r_in)
        var block = block_in
        for (i in 0..<r_in) {
            block = frw_round_SP(block_in, key_set[i], i)
        }
        return block
    }

    fun inv_SPNet(block_in: String, key_in: String, r_in: Int): String {
        val key_set = utils.produce_round_keys(key_in, r_in)
        var block = block_in
        for (i in r_in-1 downTo 0) {
            block = inv_round_SP(block_in, key_set[i], i)
        }
        return block
    }

    fun frw_round_SP(block_in: String, key_in: String, r_in: Int): String {
        var inter = ""
        for (i in 0..3) {
            val T = block_in.substring(i*4, i*4 + 4)
            inter += cesar.frw_poly_Cesar(T, key_in, i*4)
        }
        val tmp = frw_P_round(inter, r_in)
        return utils.bloc_xor(tmp, key_in)
    }

    fun inv_round_SP(block_in: String, key_in: String, r_in: Int): String {
        var out = ""
        val tmp = utils.bloc_xor(block_in, key_in)
        val inter = inv_P_round(tmp, r_in)
        for (i in 0..3) {
            val T = inter.substring(i*4, i*4 + 4)
            out += cesar.inv_poly_Cesar(T, key_in, i*4)
        }
        return out
    }

    fun frw_P_round(block_in: String, r_in: Int): String {
        val r = r_in % 3
        val j = 4 * (r_in % 4) + 2
        val tmp = frw_MagicSquare(block_in, M[r])
        val T = binary_shift(LB2B(tmp), j)
        return B2LB(T)
    }

    fun inv_P_round(block_in: String, r_in: Int): String {
        val r = r_in % 3
        val j = -(4 * (r_in % 4) + 2)
        val T = binary_shift(LB2B(block_in), j)
        return inv_MagicSquare(B2LB(T), M[r])
    }

    fun frw_MagicSquare(block_in: String, mat_in: List<List<Int>>): String {
        val d = block_in
        val m = mat_in
        var out = ""

        for(i in 0..3) {
            for (j in 0..3) {
                out += d.substring(m[i][j]-1, m[i][j]-1 + 1)
            }
        }
        return out
    }

    fun inv_MagicSquare(block_in: String, mat_in: List<List<Int>>): String {
        val d = utils.text2array(block_in)
        val m = mat_in
        val tmp = MutableList<Int>(16, {0})
        for (i in 0..3) {
            for (j in 0..3) {
                tmp[m[i][j]-1] = d[4*i+j]
            }
        }
        return utils.array2text(tmp)
    }

    fun binary_shift(array_in: List<Int>, shift_in: Int): List<Int> {
        val s = array_in.size
        val b = shift_in % s

        val out = MutableList(array_in.size, {0})
        if(b > 0) {
            for (i in b..<s) {
                out[i] = array_in[i-b]
            }
            for (i in 0..<b) {
                out[i] = array_in[s-b+i]
            }
        } else {
            for (i in 0..<s+b) {
                out[i] = array_in[i-b]
            }
            for (i in s+b..<s) {
                out[i] = array_in[i-s-b]
            }
        }
        return out
    }

    fun LB2B(block_in: String): List<Int> {
        val out = MutableList(80, {0})
        for (q in 0..3) {
            val t = block_in.substring(q*4, q*4 + 4)
            val tmp = utils.dec2bin(utils.block2num(t))
            for (i in 0..19) {
                out[i+q*20] = tmp[i]
            }
        }
        return out
    }

    fun B2LB(block_in: List<Int>): String {
        var out = ""
        for (q in 0..3) {
            val tmp = MutableList(80, {0})
            for (i in 0..19) {
                tmp[i] = block_in[i+q*20]
            }
            val t = utils.num2block(utils.bin2dec(tmp))
            out += t
        }
        return out
    }
}