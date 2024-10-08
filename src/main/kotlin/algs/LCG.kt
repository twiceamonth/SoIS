package org.example.algs

class LCG {

    fun LCG_next(state_in: Int, coeffs_in: List<Int>): Int {
        val a = coeffs_in[0]
        val c = coeffs_in[1]
        val m = coeffs_in[2]

        val out = (a * state_in + c) % m

        return out
    }
}