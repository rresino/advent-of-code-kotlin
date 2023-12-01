package rresino.adventure.code.year2023

object Day01 {

    fun run(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day01.demo" else "day01")

        return lines.map { line ->
                val onlyDigits = line.filter { it.isDigit() }
                "${onlyDigits.first()}${onlyDigits.last()}".toInt()
            }.sum()
    }


    @JvmStatic
    fun main(args: Array<String>) {
        println("Day 01 - Demo ${run(true)}")
        println("Day 01 - ${run(false)}")
    }

}