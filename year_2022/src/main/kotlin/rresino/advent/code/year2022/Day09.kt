package rresino.advent.code.year2022

import java.util.InvalidPropertiesFormatException
import kotlin.math.abs

// Day 9: Rope Bridge
// https://adventofcode.com/2022/day/9
object Day09 {

    data class Node(val col: Int, val row: Int) {
        fun moveUp() = copy(row = row-1)
        fun moveDown() = copy(row = row+1)
        fun moveLeft() = copy(col = col-1)
        fun moveRight() = copy(col = col+1)

        override fun toString(): String {
            return "(c$col,r$row)"
        }
    }

    data class Rope(var head: Node, var tail: Node, val tailHistory: MutableSet<Node>) {

        fun moveUp() {
            head = head.moveUp()
            moveTail()
        }

        fun moveDown() {
            head = head.moveDown()
            moveTail()
        }

        fun moveLeft() {
            head = head.moveLeft()
            moveTail()
        }

        fun moveRight() {
            head = head.moveRight()
            moveTail()
        }

        private fun moveTail() {
            val colDiff = head.col - tail.col
            val rowDiff = head.row - tail.row

            // Same position head and tail, no move tail
            // or tail is close head
            if (head == tail || (abs(colDiff)<=1 && abs(rowDiff) <=1)) {
                // No move
                return
            }

            /*
            . H H H .   => 1st
            H . . . H   => 2nd
            H . T . H   => 3rd
            H . . . H   => 4th
            . H H H .   => 5th
             */

            val newTail = when {
                // upper row solutions
                colDiff == -1 && rowDiff == -2 ->  tail.moveUp().moveLeft()
                colDiff ==  0 && rowDiff == -2 ->  tail.moveUp()
                colDiff ==  1 && rowDiff == -2 ->  tail.moveUp().moveRight()
                // second row solutions
                colDiff == -2 && rowDiff == -1 ->  tail.moveUp().moveLeft()
                colDiff ==  2 && rowDiff == -1 ->  tail.moveUp().moveRight()
                // third row, same row of tail
                colDiff == -2 && rowDiff ==  0 ->  tail.moveLeft()
                colDiff ==  2 && rowDiff ==  0 ->  tail.moveRight()
                // fourth row
                colDiff == -2 && rowDiff ==  1 ->  tail.moveDown().moveLeft()
                colDiff ==  2 && rowDiff ==  1 ->  tail.moveDown().moveRight()
                // fifth row
                colDiff == -1 && rowDiff ==  2 ->  tail.moveDown().moveLeft()
                colDiff ==  0 && rowDiff ==  2 ->  tail.moveDown()
                colDiff ==  1 && rowDiff ==  2 ->  tail.moveDown().moveRight()
                // else
                else -> throw InvalidPropertiesFormatException("Invalid move [$head][$tail] => $rowDiff - $colDiff")
            }
            tail = newTail
            tailHistory += newTail
        }

        override fun toString(): String {
            return "Rope(head=$head, tail=$tail)"
        }
    }

    enum class MovementCode {
        U,
        R,
        D,
        L
    }

    data class MovementAction(val move: MovementCode, val times: Int)

    private fun parseMovs(rows: List<String>): List<MovementAction> =
        rows.map {
            val chunks = it.split(" ")
            MovementAction(move = MovementCode.valueOf(chunks[0]), chunks[1].toInt())}

    fun solution1(rows: List<String>) {
        val startPoint = Node(0,0)
        val rope = Rope(startPoint, startPoint, mutableSetOf(startPoint))
        val movs = parseMovs(rows)

        movs.forEach { m ->
            println("mov:$m - BEFORE - $rope")
            repeat(m.times) {
                when (m.move) {
                    MovementCode.U -> rope.moveUp()
                    MovementCode.R -> rope.moveRight()
                    MovementCode.D -> rope.moveDown()
                    MovementCode.L -> rope.moveLeft()
                }
                println("\tH:${rope.head}\tT:${rope.tail}")
            }
            println("mov:$m - AFTER - $rope")
        }
        println("Rope:$rope")
        println("Unique Positions: ${rope.tailHistory.size}")
    }
}

fun main() {

    val dataDemo = Utils.readRawInput("day09.demo")
    val dataStep = Utils.readRawInput("day09")

//    println("Step 1 - Demo data")
//    Day09.solution1(dataDemo)
    println("Step 1 - step data")
    Day09.solution1(dataStep)

    println()
    println("Step2")


}