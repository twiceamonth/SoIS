package org.example.algs

class LCG(
    private var state_in: Int,
    private val coeffs_in: List<Int>,
) {
    fun getState(): Int { return state_in }

    fun LCG_next(): Int {
        val a = coeffs_in[0].toLong()
        val c = coeffs_in[1].toLong()
        val m = coeffs_in[2].toLong()

        val out = (a * state_in + c) % m

        state_in = out.toInt()

        return out.toInt()
    }
}