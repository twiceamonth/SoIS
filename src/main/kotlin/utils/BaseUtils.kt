package org.example.utils

import kotlin.math.min

class BaseUtils {
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
}