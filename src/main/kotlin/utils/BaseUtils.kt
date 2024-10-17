package org.example.utils

import org.example.algs.CHCLCG
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.truncate

class BaseUtils {

    /*              Cesar               */
    fun sym2num(symIn: String): Int {
        if (symIn.length > 1) throw Exception("Passed more than 1 symbol")
        val tmp = symIn.toCharArray().map { it.code }
        if (tmp[0] == 95) {
            return 0
        }
        if (tmp[0] > 1066) {
            return tmp[0] - 1039 - 1
        }
        return tmp[0] - 1039
    }

    fun num2sym(numIn: Int): String {
        if (numIn == 0) {
            return "_"
        }
        if (numIn > 26) {
            return Char(numIn + 1039 + 1).toString()
        }
        return Char(numIn + 1039).toString()
    }

    fun text2array(txtIn: String): List<Int> {
        val out = mutableListOf<Int>()
        for (element in txtIn) {
            val tmp = element.toString()
            out.add(sym2num(tmp))
        }
        return out
    }

    fun array2text(arrIn: List<Int>): String {
        var out = ""
        arrIn.forEach {
            out += num2sym(it)
        }
        return out
    }

    fun addS(s1In: String, s2In: String): String {
        val temp = sym2num(s1In) + sym2num(s2In)
        return num2sym(temp % 32)
    }

    fun subS(s1In: String, s2In: String): String {
        val temp = sym2num(s1In) - sym2num(s2In) + 32
        return num2sym(temp % 32)
    }

    fun addTxt(t1In: String, t2In: String): String {
        var out = ""
        val m = min(t1In.length, t2In.length)
        val tIn = if (t1In.length > t2In.length) t1In else t2In
        val M = tIn.length
        for(i in 0..<m) {
            val t1 = t1In[i].toString()
            val t2 = t2In[i].toString()
            out += addS(t1, t2)
        }
        if(M>m) {
            // в диаграмме вообще m+1 почемуто, но +1 делать не надо
            for(i in m..<M) {
                val t = tIn[i].toString()
                out += t
            }
        }
        return out
    }

    fun subTxt(t1In: String, t2In: String): String {
        var out = ""
        val m = min(t1In.length, t2In.length)
        val tIn = if (t1In.length > t2In.length) t1In else t2In
        val flag = if (t1In.length > t2In.length) 0 else 1
        val M = tIn.length
        for(i in 0..<m) {
            val t1 = t1In[i].toString()
            val t2 = t2In[i].toString()
            out += subS(t1, t2)
        }
        if(M>m) {
            // в диаграмме вообще m+1 почемуто, но +1 делать не надо
            for (i in m..<M) {
                val t = tIn[i].toString()
                if(flag == 1) {
                    out += subS("_", t)
                } else {
                    out += subS(t, "_")
                }
            }
        }
        return out
    }

    fun frwImproveBlock(blockIn: String, keyIn: String, jIn: Int): String {
        var t = keyIn

        while(jIn > t.length-4) t += t

        val key = t.substring(t.indexOf(t[jIn]), t.indexOf(t[jIn]) + 4)
        val k = text2array(key)
        val b = text2array(blockIn).toMutableList()
        val q = (k[0]+k[1]+k[2]+k[3]) % 4

        for(i in 0..2) {
            val j = (q+i+1) % 4
            val l = (q+i) % 4
            b[j] = (b[j] + b[l]) % 32
        }

        val out = array2text(b)
        return out
    }

    fun invImproveBloc(blockIn: String, keyIn: String, jIn: Int): String {
        var t = keyIn

        while(jIn > t.length-4) t += t

        val key = t.substring(t.indexOf(t[jIn]), t.indexOf(t[jIn]) + 4)
        val k = text2array(key)
        val b = text2array(blockIn).toMutableList()
        val q = (k[0]+k[1]+k[2]+k[3]) % 4

        for(i in 2 downTo 0) {
            val j = (q+i+1) % 4
            val l = (q+i) % 4
            b[j] = (b[j] - b[l] + 32) % 32
        }

        val out = array2text(b)
        return out
    }

    /*              Random Generator                */

