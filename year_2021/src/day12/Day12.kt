package day12

import readInput

fun main() {

    val rawInput =
        //readInput("day12/demo1")
        //readInput("day12/demo2")
        readInput("day12/input")

    var steps = mutableMapOf<String, Set<String>>()

    rawInput
        .filter { it.isNotEmpty() }
        .map { it.trim().split("-", limit = 2) }
        .forEach {
            val head = it[0]
            val tail = it[1]

            if (tail != "start")
                steps[head] = steps.getOrDefault(head, setOf()) + tail
            if (head != "start")
                steps[tail] = steps.getOrDefault(tail, setOf()) + head
        }

    println("Steps:")
    steps.forEach { println(it) }
    println()

    fun getPaths(steps: Map<String, Set<String>>, paths: List<String>, visits: Int): Long {
        val nodes = steps[paths.last()] ?: return 0L
        return nodes.sumOf { node ->
            if (node == "end") return@sumOf 1L
            if (node.all { it.isLowerCase() } && paths.contains(node)) {
                if (visits == 0) return@sumOf 0L
                else return@sumOf getPaths(steps, paths + node, visits - 1)
            }
            getPaths(steps, paths + node, visits)
        }
    }

    println(getPaths(steps, listOf("start"), 0))
    println(getPaths(steps, listOf("start"), 1))
}