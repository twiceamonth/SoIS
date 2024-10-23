package org.example.algs

import org.example.utils.BaseUtils

class SPNet {
    private val utils = BaseUtils()
    private val cesar = Cesar(utils)
    private val M = listOf(
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
            listOf(2,1,5,16)
        ),
        listOf(
            listOf(4,14,15,1),
            listOf(9,7,6,12),
            listOf(5,11,10,8),
            listOf(16,2,3,13)
        ),
    )

    // тк я в produce_round_keys инициализирую генератор, и изза того что у нас не по маткадовски, сид сразу тут подается
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

    private fun frw_round_SP(block_in: String, key_in: String, r_in: Int): String {
        var inter = ""
        for (i in 0..3) {
            val T = block_in.substring(i*4, i*4 + 4)
            inter += cesar.frw_poly_Cesar(T, key_in, i*4)
        }
        val tmp = frw_P_round(inter, r_in)
        return utils.bloc_xor(tmp, key_in)
    }

    private fun inv_round_SP(block_in: String, key_in: String, r_in: Int): String {
        var out = ""
        val tmp = utils.bloc_xor(block_in, key_in)
        val inter = inv_P_round(tmp, r_in)
        for (i in 0..3) {
            val T = inter.substring(i*4, i*4 + 4)
            out += cesar.inv_poly_Cesar(T, key_in, i*4)
        }
        return out
    }

    private fun frw_P_round(block_in: String, r_in: Int): String {
        val r = r_in % 3
        val j = 4 * (r_in % 4) + 2
        val tmp = frw_MagicSquare(block_in, M[r])
        val T = binary_shift(LB2B(tmp), j)
        return B2LB(T)
    }

    private fun inv_P_round(block_in: String, r_in: Int): String {
        val r = r_in % 3
        val j = -(4 * (r_in % 4) + 2)
        val T = binary_shift(LB2B(block_in), j)
        return inv_frw_MagicSquare(B2LB(T), M[r])
    }

    private fun frw_MagicSquare(block_in: String, mat_in: List<List<Int>>): String {
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

    private fun inv_frw_MagicSquare(block_in: String, mat_in: List<List<Int>>): String {
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

    private fun binary_shift(array_in: List<Int>, shift_in: Int): List<Int> {
        val s = array_in.size
        val b = shift_in % s

        val out = mutableListOf<Int>()
        if(b > 0) {
            // поменял местами циклы чтобы просто +=, надеюсь проблем изза этого не будет
            for (i in 0..<b) {
                out += array_in[s-b+i]
            }
            for (i in b..s) {
                out += array_in[i-b]
            }
        } else {
            // поменял местами циклы чтобы просто +=, надеюсь проблем изза этого не будет
            for (i in s+b..s) {
                out += array_in[i-s-b]
            }
            for (i in 0..s+b) {
                out += array_in[i-b]
            }
        }
        return out
    }

    private fun LB2B(block_in: String): List<Int> {
        val out = mutableListOf<Int>()
        for (q in 0..3) {
            val t = block_in.substring(q*4, q*4 + 4)
            val tmp = utils.dec2bin(utils.block2num(t))
            for (i in 0..19) {
                out += tmp[i]
            }
        }
        return out
    }

    private fun B2LB(block_in: List<Int>): String {
        var out = ""
        for (q in 0..3) {
            val tmp = mutableListOf<Int>()
            for (i in 0..19) {
                tmp.add(i, block_in[i+q*20])
            }
            val t = utils.num2block(utils.bin2dec(tmp))
            out += t
        }
        return out
    }
}