package org.example

import org.example.algs.Cesar
import org.example.utils.BaseUtils

fun main() {
    val util = BaseUtils()
    val cesar = Cesar(util)

    println(cesar.frw_Cesar("СЫЗРАНЬ", "А"))
    println(cesar.inv_Cesar("ТЬИСБОЭ", "А"))
}