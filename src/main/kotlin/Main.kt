package org.example

import org.example.algs.Cesar
import org.example.algs.SBlock
import org.example.algs.SBlockImpl
import org.example.utils.BaseUtils
import java.security.Key
import javax.swing.text.Keymap
import kotlin.system.exitProcess

fun main() {
    val util = BaseUtils()
    val cesar = Cesar(util)
    val sBlock = SBlockImpl(util, cesar )

    while (true) {
        println("Для выхода из приложения напишите q")
        println("Для работы с шифром цезаря напишите c")
        println("Для работы с S-блоком напишите s")
        val input = readln().lowercase()
        when(input) {
            "q" -> return
            "c" -> c(cesar)
            "s" -> s(sBlock)
        }
        println("--------------------------------")
    }
}

fun c(c: Cesar) {
    println("Введите слово:")
    val word = readln()
    println("Введите ключ:")
    val offset = readln()

    val frw = c.frw_Cesar(word, offset)
    val inv = c.inv_Cesar(frw, offset)
    println("Зашифрованное слово: $frw | Обратно расшифорванное слово: $inv \n")
}

fun s(s: SBlock) {
    println("Введите блок не более 4 символов:")
    val txt = readln()
    println("Введите ключ:")
    val key = readln()
    println("Введите смещение:")
    val offset = readln().toInt()

    val frw = s.frw_S(txt, key, offset)
    val inv = s.inv_S(frw, key, offset)
    println("Зашифрованное слово: $frw | Обратно расшифорванное слово: $inv \n")
}