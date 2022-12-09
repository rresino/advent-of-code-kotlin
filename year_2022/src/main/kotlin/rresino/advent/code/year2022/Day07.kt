package rresino.advent.code.year2022

import kotlin.math.abs

// Day 7: No Space Left On Device
// https://adventofcode.com/2022/day/7
object Day07 {

    interface Node {
        fun size(): Long
        fun name(): String
        fun goParent(): Node
        fun addNode(child: Node)
        fun goChild(name: String): Node
        fun filterSize(minimum: Long, less: Boolean = true): List<Node>
        fun level(): Int
    }

    data class FileNode(val size: Long, val name: String, val parent: Node, val level: Int): Node {
        override fun size(): Long = size
        override fun name(): String = name
        override fun goParent(): Node = parent
        override fun addNode(child: Node) { }
        override fun goChild(name: String): Node = this
        override fun filterSize(minimum: Long, less: Boolean): List<Node> = emptyList()
        override fun level(): Int = level

        override fun toString(): String {
            return "FileNode(size=$size, name='$name')"
        }
    }

    data class DirNode(
        val name: String,
        val children: MutableList<Node> = mutableListOf(),
        val parent: Node? = null,
        val level: Int,
        ): Node {

        override fun filterSize(minimum: Long, less: Boolean): List<Node> {
            val filteredChildren: List<Node> = if (less && size() <= minimum) listOf(this)
                                   else if (size() >= minimum) listOf(this)
                                   else listOf()
            return children.fold(filteredChildren){ acc, x -> acc + x.filterSize(minimum, less) }
        }
        override fun size(): Long = children.sumOf { it.size() }
        override fun name(): String = name
        override fun goParent(): Node = parent ?: this
        override fun addNode(child: Node) {
            children.add(child)
        }
        override fun goChild(searchName: String): Node = children.first { it.name() == searchName }
        override fun toString(): String {
            return "DirNode(name='$name', size=${size()}, children=\n${"\t".repeat(level)}-$children)"
        }
        override fun level(): Int = level
    }

    fun parseNodes(cmdData: List<String>) : Node {
        val root = DirNode(name = "/", level = 1)

        var current: Node = root
        var index = 0

        while(index < cmdData.size) {
            val cmd = cmdData[index]
            val chunks = cmd.split(" ")
            when {
                cmdData[index] == "$ cd /" -> current = root
                cmdData[index] == "$ ls" -> {}
                cmdData[index] == "$ cd .." -> current = current.goParent()
                cmd.startsWith("$ cd ") -> current = current.goChild(cmd.substring(5))
                cmd.startsWith("dir ") ->
                    current.addNode(
                        DirNode(name = cmd.substring(4), parent = current, level = 1+current.level()))
                else -> current.addNode(FileNode(chunks[0].toLong(), chunks[1], current, level = 1+current.level()))
            }
            index += 1
        }
        return root
    }

}

fun main() {

    val dataDemo = Utils.readRawInput("day07.demo")
    val dataStep = Utils.readRawInput("day07")

    println("Step 1")
    val rootDemo = Day07.parseNodes(dataDemo)
    val dirsFiltered = rootDemo.filterSize(100_000L)
    println("Demo Filter directories:")
    dirsFiltered.forEach { println("\t- ${it.name()} - ${it.size()}") }
    println("Demo size: ${dirsFiltered.sumOf { it.size() }}")
    println()
    val rootStep1 = Day07.parseNodes(dataStep)
    val dirsStepFiltered = rootStep1.filterSize(100_000L)
    println("Step Filter directories:")
    dirsStepFiltered.forEach { println("\t- ${it.name()} - ${it.size()}") }
    println("Step size: ${dirsStepFiltered.sumOf { it.size() }}")
    println()
    println("Step2")
    println()
    val totalSized = 70_000_000
    val neededSize = 30_000_000
    val demoUsedSized = rootDemo.size()
    val demoSize2Free = abs((totalSized - neededSize) - demoUsedSized)

    val demoDirs2Delete = rootDemo.filterSize(demoSize2Free, false).sortedBy { it.size() }
    println("Demo Filter directories:")
    demoDirs2Delete.forEach { println("\t- ${it.name()} - ${it.size()}") }
    println("Demo dir to delete ($demoSize2Free): ${demoDirs2Delete[0].name()} => ${demoDirs2Delete[0].size()}")

    val stepUsedSized = rootStep1.size()
    val stepSize2Free = abs((totalSized - neededSize) - stepUsedSized)
    val stepDirs2Delete = rootStep1.filterSize(stepSize2Free, false).sortedBy { it.size() }
    println("Step Filter directories:")
    stepDirs2Delete.forEach { println("\t- ${it.name()} - ${it.size()}") }
    println("Demo dir to delete ($stepSize2Free): ${stepDirs2Delete[0].name()} => ${stepDirs2Delete[0].size()}")

}