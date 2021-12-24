fun main() {

    // val rawInput = readInput("Day01.test")
    val rawInput = readInput("Day01")
    val input = rawInput.filter { it.isNotBlank() }.map { it.trim().toInt() }
    val initValue = Pair(Integer.MAX_VALUE, 0)

    fun calculate(acc: Pair<Int, Int>, next: Int): Pair<Int, Int> {
        // println("acc=>$acc - $next")
        if (acc.first < next) {
            return Pair(next, acc.second + 1)
        }
        return acc.copy(first = next)
    }

    val result = input.fold(initValue) { acc, next -> calculate(acc, next) }

    println("How many measurements are larger than the previous measurement?")
    println(result.second)

}
