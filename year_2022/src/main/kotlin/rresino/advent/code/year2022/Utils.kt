package rresino.advent.code.year2022

import java.io.File

object Utils {

    fun readInput(name: String, cleanUp: Boolean = true): List<String> =
        File("year_2022/src/main/resources", "$name.txt")
            .readLines()
            .map { it.trim() }
            .filter { !cleanUp || it.isNotBlank() }


}