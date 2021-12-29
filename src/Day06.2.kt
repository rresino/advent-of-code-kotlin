
fun main() {

    val rawInput = readInput("Day06")[0].trim()
    val fishes = rawInput.split(',').map { it.toLong() }

    fun evolve(data: MutableMap<Long, Long>) {

        val sons = data.getOrDefault(0, 0)

        for (i in 1L .. 8L) {
            data[i - 1] = data.getOrDefault(i, 0)
        }
        data[8] = sons
        data[6] = data.getOrDefault(6, 0) + sons
    }

    fun evolveAll(days: Int, data: MutableMap<Long, Long>) {
        for (i in 0 until days) {
            evolve(data)
        }
    }

    val days = 256
    val group = fishes.groupBy { it }.mapValues { it.value.size.toLong() }.toMutableMap()

    evolveAll(256, group)
    val rs = group.values.sum()

    println("Days $days => $rs")

}