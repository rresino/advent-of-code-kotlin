fun main() {

    val rawInput = readInput("Day03")
    val input = rawInput.filter { it.isNotBlank() }.map { it.trim() }
    val size = input.size

    fun sumArray(data: IntArray, bytes: String): IntArray {

        for (i in bytes.indices) {
            data[i] = data[i] + bytes[i].toString().toInt()
        }

        return data
    }

    fun intArrayToString(data: IntArray): String = data.fold(""){acc, x -> acc + x.toString()}

    val result = input.fold(IntArray(input[0].length) { 0 }) { acc, next -> sumArray(acc, next) }

    for (i in result.indices) {
        if (result[i] > size / 2) {
            result[i] = 1
        } else {
            result[i] = 0
        }
    }



    val gamma = intArrayToString(result)
    val epsilon = gamma.asIterable().joinToString("") { if (it == '0') "1" else "0" }

    val rs = gamma.toInt(2) * epsilon.toInt(2)

    println("Gamma rate $gamma")
    println("Epsilon rate $epsilon")
    println(rs)

}
