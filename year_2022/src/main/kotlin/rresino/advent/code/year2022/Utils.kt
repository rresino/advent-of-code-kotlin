package rresino.advent.code.year2022

import java.io.File

object Utils {

    fun readInput(name: String) =
        File("year_2022/src/main/resources", "$name.txt")
            .readLines()
            .map { it.trim() }
            .filter { it.isNotBlank() }


}