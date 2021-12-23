fun main() {

    //val rawInput = readInput("Day01.test")
    val rawInput = readInput("Day01")
    val input = rawInput.filter { it.isNotBlank() }.map { it.trim().toInt() }
    val nonInitValue = -1

    data class DataWindow(val first: Int = nonInitValue, val second: Int = nonInitValue, val third: Int = nonInitValue,
        val acc: Int = 0) {

        fun sum(): Int = first + second + third

        fun isInit(): Boolean = first != nonInitValue && second != nonInitValue && third != nonInitValue

        fun isLarge(next: DataWindow): Boolean {
            return isInit() && next.isInit() && sum() < next.sum()
        }

        fun sliding(nextValue: Int): DataWindow =
            copy(first = second, second = third, third = nextValue)

    }

    fun calculate(acc: DataWindow, next: Int): DataWindow {
        // println("acc=>$acc - ${acc.sum()} - $next")
        val next = acc.sliding(next)
        if (acc.isLarge(next)) {
            return next.copy(acc = next.acc + 1)
        }
        return next
    }

    val initValue = DataWindow(nonInitValue, nonInitValue, nonInitValue, 0)
    val result = input.fold(initValue) { acc, next -> calculate(acc, next) }

    println("How many measurements are larger than the previous measurement?")
    println(result.acc)

}
