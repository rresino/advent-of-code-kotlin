fun main() {

    val rawInput = readInput("Day04").filter { it.isNotBlank() }

    data class Row(val data: List<Int>) {

        var notSelected = data.toSet()

        fun select(item: Int) {
            notSelected -= item
        }

        fun isWinner(): Boolean = notSelected.isEmpty()

        fun getFinalScore(): Int = notSelected.fold(0) {acc, i -> acc + i }

        override fun toString(): String {
            return data.map { if (it<10) " $it" else "$it" }.joinToString(" ")
        }

    }

    fun getRowFromColumns(index: Int, row1: Row, row2: Row, row3: Row, row4: Row, row5: Row) =
        Row(listOf( row1.data[index], row2.data[index], row3.data[index], row4.data[index], row5.data[index]))

    data class Board(var rows: List<Row> = listOf(), var columns: List<Row> = listOf()) {

        fun addRows(row1: Row, row2: Row, row3: Row, row4: Row, row5: Row) {

            rows = listOf(row1, row2, row3, row4, row5)
            columns = listOf(
                getRowFromColumns(0, row1, row2, row3, row4, row5),
                getRowFromColumns(1, row1, row2, row3, row4, row5),
                getRowFromColumns(2, row1, row2, row3, row4, row5),
                getRowFromColumns(3, row1, row2, row3, row4, row5),
                getRowFromColumns(4, row1, row2, row3, row4, row5)
            )
        }

        fun select(item: Int) {
            rows.forEach { it.select(item) }
            columns.forEach { it.select(item) }
        }

        fun isWinner(): Boolean =
            rows.fold(false){ acc, x -> acc || x.isWinner() } ||
            columns.fold(false){ acc, x -> acc || x.isWinner() }

        fun getFinalScore(): Int = rows.fold(0) {acc, i -> acc + i.getFinalScore() }

        override fun toString(): String {
            return rows.map { it.toString() }.joinToString("\n")
        }


    }

    val numbers = rawInput[0].split(",").map { it.toInt() }

    var boards = mutableListOf<Board>()
    for (i in 1 until rawInput.size step 5) {
        val row1 = Row(rawInput[i].split(" ").filter { it.isNotBlank() }.map { it.toInt() })
        val row2 = Row(rawInput[i+1].split(" ").filter { it.isNotBlank() }.map { it.toInt() })
        val row3 = Row(rawInput[i+2].split(" ").filter { it.isNotBlank() }.map { it.toInt() })
        val row4 = Row(rawInput[i+3].split(" ").filter { it.isNotBlank() }.map { it.toInt() })
        val row5 = Row(rawInput[i+4].split(" ").filter { it.isNotBlank() }.map { it.toInt() })

        val b = Board()
        b.addRows(row1, row2, row3, row4, row5)
        boards.add(b)
    }

    for (i in 0 ..4) {
        boards.forEach { it.select(numbers[i]) }
    }
    
    var index = 5
    var winners: List<Board>
    var nonWinners: List<Board> = boards
    do {
        nonWinners.forEach { it.select(numbers[index]) }
        val rs = nonWinners.groupBy { it.isWinner() }
        winners = rs.getOrDefault(true, listOf())
        nonWinners = rs.getOrDefault(false, listOf())
        index ++
    } while (!nonWinners.isEmpty())

    val lastNumber = numbers[index-1]

    println("Num winners: ${winners.size}")
    println("Last Number: $lastNumber")

    winners.forEach {
        println("Winner: ")
        println("$it")
        println("Total score: ${it.getFinalScore()} X $lastNumber = ${it.getFinalScore() * lastNumber}") }
        println()
    }
