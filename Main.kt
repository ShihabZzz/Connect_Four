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

fun gameBoard(row: Int, col: Int) {
    for (idx in 1..col)
        print(" $idx")
    println()
    for (idxR in 0 until row) {
        for (idxC in 0..col) {
            if (idxC == col)
                println("|")
            else
                print("| ")
        }
    }
    for (idx in 0..col * 2)
        print("=")
    println()
}

fun main() {
    println("Connect Four")
    println("First player's name: ")
    val playerOne = readLine()!!
    println("Second player's name: ")
    val playerTwo = readLine()!!
    message()

    var row = 0
    var col = 0
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
    gameBoard(row, col)

    val listRec = MutableList(col) { 0 }
    for (idx in 0 until col)
        listRec[idx] = row

    val diagram = MutableList(10) {
        MutableList(10) { 0 }
    }

    var disk = ""
    var iTrack = 1
    while (disk != "end") {
        if (iTrack % 2 == 1)
            println("$playerOne's turn:")
        else
            println("$playerTwo's turn:")

        disk = readLine()!!
        if (disk == "end")
            break

        var diskFlag = false
        for (idx in disk.indices) {
            if ((disk[idx] in '0'..'9'))
                continue
            else {
                diskFlag = true
                break
            }
        }

        if (diskFlag) {
            println("Incorrect column number")
            continue
        }
        var diskInt = disk.toInt()
        if (diskInt !in 1..col) {
            println("The column number is out of range (1 - $col)")
            continue
        }
        diskInt--

        if (listRec[diskInt] > 0) {
            if (iTrack % 2 == 1)
                diagram[listRec[diskInt] - 1][diskInt] = 1
            else
                diagram[listRec[diskInt] - 1][diskInt] = 2
            listRec[diskInt]--

            for (idx in 1..col)
                print(" $idx")
            println()
            for (idxR in 0 until row) {
                for (idxC in 0..col) {
                    if (diagram[idxR][idxC] == 1)
                        print("|o")
                    else if (diagram[idxR][idxC] == 2)
                        print("|*")
                    else print("| ")
                }
                println()
            }
            for (idx in 0..col * 2)
                print("=")
            println()
        } else {
            println("Column ${diskInt + 1} is full")
            continue
        }
        iTrack++
    }
    println("Game over!")
}
