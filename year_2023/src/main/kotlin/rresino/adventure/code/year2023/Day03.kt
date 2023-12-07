package rresino.adventure.code.year2023

object Day03 {

    data class EngineNumber(val value: Int, val startIndex: Int) {

        fun add(partialValue: Int): EngineNumber = copy(value = "$value$partialValue".toInt())

        fun endIndex(): Int = startIndex + value.toString().length - 1
    }

    data class EngineSchematic(val data: List<List<Char>>) {

        fun isSymbol(c: Char): Boolean =
            when(c) {
                '.' -> false
                else -> !c.isDigit()
            }

        fun isSymbol(rowIndex: Int, colIndex: Int): Boolean =
            when {
                rowIndex < 0 -> false // out min rows
                rowIndex >= data.size -> false // out max rows
                colIndex < 0 -> false // out min cols
                colIndex >= data[0].size -> false // out max cols
                else -> isSymbol(data[rowIndex][colIndex])
            }

        fun isNumber(pair: Pair<Int, Int>): Boolean = isNumber(pair.first, pair.second)

        fun isNumber(rowIndex: Int, colIndex: Int): Boolean =
            when {
                rowIndex < 0 -> false // out min rows
                rowIndex >= data.size -> false // out max rows
                colIndex < 0 -> false // out min cols
                colIndex >= data[0].size -> false // out max cols
                else -> data[rowIndex][colIndex].isDigit()
            }

        private fun getUpperLeft(pair: Pair<Int, Int>): Pair<Int, Int> = getUpperLeft(pair.first, pair.second)
        private fun getUpperMid(pair: Pair<Int, Int>): Pair<Int, Int> = getUpperMid(pair.first, pair.second)
        private fun getUpperRight(pair: Pair<Int, Int>): Pair<Int, Int> = getUpperRight(pair.first, pair.second)
        private fun getLeft(pair: Pair<Int, Int>): Pair<Int, Int> = getLeft(pair.first, pair.second)
        private fun getRight(pair: Pair<Int, Int>): Pair<Int, Int> = getRight(pair.first, pair.second)
        private fun getBottomLeft(pair: Pair<Int, Int>): Pair<Int, Int> = getBottomLeft(pair.first, pair.second)
        private fun getBottomMid(pair: Pair<Int, Int>): Pair<Int, Int> = getBottomMid(pair.first, pair.second)
        private fun getBottomRight(pair: Pair<Int, Int>): Pair<Int, Int> = getBottomRight(pair.first, pair.second)

        private fun getUpperLeft(rowIndex: Int, colIndex: Int): Pair<Int, Int> =    Pair(rowIndex-1, colIndex-1)
        private fun getUpperMid(rowIndex: Int, colIndex: Int): Pair<Int, Int> =     Pair(rowIndex-1, colIndex)
        private fun getUpperRight(rowIndex: Int, colIndex: Int): Pair<Int, Int> =   Pair(rowIndex-1, colIndex+1)
        private fun getLeft(rowIndex: Int, colIndex: Int): Pair<Int, Int> =         Pair(rowIndex, colIndex-1)
        private fun getRight(rowIndex: Int, colIndex: Int): Pair<Int, Int> =        Pair(rowIndex, colIndex+1)
        private fun getBottomLeft(rowIndex: Int, colIndex: Int): Pair<Int, Int> =   Pair(rowIndex+1, colIndex-1)
        private fun getBottomMid(rowIndex: Int, colIndex: Int): Pair<Int, Int> =    Pair(rowIndex+1, colIndex)
        private fun getBottomRight(rowIndex: Int, colIndex: Int): Pair<Int, Int> =  Pair(rowIndex+1, colIndex+1)

        fun isValidNumber(row: Int, number: EngineNumber): Boolean {

            val startIndex = number.startIndex - 1
            val endIndex = number.endIndex() + 1

            val rowIndexes = (startIndex .. endIndex).toList()

            val indexes = mutableListOf<Pair<Int,Int>>()
            rowIndexes.forEach { ix ->
                indexes += Pair(row-1, ix)
                indexes += Pair(row, ix)
                indexes += Pair(row+1, ix)
            }

            return indexes.any { ix -> isSymbol(ix.first, ix.second) }
        }

        fun getIndexesOfAsterix(): List<Pair<Int, Int>> {
            val positions = mutableListOf<Pair<Int, Int>>()
            data.mapIndexed { rowIndex, row ->
                row.mapIndexed { colIndex, c ->
                    if (c == '*') {
                        positions += Pair(rowIndex, colIndex)
                    }
                }
            }
            return positions
        }

