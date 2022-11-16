package day08

import readInput

/*
0:      1:      2:      3:      4:
 aaaa    ....    aaaa    aaaa    ....
b    c  .    c  .    c  .    c  b    c
b    c  .    c  .    c  .    c  b    c
 ....    ....    dddd    dddd    dddd
e    f  .    f  e    .  .    f  .    f
e    f  .    f  e    .  .    f  .    f
 gggg    ....    gggg    gggg    ....

5:      6:      7:      8:      9:
 aaaa    aaaa    aaaa    aaaa    aaaa
b    .  b    .  .    c  b    c  b    c
b    .  b    .  .    c  b    c  b    c
 dddd    dddd    ....    dddd    dddd
.    f  e    f  .    f  e    f  .    f
.    f  e    f  .    f  e    f  .    f
 gggg    gggg    ....    gggg    gggg

v0.length = 6 - see v6 and v9
v1.length = 2 - unique
v2.length = 5 - see v3 and v5
v3.length = 5 - see v2 and v5
v4.length = 4 - unique
v5.length = 5 - see v2 and v3
v6.length = 6 - see v0 and v9
v7.length = 3 - unique
v8.length = 7 - unique
v9.length = 6- see v0 and v6
 */

fun main() {

    fun List<String>.parse() = map { line ->
        val (digits, number) = line.split('|')
            .map { part ->
                part.trim().split(" ").map {
                    it.toSortedSet().joinToString("")
                }
            }

        //println("digits:$digits => number:$number")

        // easy
        val v1 = digits.first { it.length == 2 }
        val v4 = digits.first { it.length == 4 }
        val v7 = digits.first { it.length == 3 }
        val v8 = digits.first { it.length == 7 }

        val hasLettersBD = v4.filter { it !in v1 } // return many
        // val hasLetterA = v1.first { it !in v1 }

        val v5 = digits.first {
            it.length == 5 &&
            it.contains(hasLettersBD.first()) &&
            it.contains(hasLettersBD.last()) }

        val hasLetterC = v1.first { it !in v5 }
        val hasLetterF = v1.first { it in v5 }

        val v2 = digits.first { it.length == 5 && it.contains(hasLetterC) && !it.contains(hasLetterF) }
        val v3 = digits.first { it.length == 5 && it.contains(hasLetterC) && it.contains(hasLetterF) } // it !in listOf(v5, v3)

        val v6 = digits.first { it.length == 6 && !it.contains(hasLetterC) }

        val hasLetterD = hasLettersBD.first { it in v2 }

        val v9 = digits.first { it.length == 6 && it != v6 && it.contains(hasLetterD) }
        val v0 = digits.first { it.length == 6 && it != v6 && it != v9 }

        val v = listOf(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9)
        // println("v=>$v")
        val rs = number.map { v.indexOf(it) }
        // println("rs=>$rs")
        return@map rs
    }

    fun part1(input: List<String>) =
        input
            .parse()
            .flatten()
            .count { it in listOf(1, 4, 7, 8) }

    fun part2(input: List<String>) =
        input
            .parse()
            .sumOf { it.joinToString("").toInt() }

    //val rawInput = readInput("day08.demo")
    val rawInput = readInput("Day08")

    println("Part 1: ")
    println(part1(rawInput))
    println("Part 2: ")
    println(part2(rawInput))
}