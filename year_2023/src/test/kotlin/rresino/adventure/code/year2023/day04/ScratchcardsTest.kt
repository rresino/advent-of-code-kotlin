package rresino.adventure.code.year2023.day04

import org.junit.jupiter.api.Test
import rresino.adventure.code.year2023.Day04
import strikt.api.*
import strikt.assertions.*

class ScratchcardsTest {

    @Test
    fun `parse a line str to numbers`() {
        val row = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val rs = Day04.Scratchcards.parseLineToInt(row)

        expectThat(rs).isEqualTo(Pair(
            listOf(41, 48, 83, 86, 17),
            listOf(83, 86, 6, 31, 17, 9, 48, 53)
            ))
    }

    @Test
    fun `create a scratchcards from row`() {
        val row = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val rs = Day04.Scratchcards.create(row)

        expectThat(rs).isEqualTo(Day04.Scratchcards(listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53)))
    }

    @Test
    fun `get winning numbers of the cards`(){
        val card = Day04.Scratchcards(
            listOf(41, 48, 83, 86, 17),
            listOf(83, 86, 6, 31, 17, 9, 48, 53))
        val rs = card.getCardWinningNumbers()

        expectThat(rs).isEqualTo(listOf(48, 83, 86, 17))
    }

    @Test
    fun `get index of winning numbers`() {
        val card = Day04.Scratchcards(
            listOf(41, 48, 83, 86, 17),
            listOf(83, 86, 6, 31, 17, 9, 48, 53))
        val rs = card.getCardWinningIndex()

        expectThat(rs).isEqualTo(listOf(1, 2, 3, 4))
    }

    @Test
    fun `get score 8 from a card with 4 winning numbers`() {
        val card = Day04.Scratchcards(
            listOf(41, 48, 83, 86, 17),
            listOf(83, 86, 6, 31, 17, 9, 48, 53))
        val rs = card.getScore()

        expectThat(rs).isEqualTo(8)
    }

    @Test
    fun `get score 2 from a card with 2 winning numbers`() {
        val card = Day04.Scratchcards(
            listOf(1, 21, 53, 59, 44),
            listOf(69, 82, 63, 72, 16, 21, 14, 1))
        val rs = card.getScore()

        expectThat(rs).isEqualTo(2)
    }

    @Test
    fun `get score 1 from a card with 1 winning numbers`() {
        val card = Day04.Scratchcards(
            listOf(41, 92, 73, 84, 69),
            listOf(59, 84, 76, 51, 58, 5, 54, 83))
        val rs = card.getScore()

        expectThat(rs).isEqualTo(1)
    }

    @Test
    fun `get score of 0 from a card with 0 winning numbers`() {
        val card = Day04.Scratchcards(
            listOf(87, 83, 26, 28, 32),
            listOf(88, 30, 70, 12, 93, 22, 82, 36))
        val rs = card.getScore()

        expectThat(rs).isEqualTo(0)
    }

}