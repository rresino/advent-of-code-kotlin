package rresino.advent.code.year2022

import java.lang.StringBuilder
import java.util.LinkedList
import java.util.Stack

// Day 13: Distress Signal
// https://adventofcode.com/2022/day/13
class Day13 : BaseDay("13") {

    sealed class Item {
        //data class Root(val items: LinkedList<Item> = LinkedList<Item>())
        data class ItemValue(val item: Int): Item()
        data class ItemGroup(val items: LinkedList<Item> = LinkedList<Item>()): Item()
    }

//    data class Item(val item: Int, val level: Int) {
//        fun add(newItem: Int) = Item(newItem, level)
//    }

    override fun doSolution(data: List<String>) {

        val packetData = data.map { row ->
            val stack = Stack<Item>()
            stack.push(Item.ItemGroup())
            //stack.push(root)
            var accRow = row
            while(accRow.isNotEmpty()) {
                when(accRow[0]) {
                    '[' -> {
                            stack.push(Item.ItemGroup())
                            accRow = accRow.substring(1)
                        }
                    ']' -> {
                            val topItem = stack.pop()
                            (stack.peek() as Item.ItemGroup).items.add(topItem)
                            accRow = accRow.substring(1)
                        }
                    ',' -> accRow = accRow.substring(1)
                    else -> {
                        val accStr = StringBuilder()
                        var i = 0
                        while (accRow[i].isDigit()) {
                            accStr.append(accRow[i++])
                        }
                        (stack.peek() as Item.ItemGroup).items.add(Item.ItemValue(accStr.toString().toInt()))
                        accRow = accRow.substring(accStr.length)
                    }
                }
            }

            val dataItems = stack.pop()
            dataItems
        }
        println(packetData)

//            row.replace("[","")
//                    .replace("]","")
//                    .split(",")
//                .filter { it.isNotBlank() }
//                    .map { it.toInt() }
//            }
//            .chunked(2)
//            .map { Pair(it[0],it[1]) }
//
//        val rs = packetData.mapIndexed { index, pair ->
//                if (isOrdered(pair)) {
//                    index + 1
//                } else {
//                    0
//                }
//            }.filter { it > 0 }

//        println("Result ${rs.sum()}: $rs")
    }

    private fun isOrdered(pair: Pair<List<Int>, List<Int>>): Boolean {
        val (left, right) = pair
        if (left.isEmpty() && right.isEmpty()) { // both empty or left empty
            return false
        } else if (left.size > right.size) { // right will be out of items
            return false
        }
        right.indices.forEach {ix ->
            if (ix >= left.size) { // no more items on right side
                return true
            }
            if (left[ix] > right[ix]) {
                return false
            }
        }
        return true
    }


}

fun main() {

    val s = Day13()
    s.run("Solution 1")


}