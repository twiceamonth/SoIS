package org.example.algs

import org.example.utils.BaseUtils

class Cesar(private val util: BaseUtils) {

    // to cesar
    fun frw_Cesar(txtIn: String, keyIn: String): String {
        if(keyIn.length > 1) throw Error("Key length longer than 1")

        var out = ""
        val key = keyIn[0].toString()

        for(i in 0..<txtIn.length) {
            val tmp = txtIn[i].toString()
            out += util.addS(tmp, key)
        }
        return out
    }

    //from cesar
    fun inv_Cesar(txtIn: String, keyIn: String): String {
        if(keyIn.length > 1) throw Error("Key length longer than 1")

        var out = ""
        val key = keyIn[0].toString()

        for(i in 0..<txtIn.length) {
            val tmp = txtIn[i].toString()
            out += util.subS(tmp, key)
        }

        return out
    }
}