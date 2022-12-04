package rresino.advent.code.year2022

// Day 3: Rucksack Reorganization
// https://adventofcode.com/2022/day/3
object Day03 {

    private fun getPriority(c: Char): Int =
        if (c.isLowerCase()) c.code - 96
        else c.code - 38

    private fun splitRow(row: String): Pair<Set<Char>, Set<Char>> {
        val middle = row.length/2

        return Pair(
            row.substring(0, middle).toCharArray().toHashSet(),
            row.substring(middle).toCharArray().toHashSet(),
        )
    }

    private fun findCoincidence(data: Pair<Set<Char>,Set<Char>>): Char {
        val rs: Set<Char> = data.first.intersect(data.second)
        return rs.first()
    }

    fun getSumPriorities(rows: List<String>): List<Int> {
        return rows
            .map { splitRow(it) }
            .map { findCoincidence(it) }
            .map { getPriority(it) }
    }

    private fun group3Rows(rows: List<String>): List<Triple<Set<Char>,Set<Char>,Set<Char>>> {
        val rs = mutableListOf<Triple<Set<Char>,Set<Char>,Set<Char>>>()
        (rows.indices step 3).forEach {
            rs += Triple(
                rows[it].toCharArray().toHashSet(),
                rows[it+1].toCharArray().toHashSet(),
                rows[it+2].toCharArray().toHashSet())
        }
        return rs
    }

    private fun findCoincidence(data: Triple<Set<Char>,Set<Char>,Set<Char>>): Char {
        return data
            .first.intersect(data.second)
            .intersect(data.third)
            .first()
    }

    fun getSumPriorities3Group(rows: List<String>): List<Int> {
        return group3Rows(rows)
            .map { findCoincidence(it) }
            .map { getPriority(it) }
    }
}

fun main() {

    val dataDemo = Utils.readInput("day03.demo", false)
    val dataStep = Utils.readInput("day03", false)

    println("Step 1")
    val rsDemo1 = Day03.getSumPriorities(dataDemo)
    println("Demo: ${rsDemo1.sum()} - $rsDemo1")
    val rsStep1 = Day03.getSumPriorities(dataStep)
    println("Result: ${rsStep1.sum()} - $rsStep1")
    println("Step2")
    val rsDemo2 = Day03.getSumPriorities3Group(dataDemo)
    println("Demo: ${rsDemo2.sum()} - $rsDemo2")
    val rsStep2 = Day03.getSumPriorities3Group(dataStep)
    println("Result: ${rsStep2.sum()} - $rsStep2")

}