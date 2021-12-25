fun main() {

    val rawInput = readInput("Day03")
    val input = rawInput.filter { it.isNotBlank() }.map { it.trim() }

    fun sumValues(index: Int, acc: Int, str: String): Int {
        if (str[index] == '1') {
            return acc + 1
        }
        return acc
    }

    val oxigenGeneratorFilter: (Int, List<String>) -> Char = { acc: Int, data: List<String> ->
        if (acc * 2 >= data.size) '1' else '0'
    }

    val co2GeneratorFilter: (Int, List<String>) -> Char = { acc: Int, data: List<String> ->
        if (acc * 2 >= data.size) '0' else '1'
    }

    fun getRating(index: Int, data: List<String>, filterFunction: (Int, List<String>) -> Char): String {

        if (data.size == 1) {
            return data[0]
        }

        val mostCommonValue = data.fold(0) { acc, x -> sumValues(index, acc, x) }
        val bitCriteria = filterFunction(mostCommonValue, data)

        val filtered = data.filter { x -> x[index] == bitCriteria }

        return getRating(index + 1, filtered, filterFunction)
    }

    val ogr = getRating(0, input, oxigenGeneratorFilter)
    val decimalOgr = binaryStrToDecimal(ogr)
    val co2gr = getRating(0, input, co2GeneratorFilter)
    val decimalCo2 = binaryStrToDecimal(co2gr)

    println("Oxygen generator rating $ogr => $decimalOgr")
    println("CO2 generator rating $co2gr => $decimalCo2")
    println(decimalOgr * decimalCo2)

}
