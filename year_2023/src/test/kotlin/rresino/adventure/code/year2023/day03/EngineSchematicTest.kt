package rresino.adventure.code.year2023.day03

import org.junit.jupiter.api.Test
import rresino.adventure.code.year2023.Day03
import rresino.adventure.code.year2023.Day03.EngineSchematic
import strikt.api.*
import strikt.assertions.*
import java.io.File

class EngineSchematicTest {

    private val day03Lines = File("src/main/resources", "day03.txt").readLines()

    @Test
    fun `load a string from file and parse it to line for all engine schematic`() {
        val data = "467..114.."
        val rs = EngineSchematic.parseInputLineToEngineSchematicRow(data)

        expectThat(rs)
            .isEqualTo(listOf('4','6','7','.','.','1','1','4','.','.'))
    }

    @Test
    fun `load a string from file and create a engine schematic with all the columns and rows`() {
        val data = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...\$.*....",
            ".664.598..",
        )

        val rs = EngineSchematic.parseInputFileToData(data)

        expect {
            that(rs.data.size).isEqualTo(10)
            rs.data.forEach { row ->
                that(row.size).isEqualTo(10)
            }
            that(rs.data[0])
                .isEqualTo(listOf('4','6','7','.','.','1','1','4','.','.'))

        }

    }

    @Test
    fun `find Numbers in one row of engine schematic`() {
        val data = "467..114..".toList()
        val rs = EngineSchematic.findNumbersInRow(data)

        expectThat(rs)
            .isEqualTo(
                listOf(
                    Day03.EngineNumber(467, 0),
                    Day03.EngineNumber(114, 5)
                ))
    }

    @Test
    fun `check if symbol is inside matrix of engine schematic`() {
        val data = listOf("4")
        val engineSchematic = EngineSchematic.parseInputFileToData(data)

        expectThat(engineSchematic.isSymbol(-2, 0)).isFalse()
        expectThat(engineSchematic.isSymbol(-1, 0)).isFalse()
        expectThat(engineSchematic.isSymbol(0, -2)).isFalse()
        expectThat(engineSchematic.isSymbol(0, -1)).isFalse()
        expectThat(engineSchematic.isSymbol(-1, -1)).isFalse()

    }

    @Test
    fun `check if symbol is inside matrix and it's a symbol`() {
        val data = listOf(".*.")
        val engineSchematic = EngineSchematic.parseInputFileToData(data)

        expectThat(engineSchematic.isSymbol(0, 0)).isFalse()
        expectThat(engineSchematic.isSymbol(0, 1)).isTrue()
        expectThat(engineSchematic.isSymbol(0, 2)).isFalse()
    }

    @Test
    fun `check if number on left must be valid because has a symbol on right side`() {
        val data = listOf("467*.......")
        val engineSchematic = EngineSchematic.parseInputFileToData(data)
        val numberToCheck = Day03.EngineNumber(value = 467, startIndex = 0)
        val rs = engineSchematic.isValidNumber(row = 0, numberToCheck)

        expectThat(rs).isTrue()
    }

    @Test
    fun `check if number must be valid because has a symbol on left side`() {
        val data = listOf(".....#467...")
        val engineSchematic = EngineSchematic.parseInputFileToData(data)
        val numberToCheck = Day03.EngineNumber(value = 467, startIndex = 6)
        val rs = engineSchematic.isValidNumber(row = 0, numberToCheck)

        expectThat(rs).isTrue()
    }

    @Test
    fun `check if number on left must be invalid because has not any symbol near`() {
        val data = listOf(
            "..........",
            "467.......",
            "..........",
        )
        val engineSchematic = EngineSchematic.parseInputFileToData(data)
        val numberToCheck = Day03.EngineNumber(value = 467, startIndex = 0)
        val rs = engineSchematic.isValidNumber(row = 1, numberToCheck)

        expectThat(rs).isFalse()
    }

    @Test
    fun `check if number on left must be valid because has symbol on top`() {
        val data = listOf(
            ".*........",
            "467.......",
            "..........",
        )
        val engineSchematic = EngineSchematic.parseInputFileToData(data)
        val numberToCheck = Day03.EngineNumber(value = 467, startIndex = 0)
        val rs = engineSchematic.isValidNumber(row = 1, numberToCheck)

        expectThat(rs).isTrue()
    }


    @Test
    fun `check if number on left must be valid because has symbol on bottom`() {
        val data = listOf(
            "..........",
            "467.......",
            "..#.......",
        )
        val engineSchematic = EngineSchematic.parseInputFileToData(data)
        val numberToCheck = Day03.EngineNumber(value = 467, startIndex = 0)
        val rs = engineSchematic.isValidNumber(row = 1, numberToCheck)

        expectThat(rs).isTrue()
    }

    @Test
    fun `get all the engines from long data file`() {
        val data = listOf(
            "311...672...34...391.....591......828.......................738....................223....803..472..................................714.840.",
            ".......*...........*.....*...........*........631%...703.......*..12....652.................*.$............368.769*148.................*....",
            "....411...........2....837.121........511.745...........*.48.422.@.........@.............311........887......*................457........595",
            )

        val engineSchematic = EngineSchematic.parseInputFileToData(data)
        val asterixPos = engineSchematic.getIndexesOfAsterix()

        val rs: List<List<Int>> =
            asterixPos.map { pos -> engineSchematic.getNearNumbers(pos)}
                .filter { it.size > 1 }

        expect {
            that(asterixPos.size).isEqualTo(10)
            that(rs.size).isEqualTo(8)
            that(rs).isEqualTo(
                listOf(
                    listOf(672,411),
                    listOf(391,2),
                    listOf(591,837),
                    listOf(828,511),
                    listOf(738,422),
                    listOf(803,311),
                    listOf(769,148),
                    listOf(714,840),
                )
            )
        }
    }

    @Test
    fun `get all the asterix postions`() {
        val engineSchematic = EngineSchematic.parseInputFileToData(day03Lines)
        val rs = engineSchematic.getIndexesOfAsterix()

        expectThat(rs.size).isEqualTo(371)
    }

    @Test
    fun `get numbers near given asterix position`() {
        val engineSchematic = EngineSchematic.parseInputFileToData(day03Lines)
        val position = Pair(1,7)
        val rs = engineSchematic.getNearNumbers(position)

        expectThat(rs).isEqualTo(listOf(672,411))
    }

    @Test
    fun `get number from index`() {
        val lines = listOf("311...672...34...391.....591......828......")
        val engineSchematic = EngineSchematic.parseInputFileToData(lines)

        val rs1 = engineSchematic.getNumberFromIndex(0,6)
        val rs2 = engineSchematic.getNumberFromIndex(0,7)
        val rs3 = engineSchematic.getNumberFromIndex(0,8)

        expectThat(rs1).isEqualTo(672)
        expectThat(rs2).isEqualTo(672)
        expectThat(rs3).isEqualTo(672)
    }

    @Test
    fun `get number from index in beginning`() {
        val lines = listOf("311...672...34...391.....591......828......")
        val engineSchematic = EngineSchematic.parseInputFileToData(lines)

        val rs1 = engineSchematic.getNumberFromIndex(0,0)
        val rs2 = engineSchematic.getNumberFromIndex(0,1)
        val rs3 = engineSchematic.getNumberFromIndex(0,2)

        expectThat(rs1).isEqualTo(311)
        expectThat(rs2).isEqualTo(311)
        expectThat(rs3).isEqualTo(311)
    }

    @Test
    fun `get number from index in end`() {
        val lines = listOf("..6")
        val engineSchematic = EngineSchematic.parseInputFileToData(lines)

        val rs = engineSchematic.getNumberFromIndex(0,2)

        expectThat(rs).isEqualTo(6)
    }

    @Test
    fun `get number -1 from index with non number`() {
        val lines = listOf("...................")
        val engineSchematic = EngineSchematic.parseInputFileToData(lines)

        val rs = engineSchematic.getNumberFromIndex(0,4)

        expectThat(rs).isEqualTo(-1)
    }

}