    fun div(num_in: Int, den_in: Int): Int {
        return truncate((num_in/den_in).toDouble()).toInt()
    }
    fun block2num(bloc_in: String): Int {
        if(bloc_in.length != 4) throw Error("Block must be 4 symbols length")

        var out = 0
        var pos = 1
        val tmp = text2array(bloc_in)

        for (i in 3 downTo 0) {
            out += pos * tmp[i]
            pos *= 32
        }

        return out
    }

    fun num2block(num_in: Int): String {
        var rem = num_in
        val tmp = mutableListOf<Int>()
        // вместо заполнения массива мусором заранее развернул ход массива и по идее итог тот же самый 3 downTo 0
        for (i in 0..3){
            tmp += rem % 32
            rem = div(rem, 32)
        }
        return array2text(tmp).reversed()
    }

    fun dec2bin(num_in: Int): List<Int> {
        var rem = num_in
        val out = mutableListOf<Int>()

        // вместо заполнения массива мусором заранее развернул ход массива и по идее итог тот же самый 19 downTo 0
        for (i in 0..19) {
            out += rem % 2
            rem = div(rem, 2)
        }

        return out.reversed()
    }

    fun bin2dec(bin_in: List<Int>): Int {
        var out = 0

        for (i in 0..19) {
            out = 2 * out + bin_in[i] // возможно тут +=
        }

        return out
    }

    /*              LCG             */

    //bpr_in и spr_in не матрицы а просто массивы
    fun make_coeffs(bpr_in: List<Int>, spr_in: List<Int>, pow_in: Int) : List<Int> {
        val ss = spr_in.min()
        val bs = bpr_in.min()
        val bb = bpr_in.max()
        val sb = spr_in.max()

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
            rem = div(rem, 2)
            out += tmp
        }

        return out
    }

    fun compose_num(num1_in: Int, num2_in: Int, cont_in: Int): Int {
        if(cont_in !in 1..19) {
            if(cont_in == 0) return num1_in else return num2_in
        }

        val arr1 = dec2bin(num1_in)
        val arr2 = dec2bin(num2_in)

        val tmp = mutableListOf<Int>()

        for (i in 0..<cont_in) {
            tmp += arr1[i]
        }

        for (i in cont_in until 20) {
            tmp += arr2[i]
        }

        val out = bin2dec(tmp)
        return out
    }

    fun seed2nums(array_in: List<String>): List<Int> {
        val out = mutableListOf<Int>()

        for (i in 0..2) {
            out += block2num(array_in[i])
        }
        return out
    }

    /*              lab3                */

    fun subblocks_xor(blocka_in: String, blockb_in: String): String {
        val decA = block2num(blocka_in)
        val decB = block2num(blockb_in)

        val binA = dec2bin(decA)
        val binB = dec2bin(decB)

        val binO = mutableListOf<Int>()
        for (i in 0..binA.size) {
            binO += ((binA[i] + binB[i]) % 2)
        }
        val decO = bin2dec(binO)

        return num2block(decO)
    }

    fun bloc_xor(blocka_in: String, blockb_in: String): String {
        val nb = div(blocka_in.length, 4)
        var out = ""

        for (i in 0..nb) {
            val tmpA = blocka_in.substring(blocka_in.indexOf(blocka_in[4*i]), blocka_in.indexOf(blocka_in[4*i]) + 4)
            val tmpB = blockb_in.substring(blockb_in.indexOf(blockb_in[4*i]), blockb_in.indexOf(blockb_in[4*i]) + 4)
            out += subblocks_xor(tmpA, tmpB)
        }

        return out
    }

    fun make_lcg_set(): List<List<Int>> {
        return listOf(
            listOf(723482, 8677, 983609),
            listOf(252564, 9109, 961193),
            listOf(357630, 8971, 948209),
        )
    }

    // я ббрал эти s_fun и rcg_fun, сид в общем будем извне сразу подавать
    fun produce_round_keys(key_in: String, num_in: Int, seedfun: String): List<String> {
        val set = make_lcg_set()
        val rcg = CHCLCG(seedfun, set)
        val out = mutableListOf<String>(rcg.CHCLCG_next())

        if(num_in > 1) {
            for (i in 1..num_in) {
                out += rcg.CHCLCG_next() // тут как сид -1 в методичке ват?
            }
        }

        return out
    }

}