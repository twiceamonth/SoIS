package org.example.algs

import org.example.utils.BaseUtils

class CHCLCG(
    private val seed: String, private val coeffs: List<List<Int>>
) {
    private val hclcg = mutableListOf<HCLCG>()
    private var seeds: MutableList<List<Int>> = mutableListOf()

    private val utils = BaseUtils()
    private val sBlock = SBlockImpl(utils, Cesar(utils))

    fun CHCLCG_next(): String {
        var stream = ""

        val seed_c = sBlock.check_seed(seed)
        for (i in 0..3) {
            val substr = seed_c.substring(i * 4, (i * 4) + 4)
            val seedSegment = sBlock.make_seed(substr)
            seeds.add(utils.seed2nums(seedSegment))
        }

        for (j in 0..3) {
            var tmp: Long = 0
            var sign = 1
            for (i in 0..3) {
                hclcg += HCLCG(seeds[i], coeffs)

                if(i > 0) {
                    for (i in 0..i) {
                        var res:Int = hclcg[i].HCLCG_next()
                        tmp = (1048576 + sign * res + tmp) % 1048576
                    }
                }

                sign = -sign
            }
            stream += utils.num2block(tmp.toInt())
        }

        return stream
    }
}