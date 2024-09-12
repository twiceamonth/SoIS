package org.example

import org.example.utils.BaseUtils

fun main() {
    val util = BaseUtils()
    print(util.text2array("АБВГДЕЖЗЙКЛМНОПРСТУФХЦЧШЩЬЭЮЯ_"))
}