package rresino.advent.code.year2022

// Day 8: Treetop Tree House
// https://adventofcode.com/2022/day/8
object Day08 {

    data class Tree(val height: Int, val row: Int, val column: Int, var visible:Boolean=false, var scenicScore: Long=-1L)

    data class TreeGrid(val trees: List<List<Tree>>) {

        private fun isEdge(current: Tree): Boolean =
            current.row == 0 ||
                    current.column == 0 ||
                    current.row == trees.lastIndex ||
                    current.column == trees[0].lastIndex

        private fun getTopTree(current: Tree): Tree? =
            if (current.row == 0) null
            else trees[current.row-1][current.column]

        private fun getBottomTree(current: Tree): Tree? =
            if (current.row == trees.lastIndex) null
            else trees[current.row+1][current.column]

        private fun getLeftTree(current: Tree): Tree? =
            if (current.column == 0) null
            else trees[current.row][current.column-1]

        private fun getRightTree(current: Tree): Tree? =
            if (current.column == trees[0].lastIndex) null
            else trees[current.row][current.column+1]

        private fun getVisibleTreesByLine(func: (Tree) -> Tree?,
                                          current: Tree,
                                          acc: Set<Tree> = emptySet(),
                                          maxHeight: Int = current.height,
        ): Set<Tree> {

            val next = func(current)?: return acc

            if (maxHeight == 9) {
                return acc
            }

            return if (next.height > maxHeight) {
                    getVisibleTreesByLine(func, next, acc + next, next.height)
                } else {
                    getVisibleTreesByLine(func, next, acc, maxHeight)
                }
        }

        fun getVisibleTrees():Set<Tree> {
            val topRow: Set<Tree> = trees[0].toSet()
            val bottomRow: Set<Tree> = trees[trees.size-1].toSet()
            val leftColumn: Set<Tree> = trees.map { it[0] }.toSet()
            val rightColumn: Set<Tree> = trees.map { it[it.size-1] }.toSet()
            val edgeTrees = topRow + bottomRow + leftColumn + rightColumn

            edgeTrees.forEach{ it.visible = true }

            val innerTopTrees: Set<Tree> = topRow.fold(setOf()){ acc, x -> getVisibleTreesByLine(::getBottomTree, x, acc) }
            val innerBottomTrees: Set<Tree> = bottomRow.fold(setOf()){ acc, x -> getVisibleTreesByLine(::getTopTree, x, acc) }
            val innerLeftTrees: Set<Tree> = leftColumn.fold(setOf()){ acc, x -> getVisibleTreesByLine(::getRightTree, x, acc) }
            val innerRightTrees: Set<Tree> = rightColumn.fold(setOf()){ acc, x -> getVisibleTreesByLine(::getLeftTree, x, acc) }
            val innerTrees = innerTopTrees + innerBottomTrees + innerLeftTrees + innerRightTrees
            innerTrees.forEach{ it.visible = true }

            println("edge=${edgeTrees.size}, inner=${(innerTrees-edgeTrees).size}")
            return edgeTrees + innerTrees
        }

        private fun getLineScore(current: Tree, func: (Tree) -> Tree?,): Long {
            var score = 0L
            var next = func(current)
            while (next != null) {
                score += 1
                if (next.height >= current.height) {
                    next = null
                } else {
                    next = func(next)
                }
            }
            return score
        }

        private fun calculateTreeScore(current: Tree) {

            if (current.scenicScore>-1) {
                return
            }

            val scores = listOf(
                getLineScore(current, ::getTopTree),
                getLineScore(current, ::getLeftTree),
                getLineScore(current, ::getRightTree),
                getLineScore(current, ::getBottomTree),
            )
            current.scenicScore = scores.fold(1){ acc,x -> acc * x }
        }

        fun calculateScenicScore() {
            trees.forEach { row -> row.forEach { t -> calculateTreeScore(t) } }
        }

        fun getMaxScenicScore() = trees.flatten().maxOf { it.scenicScore }

        fun show() {
            trees.forEach { x ->
                x.forEach { y ->
                    if (y.visible) {
                        print("\u001b[31m${y.height}")
                    } else {
                        print("\u001b[0m${y.height}")
                    }
                }
                println("\u001b[0m")
            }
        }

        fun showScenicScore() {
            trees.forEach { x ->
                x.forEach { y ->
                    print("${y.scenicScore}")
                }
                println()
            }
        }
    }

    private fun parseGrid(data: List<String>): TreeGrid =
        TreeGrid(data.mapIndexed { rowIx, row ->
            row.toCharArray().mapIndexed{ colIx, col ->
                Tree(col.digitToInt(), rowIx, colIx)
            }
        })

    fun solveStep1(data: List<String>): TreeGrid {
        val grid = parseGrid(data)
        val visibleTrees = grid.getVisibleTrees()
        println("Visible trees: ${visibleTrees.size}")

        return grid
    }
}

fun main() {

    val dataDemo = Utils.readRawInput("day08.demo")
    val dataStep = Utils.readRawInput("day08")

    println("Step 1 - Demo")
    val demoGrid = Day08.solveStep1(dataDemo)
    demoGrid.show()
    val grid = Day08.solveStep1(dataStep)
    grid.show()
    println()
    println("Step2")
    demoGrid.calculateScenicScore()
    println("Max demo scenic score: ${demoGrid.getMaxScenicScore()}")
    demoGrid.showScenicScore()
    grid.calculateScenicScore()
    println("Max scenic score: ${grid.getMaxScenicScore()}")

}