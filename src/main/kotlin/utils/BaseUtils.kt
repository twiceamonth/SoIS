package org.example.utils

class BaseUtils {
    fun sym2num(symIn: String): Int {
        if(symIn.length > 1) throw Exception("Passed more than 1 symbol")
        val tmp = symIn.toCharArray().map { it.code }
        if(tmp[0] == 95) {
            return 0
        }
        if(tmp[0] > 1066) {
            return tmp[0] - 1039 - 1
        }
        return tmp[0] - 1039
    }

    fun num2sym(numIn: Int) : String {
        if(numIn == 0) {
            return "-"
        }
        if(numIn > 26) {
            return Char(numIn + 1039 + 1).toString()
        }
        return Char(numIn + 1039).toString()
    }

    fun text2array(txtIn: String) : List<Int> {
        val out = mutableListOf<Int>()
        for(element in txtIn) {
            val tmp = element.toString()
            out.add(sym2num(tmp))
        }
        return out
    }
}