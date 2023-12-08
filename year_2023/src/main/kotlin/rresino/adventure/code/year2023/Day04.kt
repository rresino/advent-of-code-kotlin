package rresino.adventure.code.year2023

import kotlin.math.pow

object Day04 {

    data class Scratchcards(val winningNumbers: List<Int>, val numbers: List<Int>) {

        fun getCardWinningNumbers(): List<Int> = winningNumbers.filter { numbers.contains(it) }

        fun getCardWinningIndex(): List<Int> =
            winningNumbers
                .mapIndexed{ index, n -> if (numbers.contains(n)) { index } else { 0 }}
                .filter { it > 0 }

        fun getScore(): Int {
            return when (val numbersWithScore = getCardWinningNumbers().size) {
                0 -> 0
                1 -> 1
                else -> 2.0.pow(numbersWithScore-1).toInt()
            }
        }

        companion object {

            fun parseLineToInt(row: String): Pair<List<Int>,List<Int>> {
                val (_, strWinningNumbers, strNumbers) = row.split(":","|")
                val winningNumbers = strWinningNumbers.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
                val numbers = strNumbers.split(" ").filter { it.isNotBlank() }.map { it.toInt() }

                return Pair(winningNumbers, numbers)
            }

            fun create(row: String) : Scratchcards {

                val numbers = parseLineToInt(row)
                return Scratchcards(numbers.first, numbers.second)
            }

        }
    }

    fun runStep1(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day04.demo" else "day04")

        return lines.sumOf { Scratchcards.create(it).getScore() }
    }

    fun runStep2(useDemo: Boolean): Int {
        val cards: List<Scratchcards> =
            Utils.readCleanInput(if (useDemo) "day04.demo" else "day04").map { Scratchcards.create(it) }

        val counters = IntArray(cards.size) { 1 }

        cards.map { it.getCardWinningNumbers().size }.forEachIndexed { index, n ->
            repeat(n) {
                counters[index + it + 1] += counters[index]
            }
        }

        return counters.sum()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println()
        println("Step 1")
        println("Day 04 - Demo ${runStep1(true)}")
        println("Day 04 - ${runStep1(false)}")
        println()
        println("Step 2")
        println("Day 04 - Demo ${runStep2(true)}")
        println("Day 04 - ${runStep2(false)}")
        println()

    }

}