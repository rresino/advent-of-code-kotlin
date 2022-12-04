package rresino.advent.code.year2022

// Day 4: Camp Cleanup
// https://adventofcode.com/2022/day/4
object Day04 {

    private fun parseRange(row: String): Set<Int> {
        val chunks = row.split("-")
        return (chunks[0].toInt().. chunks[1].toInt()).toSet()
    }

    private fun contains(first: Set<Int>, second: Set<Int>): Boolean =
        (second - first).isEmpty() || (first - second).isEmpty()

    private fun isOverlap(first: Set<Int>, second: Set<Int>): Boolean =
        first.intersect(second).isNotEmpty()

    private fun parseRowToRanges(row: String): Pair<Set<Int>,Set<Int>> {
        val chunks = row.split(",")
        return Pair(parseRange(chunks[0]), parseRange(chunks[1]))
    }

    fun howManyFullyContains(rows: List<String>): Int =
        rows
            .map { parseRowToRanges(it) }
            .count { contains(it.first, it.second) }

    fun howManyOverlaps(rows: List<String>): Int =
        rows
            .map { parseRowToRanges(it) }
            .count { isOverlap(it.first, it.second) }

}

fun main() {

    val dataDemo = Utils.readInput("day04.demo")
    val dataStep = Utils.readInput("day04")

    println("Step 1")
    val rsDemo1 = Day04.howManyFullyContains(dataDemo)
    println("Demo: $rsDemo1")
    val rsStep1 = Day04.howManyFullyContains(dataStep)
    println("Result: $rsStep1")

    println("Step2")
    val rsDemo2 = Day04.howManyOverlaps(dataDemo)
    println("Demo: $rsDemo2")
    val rsStep2 = Day04.howManyOverlaps(dataStep)
    println("Result: $rsStep2")

}