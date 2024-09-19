package org.example

import org.example.algs.Cesar
import org.example.algs.SBlockImpl
import org.example.utils.BaseUtils

fun main() {
    val util = BaseUtils()
    val cesar = Cesar(util)
    val sBlock = SBlockImpl(util)

    val K = "ЗВЁЗДНАЯ_НОЧЬ"
    val in1 = "БЛОК"
    val out = sBlock.frw_S(in1, K, 11)
    println(out)
    println(sBlock.inv_S(out, K, 11))
}