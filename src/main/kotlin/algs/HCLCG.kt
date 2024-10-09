package org.example.algs

import org.example.utils.BaseUtils

class HCLCG(
    private val lcg: List<LCG>,
    private val utils: BaseUtils,
) {
    private var state = listOf<Int>()
    fun getState(): List<Int> { return state }

    fun HCLCG_next(): Int {
        val first = lcg[0].LCG_next()
        val second = lcg[1].LCG_next()
        val control = lcg[2].LCG_next()

        val n = utils.count_unity_bits(control)
        var out: Int

        if(control % 2 == 0) {
            out = utils.compose_num(first, second, n)
        } else {
            out = utils.compose_num(second, first, n)
        }
        state = listOf(first, second, control)

        return out
    }
}