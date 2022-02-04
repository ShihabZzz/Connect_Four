//package connectfour /* hyperskill pre req. */

const val defRow = 6
const val defCol = 7

fun message() {
    println(
        "Set the board dimensions (Rows x Columns)\n" +
                "Press Enter for default (6 x 7)"
    )
}

fun main() {
    println("Connect Four")
    println("First player's name: ")
    val playerOne = readLine()!!
    println("Second player's name: ")
    val playerTwo = readLine()!!
    message()

    var row: Int; var col: Int; var flag = false
    var initData = readLine()!!.trim().lowercase()
    var dataSize = initData.length
    var workData = ""

    if (dataSize == 0) {
        println(
            "$playerOne VS $playerTwo\n" +
                    "$defRow X $defCol board"
        )
    } else {
        val regex = Regex(".?.x..?")
        while (!flag) {
            var countAlpha = 0
            for (idx in initData.indices) {
                if ((initData[idx] in '0'..'9') || (initData[idx] in 'a'..'z'))
                    workData += initData[idx]
                if (initData[idx] in 'a'..'z')
                    countAlpha++
            }
            dataSize = workData.length
            if (dataSize == 0) {
                println(
                    "$playerOne VS $playerTwo\n" +
                            "$defRow X $defCol board"
                )
                flag = true
            } else if (workData.matches(regex) && countAlpha <= 1) {
                row = workData[0].digitToInt()
                col = workData[dataSize - 1].digitToInt()

                if (row !in 5..9) {
                    println("Board rows should be from 5 to 9")
                    message()
                    initData = readLine()!!.trim().lowercase()
                    workData = ""
                } else if (col !in 5..9) {
                    println("Board columns should be from 5 to 9")
                    message()
                    initData = readLine()!!.trim().lowercase()
                    workData = ""
                } else {
                    println("$playerOne VS $playerTwo")
                    println("$row X $col board")
                    flag = true
                }
            } else {
                println("Invalid input")
                message()
                initData = readLine()!!.trim().lowercase()
                workData = ""
            }
        }
    }
}
