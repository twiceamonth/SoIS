package org.example

import org.example.algs.Cesar
import org.example.algs.SBlockImpl
import org.example.utils.BaseUtils

fun main() {
    val util = BaseUtils()
    val cesar = Cesar(util)
    val sBlock = SBlockImpl(util)

    val K = "ЗВЕЗДНАЯ_НОЧЬ"
    val in1 = "БЛОК"

    val s1 = sBlock.frw_S(in1, K, 11)
    val s2 = sBlock.inv_S(s1, K, 11)
    println(s1)
    println(s2)

}