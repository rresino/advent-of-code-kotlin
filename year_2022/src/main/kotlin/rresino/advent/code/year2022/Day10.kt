package rresino.advent.code.year2022

import java.util.InvalidPropertiesFormatException
import kotlin.math.abs

// Day 10: Cathode-Ray Tube
// https://adventofcode.com/2022/day/10
object Day10 {

    data class Video(var value: Int, val auditCycles: MutableList<Int> = mutableListOf(),
                     private var screen: String = ""
    ) {

        private fun doAudit() {
            auditCycles += value
        }

        private fun printScreen() {
            val linePos = auditCycles.size % 40

            screen +=
                if (linePos == value-1 || linePos == value || linePos == value + 1) "#"
                else "."
        }

        private fun doNoopOrder() {
            printScreen()
            doAudit()
        }

        private fun doAddX(value2Reg: Int) {
            printScreen()
            doAudit()
            value += value2Reg
            printScreen()
            doAudit()
        }

        fun doOrder(order: String) {
            if (auditCycles.isEmpty()) {
                printScreen()
                doAudit()
            }
            if (order.startsWith("noop")) {
                doNoopOrder()
            } else {
                val value = order.substring(5).toInt()
                doAddX(value)
            }
        }

        fun getScreenResult(): List<String> = screen.chunked(40)

    }

    fun solution1(rows: List<String>) {
        val video = Video(1)

        rows.forEach {
            video.doOrder(it)
        }

        val cycles = listOf(20,60,100,140,180,220)
        val rs = cycles.sumOf { video.auditCycles.getOrElse(it-1){0} * it }
        println("Total=${rs}")
    }

    fun solution2(rows: List<String>) {
        val video = Video(1)

        rows.forEach {
            video.doOrder(it)
        }

        println("Screen:")
        video.getScreenResult().forEach { println(it) }
    }
}

fun main() {

    val dataDemo = Utils.readRawInput("day10.demo")
    val dataStep = Utils.readRawInput("day10")

    println("Step 1 - Demo data")
    Day10.solution1(dataDemo)
    println("Step 1 - step data")
    Day10.solution1(dataStep)
    println()
    println("Step 2 - Demo data")
    Day10.solution2(dataDemo)
    println("Step 2 - Step data")
    Day10.solution2(dataStep)


}