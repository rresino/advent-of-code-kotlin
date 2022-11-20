package day09

import readInput

data class Point(val row: Int, val col: Int, val value: Int): Comparable<Point> {

    override fun compareTo(other: Point): Int {
        return this.value.compareTo(other.value)
    }

}

data class Matrix(val matrix: List<List<Point>>) {

    private fun findPoint(height: Int, row: Int): Point {
        if (height < 0 || row <0 || height >= matrix.size || row >= matrix[0].size) {
            return Point(height, row, Int.MAX_VALUE)
        }
        return matrix[height][row]
    }

    private fun isLowerPoint(height: Int, row: Int): Boolean {
        val value = matrix[height][row]

        return value < findPoint(height-1, row) &&
                value < findPoint(height, row-1) &&
                value < findPoint(height, row+1) &&
                value < findPoint(height+1, row)
    }

    fun getRiskLevel(height: Int, row: Int): Int =
        matrix[height][row].value + 1

    fun getAllLowerPoints(): List<Point> {
        return matrix.mapIndexed{ h, line ->
                line.filterIndexed { r, item -> isLowerPoint(h,r) }
            }.flatten()
    }

    fun getAllRiskLevel(): List<Int> {
        return getAllLowerPoints().map { it.value + 1 }
    }

    private fun isBasin(point: Point): Boolean {
        //println("isBasin($point) = ${point.value < 9}")
        return point.value < 9
    }

    fun getBasinFromPoint(point: Point, checked: MutableSet<Point> = mutableSetOf(point)): Int {

        val nodesToCheck = setOf(
            findPoint(point.row-1, point.col),  // top
            findPoint(point.row+1, point.col),  // bottom
            findPoint(point.row, point.col-1),    // left
            findPoint(point.row, point.col+1))    // right

        val rs: List<Int> = nodesToCheck.map { p ->
            if (isBasin(p) && !checked.contains(p)) {
                checked.add(p)
                getBasinFromPoint(p, checked)
            } else {
                0
            }
        }
        return 1 + rs.sum()
    }
}

fun main() {

    val rawInput =
        //readInput("day09/demo")
        readInput("day09/input")
    val matrix = Matrix(
        rawInput.mapIndexed {
                indexRow, line ->
                    line.toCharArray().mapIndexed { indexCol, c ->
                        Point(row = indexRow, col = indexCol, value = c.digitToInt())
                    }
            })

    // Part 01
    /*
    println("Lower Points:")
    matrix.getAllLowerPoints().forEach{ println(it) }
    println("Risk Levels:")
    val riskLevels = matrix.getAllRiskLevel()
    riskLevels.forEach{ println(it) }
    println("Sum Risk Levels:")
    println(riskLevels.sum())
    */

    // Part 02
    val lowerPoints = matrix.getAllLowerPoints()
    val basins: List<Int> = lowerPoints.map {
        println("Basic => $it")
        matrix.getBasinFromPoint(it)
    }
    println("basins=>$basins")
    val selectedBasins = basins.sortedDescending().take(3)
    println("SelectedBasins=>$selectedBasins")
    val rs = selectedBasins.reduce{acc,x -> acc * x}
    println("Result: $rs")
}