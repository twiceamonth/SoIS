package org.example.algs

import org.example.utils.BaseUtils

class CHCLCG(
    private val hclcg: HCLCG,
    private val utils: BaseUtils,
    private val seed_in: String,
    private var state_in: List<Int>,
    private var set_in: List<List<Int>>,
) {
    private var state = mutableListOf<Int>()
    fun CHCLCG_next(
        init_flag: String,
        os_fun: (block_in: String, const_in: String, n_in: Int) -> String
    ): String {
        if(init_flag != "up" && init_flag != "down") throw Error("Init flag must have 'up' or 'down' value!")

        var out = ""

        var stream = ""

        var check = 0

        if(init_flag == "up") {
            for (i in 0..3) {
                state_in = utils.seed2nums(utils.make_seed(
                    seed_in.substring(seed_in.indexOf(seed_in[4]), seed_in.indexOf(seed_in[4]) + i*4),
                    os_fun
                ))
            }
            check = 1
        } else {
            state = state_in.toMutableList()
            check = 1
        }

        if(check == 1) {
            val tmp = 0
            for (j in 0..3) {

                var sign = 1
                for (i in 0..3) {
                    val T = hclcg.HCLCG_next()
                    state[i] = T
                    sign = -sign
                }
            }

            stream += utils.num2block(tmp)
            return stream
        }
        return out
    }

}