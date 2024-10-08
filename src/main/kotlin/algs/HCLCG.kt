package org.example.algs

import org.example.utils.BaseUtils

class HCLCG(
    private val lcg: LCG,
    private val utils: BaseUtils,
    private var state_in:List<Int>,
    private var set_in: List<List<Int>>,
) {
    fun HCLCG_next(): Int {
        val first = lcg.LCG_next(state_in[0], set_in[0])
        val second = lcg.LCG_next(state_in[1], set_in[1])
        val control = lcg.LCG_next(state_in[2], set_in[2])

        val n = utils.count_unity_bits(control)

        var out: Int
        if(control % 2 == 0) {
            out = utils.compose_num(first, second, n)
        } else {
            out = utils.compose_num(second, first, n)
        }
        state_in = listOf(first, second, control)

        return out
    }
}