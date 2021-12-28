import kotlin.math.abs

fun main() {

    val MAX_ITEMS = 1000
    val rawInput = readInput("Day05").filter { it.isNotBlank() }

    data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun isValid(): Boolean = isVertical() || isHorizontal() || isDiagonal()
        fun isVertical(): Boolean = x1 == x2 && y1 != y2
        fun isHorizontal(): Boolean = x1 != x2 && y1 == y2
        fun isDiagonal(): Boolean = abs(x1 - x2) == abs(y1 - y2)
    }

    data class Floor(val points: Array<IntArray>) {

        override fun toString(): String {
            return points.fold(""){acc, x -> acc + x.joinToString(" ") + "\n" }
        }

        fun addLines(line: Line) {

            if (line.isVertical()) {
                val stepY = if (line.y1 > line.y2) line.y1 downTo line.y2 else line.y1 .. line.y2
                for (y in stepY) {
                    points[y][line.x1] += 1
                }
            } else if (line.isHorizontal()) {
                val stepX = if (line.x1 > line.x2) line.x1 downTo line.x2 else line.x1 .. line.x2
                for (y in stepX) {
                    points[line.y1][y] += 1
                }
            } else if (line.isDiagonal()) {
                val stepX = if (line.x1 > line.x2) -1 else 1
                val stepY = if (line.y1 > line.y2) -1 else 1

                for (i in 0 .. abs(line.x1 - line.x2)) {
                    val x = line.x1 + (stepX * i)
                    val y = line.y1 + (stepY * i)
                    points[y][x] += 1
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
    //floor.addLines(rows[1])

    println(floor)
    println()
    println("Danger points: ${floor.howManyDangerPoints()}")

}
