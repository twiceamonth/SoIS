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

        val key = t.substring(jIn, jIn + 4)
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

        val key = t.substring(jIn, jIn + 4)
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

        val binO = MutableList(binA.size, {0})
        for (i in 0..binA.size-1) {
            binO[i] = ((binA[i] + binB[i]) % 2)
        }
        val decO = bin2dec(binO)

        return num2block(decO)
    }

    fun bloc_xor(blocka_in: String, blockb_in: String): String {
        val nb = div(blocka_in.length, 4)
        var out = ""

        for (i in 0..nb-1) {
            val tmpA = blocka_in.substring(4*i, 4*i + 4)
            val tmpB = blockb_in.substring(4*i, 4*i + 4)
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

    fun produce_round_keys(key_in: String, num_in: Int): List<String> {
        val set = make_lcg_set()
        val rcg = CHCLCG(key_in, set)
        val out = mutableListOf<String>()

        if(num_in > 1) {
            for (i in 1..num_in) {
                out += rcg.CHCLCG_next()
            }
        }

        return out
    }


    /*              lab 4               */

    fun sym2bin(s_in: String): Int {
        return s_in.toList().map { it.code }[0]
    }

    fun isSym(s_in: String): Boolean {
        val C = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ_"

        if(s_in in C) {
            return true
        }
        return false
    }

    fun msg2bin(MSG_IN: String): List<Int> {
        val M = MSG_IN.length
        var  i = 0
        var f = 0
        val tmp = MutableList(MSG_IN.length, {0})
        while (isSym(MSG_IN[i].toString())) {
            val p = MSG_IN.substring(i, i + 1)
            var c = sym2num(p)
            for (j in 0..4) {
                if(c % 2 == 0) {
                    tmp[i*5+4-j] = 0
                } else {
                    tmp[i*5+4-j] = 1
                }
                c = div(c, 2)
            }

            if(i == M-1) {
                f = 1
                break
            } else {
                i++
            }
        }

        if(f == 0) {
            for (k in i..M-1) {
                val p = MSG_IN.substring(k, k + 1)
                tmp[4*i+k] = sym2bin(p)
            }
        }

        return tmp
    }

    fun bin2msg(BIN_IN: List<Int>): String {
        val B = BIN_IN.size
        val b = div(B, 5)
        val q = B % 5
        var out = ""
        for (i in 0..b-1) {
            var t = 0
            for (j in 0..4) {
                t = 2*t + BIN_IN[i*5+j]
            }
            out += num2sym(t)
        }

        if(q > 0) {
            for (k in 1..q) {
                out += BIN_IN[b*5+k-1].toChar().toString()
            }
        }
        return out
    }

    fun submatrix(matrix: List<Int>, startRow: Int, endRow: Int, startCol: Int, endCol: Int): List<Int> {
        // Предполагаем, что матрица квадратная, и вычисляем размер
        val size = kotlin.math.sqrt(matrix.size.toDouble()).toInt()

        val subMatrix = mutableListOf<Int>()

        // Извлечение элементов подматрицы по диапазонам строк и столбцов
        for (i in startRow..endRow) {
            for (j in startCol..endCol) {
                subMatrix.add(matrix[i * size + j])
            }
        }

        return subMatrix
    }

    fun check_padding(BINMASG_IN: List<Int>): List<Any> {
        val BINS = BINMASG_IN
        val M = BINS.size
        val blocks = div(M, 80)
        val reminder = M % 80
        var f = 0
        var numblocks = 0
        var padlegth = 0
        if(reminder == 0) {
            val tb = submatrix(BINS, M-20,M-1, 0,0)
            val ender = submatrix(tb, 17,19, 0,0)
            if(ender == listOf(0, 0, 1)) {
                val NB = submatrix(tb, 7,16, 0,0)
                val PL = submatrix(tb, 0,6, 0 ,0)

                for (i in 0..6) {
                   padlegth = 2*padlegth + PL[i]
                }

                for (i in 0..9) {
                    numblocks = 2*numblocks + NB[i]
                }
                if(numblocks == blocks && padlegth >= 23 && padlegth < 103) {
                    val tb = submatrix(BINS, M-padlegth,M-21, 0,0)
                    val starter = tb[0]
                    if(starter == 1) {
                        f = 1
                        for (j in 1..padlegth-1) {
                            val tmp = tb[j]
                            if(tmp == 1) {
                                f = 0
                            }
                            break // в методичке брейк тут, точно ли здесь он должен быть?
                        }
                    }
                } else {
                    f = 0
                }
            } else {
                f = 0
            }
        } else {
            f = 0
        }
        return listOf(f, listOf(numblocks, padlegth))
    }

    fun produce_padding(rem_in: Int, blocks_in: Int): List<Int> {
        var r = 0
        var b = 0
        if(rem_in == 0) {
            b = blocks_in+1
            r = 80
        }
        else if(rem_in <= 57) {
            r = 80 - rem_in
            b = blocks_in+1
        } else {
            b = blocks_in + 2
            r = 160 - rem_in
        }
        val pad = MutableList(r-21, {0})
        pad[0] = 1

        for (i in 1..r-21) { // можно вообще эттот цикл убрать
            pad[i] = 0
        }
        var rt = r

        for (i in 6 downTo 0) {
            pad[r-20+i] = rt % 2
            rt = div(rt, 2)
        }
        for (i in 9 downTo 0) {
            pad[r-12+i] = b % 2
            b = div(b,2)
        }
        pad[r-3] = 0
        pad[r-2] = 0
        pad[r-1] = 1
        return pad
    }

}