fun main() {

    // val rawInput = readInput("Day02.test")
    val rawInput = readInput("Day02")
    val input = rawInput.filter { it.isNotBlank() }.map { it.trim().lowercase().split(" ", limit = 2) }

    data class Submarine(val depth: Int = 0, val horizontal: Int = 0) {

        fun result(): Int = depth * horizontal

        fun move(action: String, steps: Int): Submarine {
            return when(action) {
                "forward" -> copy(horizontal = horizontal + steps)
                "down" -> copy(depth = depth + steps)
                "up" -> copy(depth = depth - steps)
                else -> this
            }
        }
    }


    val result = input.fold(Submarine()) { acc, next -> acc.move(next[0], next[1].toInt()) }

    println("How many measurements are larger than the previous measurement?")
    println("$result => ${result.result()}")

}
