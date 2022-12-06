package rresino.advent.code.year2022

// Day 6: Tuning Trouble
// https://adventofcode.com/2022/day/6
object Day06 {

    fun howManyDistinctChars(line: String, targetSize: Int = 4): Int {
        (targetSize .. line.length).forEach {
            val step = line.substring(it-targetSize, it).toSet()
            if (step.size == targetSize) {
                return it
            }
        }

        return -1
    }


}

fun main() {

    val dataDemo = Utils.readRawInput("day06.demo")
    val dataStep = Utils.readRawInput("day06")

    println("Step 1")
    dataDemo.forEach {
        val rs = Day06.howManyDistinctChars(it)
        println("Demo $rs - $it")
    }
    println()
    dataStep.forEach {
        val rs = Day06.howManyDistinctChars(it)
        println("Result $rs - $it")
    }
    println()
    println("Step2")
    dataDemo.forEach {
        val rs = Day06.howManyDistinctChars(it, 14)
        println("Demo $rs - $it")
    }
    println()
    dataStep.forEach {
        val rs = Day06.howManyDistinctChars(it, 14)
        println("Result $rs - $it")
    }

}