package rresino.adventure.code.year2023

object Day03 {

    data class EngineNumber(val value: Int, val startIndex: Int) {
        fun add(partialValue: Int): EngineNumber = copy(value = "$value$partialValue".toInt())
    }

    data class EngineSchematic(val data: List<List<Char>>) {

        fun isSymbol(c: Char): Boolean =
            when(c) {
                '.' -> false
                else -> !c.isDigit()
            }

        fun IsSymbol(rowIndex: Int, colIndex: Int): Boolean =
            when {
                rowIndex < 0 -> false // out min rows
                rowIndex >= data.size -> false // out max rows
                colIndex < 0 -> false // out min cols
                colIndex >= data[0].size -> false // out max cols
                else -> isSymbol(data[rowIndex][colIndex])
            }

        fun isValidNumber(row: Int, number: EngineNumber): Boolean {

            val startIndex = number.startIndex-1
            val endIndex = number.startIndex + number.value.toString().length

            val rowIndexes = (startIndex .. endIndex).toList()

            val indexes = mutableListOf<Pair<Int,Int>>()
            rowIndexes.forEach { ix ->
                indexes += Pair(row-1, ix)
                indexes += Pair(row, ix)
                indexes += Pair(row+1, ix)
            }

            return indexes.any { ix -> IsSymbol(ix.first, ix.second) }
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
        return 0
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println()
        println("Step 1")
        println("Day 03 - Demo ${runStep1(true)}")
        println("Day 03 - ${runStep1(false)}")
        println()
//        println("Step 2")
//        println("Day 03 - Demo ${runStep2(true)}")
//        println("Day 03 - ${runStep2(false)}")
//        println()
    }

}