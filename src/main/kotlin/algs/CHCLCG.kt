package org.example.algs

import org.example.utils.BaseUtils

class CHCLCG(
    private val seed: String, private val coeffs: List<List<Int>>
) {
    private val hclcg = mutableListOf<HCLCG>()
    private var seeds: List<List<Int>> = emptyList()
    private var state: List<Int> = emptyList()

    private val utils = BaseUtils()
    private val sBlock = SBlockImpl(utils, Cesar(utils))

   /* init {
        for (i in 0..3) {
            hclcg += HCLCG(seed[i], coeffs)
        }
    }*/

    fun CHCLCG_next(): String {
        var stream = ""

        for (i in 0..3) {
            seeds += listOf(
                utils.seed2nums(sBlock.make_seed(seed.substring(seed.indexOf(seed[4]), seed.indexOf(seed[4]) + i*4)))
            )
        }

        // такое говнище наверное, писал как понял что хотят
        for (j in 0..3) {
            var tmp = 0
            var sign = 1
            for (i in 0..3) {
                hclcg += HCLCG(seeds[i], coeffs)
                state += hclcg[i].HCLCG_next()
                tmp = (1048576 + sign * state[0] + tmp) % 1048576
                sign = -sign
            }
            stream += utils.num2block(tmp)
        }

        return stream
    }

}