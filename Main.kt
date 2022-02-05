//package connectfour /* hyperskill pre req. */

const val defRow = 6
const val defCol = 7
var initData = ""
var workData = ""

fun message() {
    println(
        "Set the board dimensions (Rows x Columns)\n" +
                "Press Enter for default (6 x 7)"
    )
}

fun userInput() {
    initData = readLine()!!.trim().lowercase()
    workData = ""
}

fun main() {
    println("Connect Four")
    println("First player's name: ")
    val playerOne = readLine()!!
    println("Second player's name: ")
    val playerTwo = readLine()!!
    message()

    var row: Int
    var col: Int
    var flag = false
    initData = readLine()!!.trim().lowercase()
    var dataSize = initData.length
    workData = ""

    if (dataSize == 0) {
        row = defRow; col = defCol
        println(
            "$playerOne VS $playerTwo\n" +
                    "$row X $col board"
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
                var tempRow = ""
                var tempIdx = 0
                for (idx in workData.indices) {
                    if (workData[idx] in 'a'..'z') {
                        tempIdx = idx + 1
                        break
                    } else tempRow += workData[idx]
                }

                var tempCol = ""
                for (idx in tempIdx..workData.lastIndex) {
                    if (workData[idx] in 'a'..'z')
                        continue
                    else tempCol += workData[idx]
                }

                row = tempRow.toInt()
                col = tempCol.toInt()

                if (row !in 5..9) {
                    println("Board rows should be from 5 to 9")
                    message()
                    userInput()
                } else if (col !in 5..9) {
                    println("Board columns should be from 5 to 9")
                    message()
                    userInput()
                } else {
                    println("$playerOne VS $playerTwo")
                    println("$row X $col board")
                    flag = true
                }
            } else {
                println("Invalid input")
                message()
                userInput()
            }
        }
    }
}
