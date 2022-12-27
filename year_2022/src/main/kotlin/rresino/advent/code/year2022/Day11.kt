package rresino.advent.code.year2022

import java.lang.IllegalArgumentException
import rresino.advent.code.year2022.Utils.lcm

// Day 11: Monkey in the Middle
// https://adventofcode.com/2022/day/11
object Day11 {

    interface ActionAfterInspect {
        fun apply(value: Long): Long
    }

    class DivisionAfterInspect(private val divisor: Long): ActionAfterInspect {
        override fun apply(value: Long): Long = value / divisor
    }

    class ModulusAfterInspect(private val modulus: Long): ActionAfterInspect {
        override fun apply(value: Long): Long = value % modulus
    }

    interface Operation {
        fun apply(value: Long): Long

        companion object {
            fun parse(operator: Char, number: String): Operation {
                val numberValue: Long? = if (number == "old") null else number.toLong()
                return when (operator) {
                    '+' -> SumOperation(numberValue)
                    '-' -> LessOperation(numberValue)
                    '*' -> MultipleOperation(numberValue)
                    '/' -> DivideOperation(numberValue)
                    '\\' -> DivideOperation(numberValue)
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    data class SumOperation(val number: Long?): Operation {
        override fun apply(value: Long): Long = value + (number ?: value)
    }

    data class LessOperation(val number: Long?): Operation {
        override fun apply(value: Long): Long = value - (number ?: value)
    }

    data class MultipleOperation(val number: Long?): Operation {
        override fun apply(value: Long): Long = value * (number ?: value)
    }

    data class DivideOperation(val number: Long?): Operation {
        override fun apply(value: Long): Long = value / (number ?: value)
    }

    data class Item(var value: Long) {

        fun inspect(operation: Operation) {
            this.value = operation.apply(value)
        }

        fun actionAfterInspect(aai: ActionAfterInspect) {
            this.value = aai.apply(value)
        }

        fun doTest(srcMonkey: Monkey, allMonkeys: Map<Int, Monkey>) {
            val throwTo =
                if (value % srcMonkey.testIsDivisible == 0L) srcMonkey.ifTrueThrow else srcMonkey.ifFalseThrow
            allMonkeys[throwTo]!!.itemThrown(this)
        }
    }

    data class Monkey(val name: Int,
                      val items: MutableList<Item>,
                      val operation: Operation,
                      val testIsDivisible: Long,
                      val ifTrueThrow: Int,
                      val ifFalseThrow: Int) {

        private var inspectCount = 0L

        fun itemThrown(item: Item) {
            items += item
        }

        fun isEmpty() = items.isEmpty()

        fun doInspect(allMonkeys: Map<Int, Monkey>, aai: ActionAfterInspect) {
            val head = items[0]
            items.removeFirst()

            head.inspect(operation)
            inspectCount++
            head.actionAfterInspect(aai)
            head.doTest(this, allMonkeys)
        }

        fun getInspectCount() = inspectCount

        companion object {
            fun parseRawData(dataDemo: List<String>): Map<Int, Monkey> {
                val allMonkeys = mutableMapOf<Int, Monkey>()

                dataDemo.chunked(7).map { it ->
                    val name = it[0].split("Monkey ",":").filter { it.isNotBlank() }[0].toInt()
                    val items = it[1].split("  Starting items: ",", ")
                        .filter { it.isNotBlank() }
                        .map { Item(it.toLong()) }
                        .toMutableList()

                    val operationChunks = it[2].split("  Operation: new = old ", " ")
                        .filter { it.isNotBlank() }

                    val divisible = it[3].split("  Test: divisible by ")
                        .filter { it.isNotBlank() }[0].toLong()
                    val ifTrue = it[4].split("    If true: throw to monkey ")
                        .filter { it.isNotBlank() }[0].toInt()
                    val ifFalse = it[5].split("    If false: throw to monkey ")
                        .filter { it.isNotBlank() }[0].toInt()

                    Monkey(name = name, items = items,
                        operation = Operation.parse(operationChunks[0][0], operationChunks[1]),
                        testIsDivisible = divisible, ifTrueThrow = ifTrue, ifFalseThrow = ifFalse)
                }.forEach { allMonkeys += it.name to it }

                return allMonkeys
            }
        }
    }

    private fun showMonkeys(monkeys: Map<Int, Monkey>) {
        monkeys.keys.sorted().forEach {
            val m = monkeys[it]!!
            println("Monkey ${m.name} inspected items ${m.getInspectCount()} times.")
        }
    }

    fun solution(dataDemo: List<String>, numberRounds: Int, step1: Boolean, debug: Boolean) {

        val allMonkeys = Monkey.parseRawData(dataDemo)

        val aai = if (step1) {
                DivisionAfterInspect(3L)
            } else {
                val lcm = allMonkeys.values.map { it.testIsDivisible }.lcm()
                ModulusAfterInspect(lcm)
            }

        repeat(numberRounds) { ix ->
            allMonkeys.keys.sorted().forEach {
                val m = allMonkeys[it]

                while (!m!!.isEmpty()) {
                    m.doInspect(allMonkeys, aai)
                }
            }
            if (debug && 1+ix in listOf(1, 20, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000)) {
                println("== After round ${1+ix} ==")
                showMonkeys(allMonkeys)
                println()
            }
        }

        val inspected = allMonkeys.values.map { it.getInspectCount() }.sortedDescending().take(2)
        val activity = inspected[0] * inspected[1]
        println("Activity: $activity")
    }

}

fun main() {

    val dataDemo = Utils.readRawInput("day11.demo")
    val dataStep = Utils.readRawInput("day11")

    println("Step 1 - Demo data")
    Day11.solution(dataDemo, 20, true, false)
    println("Step 1 - step data")
    Day11.solution(dataStep, 20, true, false)

    println()
    println("Step 2 - Demo data")
    Day11.solution(dataDemo, 10_000, false, false)
    println("Step 2 - Step data")
    Day11.solution(dataStep, 10_000, false, false)

}