package org.example.algs

import org.example.utils.BaseUtils
import kotlin.math.pow

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


    // возможно все что ниже вынести лучше в BaseUtils
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

    fun count_unity_bits(num_in: Int): Int {
        var out = 0
        var rem = num_in

        for (i in 0..19) {
            val tmp = rem % 2
            rem = utils.div(rem, 2)
            out += tmp
        }

        return out
    }

    fun compose_num(num1_in: Int, num2_in: Int, cont_in: Int): Int {
        if(cont_in !in 1..19) {
            if(cont_in == 0) return num1_in else return num2_in
        }

        val arr1 = utils.dec2bin(num1_in)
        val arr2 = utils.dec2bin(num2_in)

        val tmp = mutableListOf<Int>()

        for (i in 0..<cont_in) {
            tmp += arr1[i]
        }

        // мб тут чтото не сойдется
        for (i in cont_in + 1..19) {
            tmp += arr2[i]
        }

        val out = utils.bin2dec(tmp)
        return out
    }

    //// os_fun это анонимная функция которой надо будет подать тело из вне
    //// пример вызова из main:
    //// [make_seed("АААА") { block_in, const_in, n_in -> sBlock.oneside_cesar(block_in, const_in, n_in) }]
    fun make_seed(block_in: String, os_fun: (block_in: String, const_in: String, n_in: Int) -> String): List<String> {
        val str1 = "ПЕРВЫЙ_ГЕНЕРАТОР"
        val str2 = "ВТОРОЙ_ГЕНЕРАТОР"
        val str3 = "ТРЕТИЙ_ГЕНЕРАТОР"

        val out = mutableListOf<String>()
        out += os_fun(block_in, str1, 10)
        out += os_fun(block_in, str2, 10)
        out += os_fun(block_in, str3, 10)

        return out
    }

    fun seed2nums(array_in: List<String>): List<Int> {
        val out = mutableListOf<Int>()

        for (i in 0..2) {
            out += utils.block2num(array_in[i])
        }
         return out
    }
}