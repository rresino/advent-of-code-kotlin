package rresino.advent.code.year2022

// Day 1: Calorie Counting
// https://adventofcode.com/2022/day/1
object Day01 {

    data class Elf(private val foods: MutableList<Long> = mutableListOf()) {

        fun getCalories(): Long = foods.sum()

        operator fun plusAssign(food: Long) {
            foods += food
        }

    }

    fun getMaxCaloriesElf(data: List<String>, elements: Int): List<Long> {

        val elfs = data.fold(mutableListOf(Elf())){ acc, dataRow ->

            if (dataRow.isBlank()) {
                acc += Elf()
            } else {
                acc.last() += dataRow.toLong()
            }
            acc
        }

        return elfs.map { it.getCalories() }.sortedDescending().take(elements)
    }

}

fun main() {

    val dataDemo = Utils.readInput("day01.demo", false)
    val dataStep = Utils.readInput("day01", false)

    println("Step 1")
    val rsDemo1 = Day01.getMaxCaloriesElf(dataDemo, 1)
    println("Demo: ${rsDemo1[0]}")
    val rs1 = Day01.getMaxCaloriesElf(dataStep, 1)
    println("Result: ${rs1[0]}")

    println()
    println("Step 2")
    val rsDemo2 = Day01.getMaxCaloriesElf(dataDemo, 3)
    println("Demo: $rsDemo2 => ${rsDemo2.sum()}")
    val rs2 = Day01.getMaxCaloriesElf(dataStep, 3)
    println("Result: $rs2 => ${rs2.sum()}")

}