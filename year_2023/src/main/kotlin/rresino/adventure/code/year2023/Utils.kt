package rresino.adventure.code.year2023

import java.io.File

object Utils {

    fun readCleanInput(name: String): List<String> =
        readRawInput(name)
            .map { it.trim() }
            .filter { it.isNotBlank() }

    fun readRawInput(name: String): List<String> =
        File("year_2023/src/main/resources", "$name.txt")
            .readLines()

}