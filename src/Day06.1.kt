
fun main() {

    val rawInput = readInput("Day06")[0].trim()
    val fishes = rawInput.split(',').map { it.toInt() }

    fun evolve(fish: Int): Int {
        return when(fish) {
            0       -> 6
            else    -> fish - 1
        }
    }

    fun evolveAll(fishes: List<Int>): List<Int> {
        val rs = fishes.map { evolve(it) }
        val numFishesCount = fishes.count { it == 0 }

        return rs + List(numFishesCount) { 8 }
    }
    fun evolveAll(days: Int, fishes: List<Int>): List<Int> {
        var rs = fishes
        for (i in 0 until days) {
            rs = evolveAll(rs)
        }
        return rs
    }

    val day18 = evolveAll(18, fishes)
    val day80 = evolveAll(80, fishes)

    println("Day  1 with (${fishes.size}): $fishes")
    println("Day 18 with (${day18.size}): $day18")
    println("Day 80 with (${day80.size}): $day80")

}