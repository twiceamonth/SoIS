package org.example

import org.example.algs.Cesar
import org.example.utils.BaseUtils

fun main() {
    val util = BaseUtils()
    val cesar = Cesar(util)

    val out = cesar.frw_poly_Cesar("ОЛОЛО_КРИНЖ", "Х", 0)
    println(out)
    println(cesar.inv_poly_Cesar(out, "Х", 0))
}