package rresino.advent.code.year2022

import java.util.Stack

// Day 5: Supply Stacks
// https://adventofcode.com/2022/day/5
object Day05 {

    data class MovOrder(val howMany: Int, val src: Int, val dst: Int)

    data class ElfStacks(val stacks: MutableList<Stack<Char>> = mutableListOf()) {

        fun getUpperBox(): String =
            stacks.fold(""){ acc, c ->
                if (c.isNotEmpty()) {
                    acc + c.pop()
                } else {
                    acc
                }
            }

        fun add(index: Int, data: Char) {
            while (index >= stacks.size) {
                stacks.add(Stack())
            }
            stacks[index].push(data)
        }

        fun move(order: MovOrder, saveOrder: Boolean = false) {

            val aux = mutableListOf<Char>()

            repeat((1..order.howMany).count()) {
                aux.add(stacks[order.src - 1].pop())
            }

            if (saveOrder) {
                aux.reverse()
            }
            aux.forEach { stacks[order.dst - 1].push(it) }
        }

    }

    fun parseRawData(data: List<String>, saveOrder: Boolean = false): ElfStacks {
        val ix = data.indexOfFirst { it.isBlank() }
        val stacksInfo = data.take(ix-1).reversed()
        val movesInfo = data.drop(1+ix)

        val stacks = parseStacks(stacksInfo)
        //println(stacks)
        val movOrders = movesInfo.map { parseMovOrder(it) }
        movOrders.forEach { stacks.move(it, saveOrder) }
        //println(stacks)
        return stacks
    }

    private fun parseStackLine(row: String): List<Char> {
        val cols = mutableListOf<Char>()
        (1..row.length step 4)
            .forEach { i ->
                cols.add(row[i])
            }
        return cols
    }

    private fun parseStacks(data: List<String>): ElfStacks {
        val es = ElfStacks()

        data.map { parseStackLine(it) }.forEach {
            it.indices.forEach { ix ->
                if (it[ix] != ' ') {
                    es.add(ix, it[ix])
                }
            }
        }
        return es
    }

    private fun parseMovOrder(row: String): MovOrder {
        val chunks = row.split(" ")
        return MovOrder(chunks[1].toInt(), chunks[3].toInt(), chunks[5].toInt())
    }

}

fun main() {

    val dataDemo = Utils.readRawInput("day05.demo")
    val dataStep = Utils.readRawInput("day05")

    println("Step 1")
    val rsDemo1 = Day05.parseRawData(dataDemo)
    println("Demo: ${rsDemo1.getUpperBox()}")
    val rsStep1 = Day05.parseRawData(dataStep)
    println("Result: ${rsStep1.getUpperBox()}")

    println("Step2")
    val rsDemo2 = Day05.parseRawData(dataDemo, true)
    println("Demo: ${rsDemo2.getUpperBox()}")
    val rsStep2 = Day05.parseRawData(dataStep, true)
    println("Result: ${rsStep2.getUpperBox()}")

}