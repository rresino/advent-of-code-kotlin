
fun main() {

    //val rawInput = readInput("Day08.demo")
    val rawInput = readInput("day08")
    val input = rawInput.filter { it.isNotBlank() }.map { it.trim() }.map { it.split("|")[1] }

    fun getSimpleNumber(word: String): Int {
        return when(word.length) {
            2 -> 1
            4 -> 4
            3 -> 7
            7 -> 8
            else -> -1
        }
    }

    fun getNumbersByLine(line: String): Map<Int, Int> {
        val values = mutableMapOf<Int,Int>()
        val words = line.split(' ').filter { it!="|" }

        val rs =


        words.forEach {
            val value = getSimpleNumber(it)
            if (value != -1) {
                values += value to values.getOrDefault(value, 0) + 1
            }
        }
        return values
    }

    val total = input.map {
            val rs = getNumbersByLine(it)
            val total = rs.values.sum()
            println("$rs ==> $total")
            total
        }.sum()

    println("Total: $total")
}