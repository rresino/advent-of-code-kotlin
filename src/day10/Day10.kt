package day10

import readInput
import java.util.*

fun main() {

    val rawInput =
        //readInput("day10/demo")
        readInput("day10/input")

    fun isOpenChar(c: Char): Boolean  = (c == '(' || c == '[' || c == '{' || c == '<')
    fun isCloseChar(c: Char): Boolean  = (c == ')' || c == ']' || c == '}' || c == '>')
    fun isValidClose(openChar: Char, closeChar: Char) =
        when (openChar) {
            '(' -> closeChar == ')'
            '[' -> closeChar == ']'
            '{' -> closeChar == '}'
            '<' -> closeChar == '>'
            else -> false
        }

    fun getScore(c: Char) =
        when (c) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }

    fun getIncompleteScore(pendingOperator: Deque<Char>): Long =
        pendingOperator.fold(0L) {
            acc, c ->
                acc * 5L + when(c) {
                    '(' -> 1L
                    '[' -> 2L
                    '{' -> 3L
                    '<' -> 4L
                    else -> 0L
                }
        }

    fun getCorruptedScoring(input: List<String>) =
        input.map { line ->
            val operator: Deque<Char> = LinkedList()

            line.forEach {
                if (isOpenChar(it)) {
                    operator.push(it)
                } else if (isCloseChar(it)) {
                    val lastChar = operator.pop()
                    if (!isValidClose(lastChar, it)) {
                        val score = getScore(it)
                        println("invalid close: $operator and $it ==> score=$score")
                        return@map score
                    }
                }
            }
            if (operator.isNotEmpty()) {
                println("Incomplete line: $operator")
            } else {
                println("Line ok")
            }
            return@map 0
        }.filter { it > 0 }

    fun getIncompleteScoring(input: List<String>) =
        input.map { line ->
            val operator: Deque<Char> = LinkedList()

            line.forEach {
                if (isOpenChar(it)) {
                    operator.push(it)
                } else if (isCloseChar(it)) {
                    val lastChar = operator.pop()
                    if (!isValidClose(lastChar, it)) {
                        return@map 0
                    }
                }
            }
            if (operator.isNotEmpty()) {
                println("Incomplete line: $operator")
                return@map getIncompleteScore(operator)
            }
            println("Line ok")
            return@map 0
        }.filter { it > 0 }

//    println()
//    println("Part 1:")
//    val scoring = getCorruptedScoring(rawInput)
//    println("Scoring: $scoring")
//    println("Scoring Total: ${scoring.sum()}")
    println()
    println("Part 2:")
    val scoring = getIncompleteScoring(rawInput)
    println("Scoring: $scoring")
    val winner = scoring.sorted()[scoring.size/2]
    println("Winner: $winner")
}