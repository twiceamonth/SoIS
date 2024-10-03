package org.example.algs

import org.example.utils.BaseUtils
import kotlin.math.pow
import kotlin.time.times

class LCG(
    private val utils: BaseUtils,
    private val sBlock: SBlockImpl,
) {

    fun LCG_next(state_in: Int, coeffs_in: List<Int>): Int {
        val a = coeffs_in[0]
        val c = coeffs_in[1]
        val m = coeffs_in[2]

        val out = (a * state_in + c) % m

        return out
    }

    //bpr_in и spr_in не матрицы а просто массивы
    fun make_coeffs(bpr_in: List<Int>, spr_in: List<Int>, pow_in: Int) : List<Int> {
        val ss = spr_in.min()
        val bs = bpr_in.min()
        val bb = bpr_in.max()
        val sb = spr_in.max()

        // немножко закостылил
        val max = (2.0.pow(pow_in)).toInt() - 1
        var tmp = bs * ss
        val a = ss * bs * sb + 1
        val c = bb

        for (i in 0..pow_in) {
            if(tmp*ss >= max) tmp *= ss
        }

        val m = tmp
        if(a<m && c<m) return listOf(a, c, m) else throw Error("wrong_guess")
    }
}