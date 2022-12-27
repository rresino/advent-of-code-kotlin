package rresino.advent.code.year2022

import java.io.File
import kotlin.math.absoluteValue

object Utils {

    fun readInput(name: String, cleanUp: Boolean = true): List<String> =
        File("year_2022/src/main/resources", "$name.txt")
            .readLines()
            .map { it.trim() }
            .filter { !cleanUp || it.isNotBlank() }

    fun readRawInput(name: String): List<String> =
        File("year_2022/src/main/resources", "$name.txt")
            .readLines()

    /**
     * Euclid's algorithm for finding the greatest common divisor of a and b.
     * Take code and idea from https://github.com/Zordid/adventofcode-kotlin-2022/blob/main/src/main/kotlin/utils/Math.kt
     * Thanks!!
     */
    fun gcd(a: Long, b: Long): Long = if (b == 0L) a.absoluteValue else gcd(b, a % b)
    fun gcd(f: Long, vararg n: Long): Long = n.fold(f, ::gcd)
    fun Iterable<Long>.gcd(): Long = reduce(::gcd)

    /**
     * Find the least common multiple of a and b using the gcd of a and b.
     */
    fun lcm(a: Long, b: Long) = (a safeTimes b) / gcd(a, b)
    fun lcm(f: Long, vararg n: Long): Long = n.fold(f, ::lcm)
    fun Iterable<Long>.lcm(): Long = reduce(::lcm)

    infix fun Long.safeTimes(other: Long) = (this * other).also {
        check(other == 0L || it / other == this) { "Long Overflow at $this * $other" }
    }
}