package rresino.adventure.code.year2023

object Day02 {

    data class GameRules(val maxRed: Int, val maxGreen: Int, val maxBlue: Int)

    data class Game(val id: Int, val gameSets: List<GameSet> = listOf()) {

        fun validate(rules: GameRules): Boolean =
            gameSets.all { it.validate(rules) }

        companion object {
            fun create(gameInfo: String, setInfo: String) : Game {
                return Game(
                    id = gameInfo.substring(5).toInt(),
                    gameSets = setInfo.split(";").map { s -> GameSet().loadLineOfSet(s) }
                )
            }
        }
    }

    data class GameSet(val red: Int = 0, val green: Int = 0, val blue: Int = 0) {

        fun validate(rules: GameRules): Boolean =
            red <= rules.maxRed && green <= rules.maxGreen && blue <= rules.maxBlue

        fun loadLineOfSet(line: String): GameSet {
            return line.split(",")
                .fold(this){ acc, str -> acc.addCubes(str.trim()) }
        }

        fun addCubes(setCubes: String): GameSet {
            val chunks = setCubes.split(" ")
            val color = chunks[1]
            val numberOfCubes = chunks[0].toInt()

            return when(color) {
                "red" -> copy(red = red + numberOfCubes)
                "green" -> copy(green = green + numberOfCubes)
                "blue" -> copy(blue = blue + numberOfCubes)
                else -> this
            }
        }

    }

    fun runStep1(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day02.demo" else "day02")
        val games = parseLines(lines)

        val rules = GameRules(maxRed = 12, maxGreen = 13, maxBlue = 14)

        return games.filter { it.validate(rules) }.sumOf { it.id }
    }

    private fun parseLines(lines: List<String>): List<Game> {

        val games = lines.map { line ->
            val (gameId, lineSets) = line.split(":")
            Game.create(gameId, lineSets)
        }

        return games
    }

    fun runStep2(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day02.demo2" else "day02")
        return 0
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println()
        println("Step 1")
        println("Day 02 - Demo ${runStep1(true)}")
        println("Day 02 - ${runStep1(false)}")
        println()
//        println("Step 2")
//        println("Day 02 - Demo ${runStep2(true)}")
//        println("Day 02 - ${runStep2(false)}")
        println()
    }

}