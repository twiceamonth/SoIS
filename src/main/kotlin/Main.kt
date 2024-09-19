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

    val tmp1 = sBlock.frw_S(in1, K, 11)
    val out1 = util.frwImproveBlock(tmp1, K, 11)

    val tmp2 = util.invImproveBloc(out1 ,K, 11)
    val out2 = sBlock.inv_S(tmp2, K, 11)
    println(out1)
    println(out2)
}