package rresino.adventure.code.year2023

object Day01 {

    fun runStep1(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day01.demo" else "day01")
        return getCalibrationNumbers(lines)
    }

    fun runStep2(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day01.demo2" else "day01")
        val cleanLines = lines.map { replaceTextDigitsToDigits(it) }
        return getCalibrationNumbers(cleanLines)
    }

    private fun getCalibrationNumbers(lines: List<String>): Int {
        return lines.sumOf { line ->
            val onlyDigits = line.filter { it.isDigit() }
            "${onlyDigits.first()}${onlyDigits.last()}".toInt()
        }
    }

    private fun replaceTextDigitsToDigits(str: String): String {
        return str.foldIndexed("") {index, acc, c ->
            val partialStr = str.substring(index)
            when {
                c.isDigit() -> acc + c
                partialStr.startsWith("one") -> acc + "1"
                partialStr.startsWith("two") -> acc + "2"
                partialStr.startsWith("three") -> acc + "3"
                partialStr.startsWith("four") -> acc + "4"
                partialStr.startsWith("five") -> acc + "5"
                partialStr.startsWith("six") -> acc + "6"
                partialStr.startsWith("seven") -> acc + "7"
                partialStr.startsWith("eight") -> acc + "8"
                partialStr.startsWith("nine") -> acc + "9"
                else -> acc
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println()
        println("Step 1")
        println("Day 01 - Demo ${runStep1(true)}")
        println("Day 01 - ${runStep1(false)}")
        println()
        println("Step 2")
        println("Day 01 - Demo ${runStep2(true)}")
        println("Day 01 - ${runStep2(false)}")
        println()
    }

}