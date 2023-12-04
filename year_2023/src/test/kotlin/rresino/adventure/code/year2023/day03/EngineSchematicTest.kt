package rresino.adventure.code.year2023.day03

import org.junit.jupiter.api.Test
import rresino.adventure.code.year2023.Day03
import rresino.adventure.code.year2023.Day03.EngineSchematic
import strikt.api.*
import strikt.assertions.*

class EngineSchematicTest {

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

        expectThat(engineSchematic.IsSymbol(-2, 0)).isFalse()
        expectThat(engineSchematic.IsSymbol(-1, 0)).isFalse()
        expectThat(engineSchematic.IsSymbol(0, -2)).isFalse()
        expectThat(engineSchematic.IsSymbol(0, -1)).isFalse()
        expectThat(engineSchematic.IsSymbol(-1, -1)).isFalse()

    }

    @Test
    fun `check if symbol is inside matrix and it's a symbol`() {
        val data = listOf(".*.")
        val engineSchematic = EngineSchematic.parseInputFileToData(data)

        expectThat(engineSchematic.IsSymbol(0, 0)).isFalse()
        expectThat(engineSchematic.IsSymbol(0, 1)).isTrue()
        expectThat(engineSchematic.IsSymbol(0, 2)).isFalse()
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


}