fun main() {

    val MAX_ITEMS = 1000
    val rawInput = readInput("Day05").filter { it.isNotBlank() }

    data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun isValid(): Boolean = y1 == y2 || x1 == x2
        fun isVertical(): Boolean = y1 != y2
    }

    data class Floor(val points: Array<IntArray>) {

        override fun toString(): String {
            return points.fold(""){acc, x -> acc + x.joinToString(" ") + "\n" }
        }

        fun addLines(line: Line) {

            var start: Int
            var end: Int

            if (line.isVertical()) {
                if (line.y1 > line.y2) {
                    start = line.y2
                    end = line.y1
                } else {
                    start = line.y1
                    end = line.y2
                }
                for (i in start .. end) {
                    points[i][line.x1] += 1
                }
            } else {
                if (line.x1 > line.x2) {
                    start = line.x2
                    end = line.x1
                } else {
                    start = line.x1
                    end = line.x2
                }
                for (i in start .. end) {
                    points[line.y1][i] += 1
                }
            }
        }

        fun howManyDangerPoints(): Int = points.fold(0){ acc, x -> acc + x.filter { it >= 2 }.size }

    }

    fun parseRow(strRow: String): Line {

        val chunks = strRow.split(" -> ", limit = 2)
            .map { it.split(",", limit = 2).map { it.toInt() } }

        return Line(chunks[0][0], chunks[0][1], chunks[1][0], chunks[1][1])
    }

    val rows = rawInput.map { parseRow(it) }.filter { it.isValid() }
    val floor = Floor(Array(MAX_ITEMS, { IntArray(MAX_ITEMS) { 0 } }))

    rows.forEach { floor.addLines(it) }

    println(floor)
    println()
    println("Danger points: ${floor.howManyDangerPoints()}")

}
