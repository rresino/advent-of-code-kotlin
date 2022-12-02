package rresino.advent.code.year2022

import rresino.advent.code.year2022.Day02.Game.*
import rresino.advent.code.year2022.Day02.Result.*

object Day02 {

    enum class Result(val score: Int) {
        Win(6),
        Draw(3),
        Lost(0);

        companion object {
            fun parse(value: String): Result =
                when (value.uppercase()) {
                    "X" -> Lost
                    "Y" -> Draw
                    else -> Win
                }
        }
    }

    enum class Game(val score: Int) {
        Rock(1),
        Paper(2),
        Scissors(3);

        companion object {
            fun parse(value: String): Game =
                when (value.uppercase()) {
                    "A", "X" -> Rock
                    "B", "Y" -> Paper
                    else -> Scissors
                }
        }
    }

    private fun calculateResult(player1: Game, player2: Game): Result =
        when {
            player1 === player2 -> Draw
            player1 == Rock && player2 == Paper -> Win
            player1 == Paper && player2 == Scissors -> Win
            player1 == Scissors && player2 == Rock -> Win
            else -> Lost
        }

    private fun guestGame(player1: Game, result: Result): Game =
        when(Pair(player1, result)) {
            Pair(Rock, Win) -> Paper
            Pair(Rock, Lost) -> Scissors
            Pair(Paper, Win) -> Scissors
            Pair(Paper, Lost) -> Rock
            Pair(Scissors, Win) -> Rock
            Pair(Scissors, Lost) -> Paper
            else -> player1
        }

    private fun calculateScore(player: Game, result: Result): Int =
        player.score + result.score

    private fun parse(row: String): Pair<Game, Game> {
        val chunks = row.split(" ")
        return Pair(Game.parse(chunks[0]), Game.parse(chunks[1]))
    }

    private fun parseAndGuest(row: String): Pair<Game, Result> {
        val chunks = row.split(" ")
        return Pair(Game.parse(chunks[0]), Result.parse(chunks[1]))
    }

    fun parseAndGetScore(row: String): Int {
        val games = parse(row)
        val result = calculateResult(games.first, games.second)
        return calculateScore(games.second, result)
    }

    fun parseGuestAndGetScore(row: String): Int {
        val games = parseAndGuest(row)
        val result = guestGame(games.first, games.second)
        return calculateScore(result, games.second)
    }

}

fun main() {

    val dataDemo = Utils.readInput("day02.demo")
    val dataStep = Utils.readInput("day02")

    println("Step 1")
    val rs1 = dataDemo.map { Day02.parseAndGetScore(it) }
    println("Demo: $rs1 => ${rs1.sum()}")
    val rs2 = dataStep.map { Day02.parseAndGetScore(it) }
    println("Result: $rs2 => ${rs2.sum()}")

    println()
    println("Step 2")
    val rs3 = dataDemo.map { Day02.parseGuestAndGetScore(it) }
    println("Demo: $rs3 => ${rs3.sum()}")
    val rs4 = dataStep.map { Day02.parseGuestAndGetScore(it) }
    println("Result: $rs4 => ${rs4.sum()}")

}