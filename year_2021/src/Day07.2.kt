
import kotlin.math.abs

fun main() {

    //val rawInput = readInput("Day07.demo")[0].trim()
    val rawInput = readInput("Day07")[0].trim()
    var maxIndex = 0
    val chunks = rawInput.split(',').map {
        val value = it.toInt()
        if (maxIndex < value) {
            maxIndex = value
        }
        value
    }

    data class SubCrab(val from: Int, val to: Int) {
        fun getFuel(): Int {
            return getSteps(0, abs(from - to), 1)
        }

        private fun getSteps(count: Int, diff: Int, step: Int): Int {
            if (diff == 0) {
                return count
            }
            return getSteps(count + step, diff-1, step+1)
        }

    }

    fun calculateFuel(indexTo: Int, chunks: List<Int>): Int {
        val crabSubs = chunks.map {
            SubCrab(it, indexTo)
        }

        //println("Crab Submarines:")
        /*crabSubs.forEach{
            println(" - Move from ${it.from} to ${it.to}: ${it.getFuel()} fuel")
        }*/
        return crabSubs.sumOf { it.getFuel() }
    }

    var bestTo = 0
    var bestFuel = Int.MAX_VALUE
    println("How much fuel must they spend to align to that position?")
    for (i in 0 .. maxIndex) {
        val totalFuel = calculateFuel(i, chunks)
        println("$i\t$totalFuel fuel")
        if (bestFuel > totalFuel) {
            bestTo = i
            bestFuel = totalFuel
        } else {
            // results are increasing
            break
        }
    }
    println("Best to=$bestTo - fuel=$bestFuel")
}