package day11

import readInput

fun main() {

    val rawInput =
        //readInput("day11/demo")
        readInput("day11/input")

    val MAX_COL = 9
    val MAX_ROW = 9

    data class Octopus(var energy: Int, val rowIx: Int, val colIx: Int, var flashedInStep: Boolean = false) {

        fun reset() {
            if (flashedInStep) {
                energy = 0
                flashedInStep = false
            }
        }

        fun increase(): Boolean {
            energy += 1
            if (energy > 9 && !flashedInStep) {
                flashedInStep = true
                return true
            }
            return false
        }
    }

    data class Cave(val octopus: List<List<Octopus>>) {

        fun show() {
            octopus.forEach { row ->
                println(row.map { it.energy }.joinToString("-"))
            }
        }

        fun doStep(): Int {
            var countFlashes = 0

            octopus.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, octo ->
                    val flashed = octo.increase()
                    if (flashed) {
                        countFlashes += 1
                        countFlashes += expandFlashEnergy(rowIndex, colIndex)
                    }
                }
            }
            return countFlashes
        }

        fun reset() {
            octopus.forEach { row -> row.forEach { it.reset() } }
        }

        fun expandFlashEnergy(rowIx: Int, colIx: Int): Int {
            var countFlashes = 0
            val nearOctos = mutableListOf<Octopus>()
            // up - left
            if (rowIx != 0 && colIx != 0) {
                nearOctos += octopus[rowIx-1][colIx-1]
            }
            // up
            if (rowIx != 0) {
                nearOctos += octopus[rowIx-1][colIx]
            }
            // up - right
            if (rowIx != 0 && colIx != MAX_COL ) {
                nearOctos += octopus[rowIx-1][colIx+1]
            }
            // left
            if (colIx != 0) {
                nearOctos += octopus[rowIx][colIx-1]
            }
            // right
            if (colIx != MAX_COL) {
                nearOctos += octopus[rowIx][colIx+1]
            }
            // down - left
            if (rowIx != MAX_ROW && colIx != 0) {
                nearOctos += octopus[rowIx+1][colIx-1]
            }
            // down
            if (rowIx != MAX_ROW) {
                nearOctos += octopus[rowIx+1][colIx]
            }
            // down - right
            if (rowIx != MAX_ROW && colIx != MAX_COL ) {
                nearOctos += octopus[rowIx+1][colIx+1]
            }

            return nearOctos.fold(0){ acc, octo ->
                if (octo.increase()) {
                    acc + 1 + expandFlashEnergy(octo.rowIx, octo.colIx)
                } else {
                    acc
                }
            }
        }

        fun isAllFlashing(): Boolean =
            octopus.flatten().count { it.energy != 0 } == 0
    }

    val cave = Cave(
        rawInput.mapIndexed {
            rowIx, line ->
                line.toCharArray().mapIndexed { colIx, c ->
                    Octopus(c.digitToInt(), rowIx, colIx, false)
            }
        })

    /* Part 1
    val rs = (1..100).map {
        val flashed = cave.doStep()
        cave.reset()
        //println("Step $it")
        //cave.show()
        flashed
    }
    println(rs)
    println(rs.sum())
     */

    // Part 2

    var count = 0
    do {
        cave.doStep()
        cave.reset()
        count += 1
    } while (!cave.isAllFlashing())
    println("All flashing in step: $count")
}