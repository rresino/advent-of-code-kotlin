fun main() {

    /*
    0:      1:      2:      3:      4:
     aaaa    ....    aaaa    aaaa    ....
    b    c  .    c  .    c  .    c  b    c
    b    c  .    c  .    c  .    c  b    c
     ....    ....    dddd    dddd    dddd
    e    f  .    f  e    .  .    f  .    f
    e    f  .    f  e    .  .    f  .    f
     gggg    ....    gggg    gggg    ....

    5:      6:      7:      8:      9:
     aaaa    aaaa    aaaa    aaaa    aaaa
    b    .  b    .  .    c  b    c  b    c
    b    .  b    .  .    c  b    c  b    c
     dddd    dddd    ....    dddd    dddd
    .    f  e    f  .    f  e    f  .    f
    .    f  e    f  .    f  e    f  .    f
     gggg    gggg    ....    gggg    gggg

     */
    val rawInput = readInput("Day08.demo")
    //val rawInput = readInput("Day08")
    val input = rawInput
        .filter { it.isNotBlank() }
        .map { it.trim() }
        .map { it.split("|")[1].trim() }
        .filter { it.isNotBlank() }

    fun getFourthSet(words: List<String>): Set<Char>? =
        words.firstOrNull { it.length == 4 }?.toCharArray()?.toSet()

    fun getSevenSet(words: List<String>): Set<Char>? =
        words.firstOrNull { it.length == 3 }?.toCharArray()?.toSet()

    fun getOneSet(words: List<String>): Set<Char>? =
        words.firstOrNull { it.length == 2 }?.toCharArray()?.toSet()

    fun getThreeSet(words: List<String>, oneSet: Set<Char>?): Set<Char>? {
        if (oneSet == null) {
            return null
        }
        return words
            .firstOrNull{ it.length == 5 && oneSet.intersect(it.toCharArray().toSet()).size == 2 }
            ?.toCharArray()
            ?.toSet()
    }


    /**
     * 0, 6 or 9 numbers
     */
    fun get6Segments(words: List<String>, word: String): Int {

        val chars = word.toCharArray().toSet()

        val onePattern = getOneSet(words)
        val threePattern = getThreeSet(words, onePattern)
        val fourPattern = getFourthSet(words)
        val sevenPattern = getSevenSet(words)

        if (onePattern != null && onePattern.intersect(chars).size == 1) {
            return 6
        }

        if (threePattern != null && (chars - threePattern).size == 1) {
            return 9
        }

        if (fourPattern != null && (chars - fourPattern).size == 2) {
            return 9
        }

        if (sevenPattern != null) {
            if ((chars - sevenPattern).size == 4) {
                return 6
            }
            if (onePattern != null) {
                if ((chars - onePattern).size == 4) {
                    return 9
                } else {
                    return 0
                }
            }
        }

        return 0
    }

    fun get5Segments(words: List<String>, word: String): Int {
        // 2, 3, 5
        val chars = word.toCharArray().toSet()

        val fourPattern = getFourthSet(words)
        val onePattern = getOneSet(words)
        val sevenPattern = getSevenSet(words)

        if (fourPattern != null && onePattern != null && (fourPattern - onePattern).intersect(chars).size == 2) {
            return 5
        }

        // 2 => 2
        // 3 => 3
        // 5 => 3
        if (fourPattern != null) {
            if (fourPattern.intersect(chars).size == 2) {
                return 2
            }

        }

        if (onePattern != null && onePattern.intersect(chars).size == 2) {
            return 3
        }

//        if (fourPattern != null && (fourPattern - chars).size == 1) {
//            return 3
//        }

        return 2
    }

    fun getNumber(words: List<String>, word: String): Int {
        val segments = word.length
        return when(segments) {
            2 -> 1
            4 -> 4
            3 -> 7
            7 -> 8
            6 -> get6Segments(words, word)
            5 -> get5Segments(words, word)
            else -> -1
        }
    }

    fun getNumbersByLine(line: String): String {
        val words = line.split(' ').filter { it!="|" }

        val ls =  words.map {
            getNumber(words, it).toString()
        }

        return ls.joinToString(separator = "")
    }

    val total = input.map {
        val rs = getNumbersByLine(it)
        println("$rs")
        rs.toInt()
    }.sum()

    println("Total: $total")
}