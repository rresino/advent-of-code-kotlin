package rresino.advent.code.year2022

import rresino.advent.code.year2022.Day12.PositionType.*
import java.util.LinkedList
import java.util.Queue

// Day 12: Hill Climbing Algorithm
// https://adventofcode.com/2022/day/12
object Day12 {

    enum class PositionType {
        START, END, OTHER;

        companion object {
            fun parse(c: Char): PositionType =
                when (c) {
                    'S' -> START
                    'E' -> END
                    else -> OTHER
                }
        }
    }

    data class Position(val height: Char, val row:Int, val col: Int, var typePoint: PositionType) {

        fun isValidMovement(currentHeight: Char): Boolean = currentHeight+1 >= this.height
        fun isValidMovementInverse(currentHeight: Char): Boolean = currentHeight <= this.height+1

        fun isEnd() = this.typePoint == END
        fun isStart() = this.typePoint == START

    }

    data class Step(val position: Position, val distance:Int) {
        fun next(position: Position): Step = Step(position, distance+1)
    }

    data class Heightmap(val grid: List<List<Position>>) {

        var maxLengthPath = Integer.MAX_VALUE

        fun findFirstPoint(typePoint: PositionType): Position? {
            var item: Position? = null
            grid.forEach { row ->
                row.forEach {
                    if (it.typePoint == typePoint) {
                        item = it
                    }
                }
            }
            return item
        }

        fun getValidMovements(init: Position, visitedPaths: Set<Position>, inverse: Boolean): Set<Position> {

            val positions = mutableSetOf<Position>()

            // left
            if (init.col > 0) {
                positions += grid[init.row][init.col-1]
            }
            // right
            if (init.col < grid[0].lastIndex) {
                positions += grid[init.row][init.col+1]
            }
            // up
            if (init.row > 0) {
                positions += grid[init.row-1][init.col]
            }
            // down
            if (init.row < grid.lastIndex) {
                positions += grid[init.row+1][init.col]
            }

            return positions.filter {
                (!inverse && it.isValidMovement(init.height) || inverse && it.isValidMovementInverse(init.height))
                        && !visitedPaths.contains(it) }.toSet()
        }

        companion object {
            fun parse(rawData: List<String>): Heightmap {
                val grid = rawData.mapIndexed{ rowIx, s ->
                    s.toCharArray().mapIndexed { colIx, c ->
                        val height = when(c) {
                                'S'  -> 'a'
                                'E'  -> 'z'
                                else -> c
                            }
                        val positionType = PositionType.parse(c)
                        Position(height, rowIx, colIx, positionType)
                    }
                }
                return Heightmap(grid)
            }
        }

        fun breadthFirstSearch(inverse: Boolean, isDestination: (Position) -> Boolean):Int {

            val visitedPaths = mutableSetOf<Position>()
            val stepsToCheck: Queue<Step> = LinkedList()
            val startPosition = findFirstPoint(if (!inverse) START else END)!!

            visitedPaths += startPosition
            stepsToCheck += Step(startPosition,0)

            while (stepsToCheck.isNotEmpty()) {
                val step = stepsToCheck.poll()
                //println("Checking step $step - Queue $stepsToCheck - Visited $visitedPaths")

                // Check if it's the end of search
                if (isDestination(step.position)) {
                    return step.distance
                }

                getValidMovements(step.position, visitedPaths, inverse).forEach {
                    stepsToCheck.add(step.next(it))
                    visitedPaths += it
                }
            }
            throw IllegalStateException("Something wrong on solution")
        }

    }

    fun solution1(data: List<String>) {

        val heightmap = Heightmap.parse(data)

        // BreadthFirstSearch
        val paths1 = heightmap.breadthFirstSearch(false){ it.typePoint == END}
        println("Fewest steps: $paths1")
        val paths2 = heightmap.breadthFirstSearch(true){ it.typePoint == START}
        println("Fewest steps (inverse): $paths2")
    }

    fun solution2(data: List<String>) {
        val heightmap = Heightmap.parse(data)
        val paths = heightmap.breadthFirstSearch(true){ it.height == 'a'}
        println("Fewest steps: $paths")
    }

}

fun main() {

    val dataDemo = Utils.readRawInput("day12.demo")
    val dataStep = Utils.readRawInput("day12")

    println("Step 1 - Demo data")
    Day12.solution1(dataDemo)
    println("Step 1 - step data")
    Day12.solution1(dataStep)

    println()
    println("Step 2 - Demo data")
    Day12.solution2(dataDemo)
    println("Step 2 - Step data")
    Day12.solution2(dataStep)
}