        fun getNearNumbers(position: Pair<Int, Int>): List<Int> {

            val results = mutableListOf<Int>()

            // Top
            val top = Triple(
                isNumber(getUpperLeft(position)),
                isNumber(getUpperMid(position)),
                isNumber(getUpperRight(position))
            )

            when(top) {
                Triple(false, false, false) -> {}// do nothing
                Triple(true, true, true) -> results += getNumberFromIndex(getUpperLeft(position))
                // 2
                Triple(true, true, false) -> results += getNumberFromIndex(getUpperLeft(position))
                Triple(false, true, true) -> results += getNumberFromIndex(getUpperMid(position))
                // 2 numbers same row
                Triple(true, false, true) -> {
                    results += getNumberFromIndex(getUpperLeft(position))
                    results += getNumberFromIndex(getUpperRight(position))
                }
                // Only 1
                Triple(true, false, false) -> results += getNumberFromIndex(getUpperLeft(position))
                Triple(false, true, false) -> results += getNumberFromIndex(getUpperMid(position))
                Triple(false, false, true) -> results += getNumberFromIndex(getUpperRight(position))
            }

            if (isNumber(getLeft(position))) {
                results += getNumberFromIndex(getLeft(position))
            }
            if (isNumber(getRight(position))) {
                results += getNumberFromIndex(getRight(position))
            }

            // Bottom
            val bottom = Triple(
                isNumber(getBottomLeft(position)),
                isNumber(getBottomMid(position)),
                isNumber(getBottomRight(position))
            )

            when(bottom) {
                Triple(false, false, false) -> {}// do nothing
                Triple(true, true, true) -> results += getNumberFromIndex(getBottomLeft(position))
                // 2
                Triple(true, true, false) -> results += getNumberFromIndex(getBottomLeft(position))
                Triple(false, true, true) -> results += getNumberFromIndex(getBottomMid(position))
                // 2 numbers same row
                Triple(true, false, true) -> {
                    results += getNumberFromIndex(getBottomLeft(position))
                    results += getNumberFromIndex(getBottomRight(position))
                }
                // only 1
                Triple(true, false, false) -> results += getNumberFromIndex(getBottomLeft(position))
                Triple(false, true, false) -> results += getNumberFromIndex(getBottomMid(position))
                Triple(false, false, true) -> results += getNumberFromIndex(getBottomRight(position))
            }

            return results
        }

        fun getNumberFromIndex(pair: Pair<Int, Int>): Int = getNumberFromIndex(pair.first, pair.second)

        fun getNumberFromIndex(rowIndex: Int, colIndex: Int): Int {
            if (!isNumber(rowIndex, colIndex)) {
                return -1
            }

            var content = ""

            loop@ for (i in colIndex downTo 0) {
                if (data[rowIndex][i].isDigit()) {
                    content = data[rowIndex][i] + content
                } else {
                    break@loop
                }
            }

            if (colIndex < data[0].size - 1) {
                loop@ for (i in colIndex + 1 until data[rowIndex].size) {
                    if (data[rowIndex][i].isDigit()) {
                        content += data[rowIndex][i]
                    } else {
                        break@loop
                    }
                }
            }
            return content.toInt()
        }

        companion object {
            fun parseInputFileToData(fileInput: List<String>): EngineSchematic {
                return EngineSchematic(
                    fileInput.map { parseInputLineToEngineSchematicRow(it) })
            }

            fun parseInputLineToEngineSchematicRow(strLine: String): List<Char> =
                strLine.toList()

            fun findNumbersInRow(row: List<Char>): List<EngineNumber> {
                return innerFindNumbersInRow(row, 0, listOf(), null)
            }

            private fun innerFindNumbersInRow(row: List<Char>,
                                              index: Int,
                                              numbers: List<EngineNumber>,
                                              tempNumbers: EngineNumber?): List<EngineNumber> {

                if (row.isEmpty()) {
                    return tempNumbers?.let { numbers + tempNumbers } ?: numbers
                }

                val head = row.first()

                return if (head.isDigit()) {
                        val newTmpNumber = tempNumbers?.add(head.digitToInt())?:EngineNumber(head.digitToInt(), index)
                        innerFindNumbersInRow(row.drop(1), index + 1, numbers, newTmpNumber)
                    } else if (tempNumbers != null) {
                        innerFindNumbersInRow(row.drop(1), index + 1, numbers + tempNumbers, null)
                    } else {
                        innerFindNumbersInRow(row.drop(1), index + 1, numbers, null)
                    }

            }
        }
    }

    fun runStep1(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day03.demo" else "day03")

        val engineSchematic = EngineSchematic.parseInputFileToData(lines)
        return engineSchematic.data
            .mapIndexed { index, chars ->
                EngineSchematic.findNumbersInRow(chars).filter { engineSchematic.isValidNumber(index, it) } }
            .fold(0){ acc, engineNumbers -> acc + engineNumbers.sumOf { it.value } }
    }

    fun runStep2(useDemo: Boolean): Int {
        val lines = Utils.readCleanInput(if (useDemo) "day03.demo" else "day03")
        val engineSchematic = EngineSchematic.parseInputFileToData(lines)

        val asterixPos = engineSchematic.getIndexesOfAsterix()

        val nearAsterixNumbers = asterixPos.map { position -> engineSchematic.getNearNumbers(position) }
            .filter { it.size == 2 }

        return nearAsterixNumbers.sumOf { row -> row.reduce(Int::times) }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println()
        println("Step 1")
        println("Day 03 - Demo ${runStep1(true)}")
        println("Day 03 - ${runStep1(false)}")
        println()
        println("Step 2")
        println("Day 03 - Demo ${runStep2(true)}")
        println("Day 03 - ${runStep2(false)}")
        println()

    }

}