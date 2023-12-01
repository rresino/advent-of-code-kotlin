package rresino.advent.code.year2022

abstract class BaseDay(val day:String) {

    fun run(name: String, cleanUp: Boolean = true) {
        println("Day $day - Solution $name - Demo")
        doSolution(getDemoFileData(cleanUp))
        println("Day $day - Solution $name")
        doSolution(getFileData(cleanUp))
    }

    protected abstract fun doSolution(data: List<String>): Unit

    private fun getDemoFileData(cleanUp: Boolean = true): List<String> =
        if (cleanUp) {
            Utils.readInput("day$day.demo", cleanUp)
        } else {
            Utils.readRawInput("day$day.demo")
        }

    private fun getFileData(cleanUp: Boolean = true): List<String> =
        if (cleanUp) {
            Utils.readInput("day$day", cleanUp)
        } else {
            Utils.readRawInput("day$day")
        }

}