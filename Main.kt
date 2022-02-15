//package connectfour /* hyperskill pre req. */

const val defRow = 6
const val defCol = 7
var initData = ""
var workData = ""
var totalGame = ""

var diagram = MutableList(10) {
    MutableList(10) { 0 }
}

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

fun gameNumber() {
    var totalGameFlag = true
    while (totalGameFlag) {
        println(
            "Do you want to play single or multiple games?\n" +
                    "For a single game, input 1 or press Enter\n" +
                    "Input a number of games:"
        )
        totalGame = readLine()!!.trim().lowercase()
        if (totalGame.isEmpty()) {
            totalGameFlag = false
            totalGame = "1"
        } else {
            for (idx in totalGame.indices) {
                if (totalGame[idx] in '1'..'9') {
                    totalGameFlag = false
                } else {
                    totalGameFlag = true
                    println("Invalid input")
                    break
                }
            }
        }
    }
}

fun gameBoard(row: Int, col: Int) {
    for (idx in 1..col)
        print(" $idx")
    println()
    for (idxR in 0 until row) {
        for (idxC in 0..col) {
            when {
                diagram[idxR][idxC] == 1 -> print("|o")
                diagram[idxR][idxC] == 2 -> print("|*")
                idxC == col -> print("|")
                else -> print("| ")
            }
        }
        println()
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
    var totalG = 0
    var playerOneScore = 0
    var playerTwoScore = 0

    if (dataSize == 0) {
        row = defRow; col = defCol
        gameNumber()
        println(
            "$playerOne VS $playerTwo\n" +
                    "$row X $col board"
        )
        totalG = totalGame.toInt()
        if (totalG > 1)
            println("Total $totalG games")
        else println("Single game")
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
                gameNumber()
                println(
                    "$playerOne VS $playerTwo\n" +
                            "$defRow X $defCol board"
                )
                totalG = totalGame.toInt()
                if (totalG > 1)
                    println("Total $totalG games")
                else println("Single game")
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
                    gameNumber()
                    println("$playerOne VS $playerTwo")
                    println("$row X $col board")
                    totalG = totalGame.toInt()
                    if (totalG > 1)
                        println("Total $totalG games")
                    else println("Single game")
                    flag = true
                }
            } else {
                println("Invalid input")
                message()
                userInput()
            }
        }
    }

    val listRec = MutableList(col) { 0 }
    var disk = ""
    var playerSeq: Int
    var currGame = 1

    loopTwo@ while (currGame <= totalG) {
        if (totalG > 1) {
            println("Game #$currGame")
        }
        diagram = MutableList(10) {
            MutableList(10) { 0 }
        }
        for (idx in 0 until col)
            listRec[idx] = row

        gameBoard(row, col)

        playerSeq = if (currGame % 2 == 1)
            1
        else
            2

        loop@ while (disk != "end") {
            if (playerSeq % 2 == 1)
                println("$playerOne's turn:")
            else
                println("$playerTwo's turn:")

            disk = readLine()!!
            if (disk == "end")
                break@loopTwo

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
                when {
                    playerSeq % 2 == 1 -> diagram[listRec[diskInt] - 1][diskInt] = 1
                    else -> diagram[listRec[diskInt] - 1][diskInt] = 2
                }
                listRec[diskInt]--

                gameBoard(row, col)

                // Horizontal-Check
                for (i in 0 until row) {
                    for (j in 0 until col) {
                        if (playerSeq % 2 == 1) {
                            if (diagram[i][j] == 1 && (j + 3) <= (col - 1)) {
                                if (diagram[i][j + 1] == 1 && diagram[i][j + 2] == 1 && diagram[i][j + 3] == 1) {
                                    println("Player $playerOne won")
                                    playerOneScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        } else {
                            if (diagram[i][j] == 2 && (j + 3) <= (col - 1)) {
                                if (diagram[i][j + 1] == 2 && diagram[i][j + 2] == 2 && diagram[i][j + 3] == 2) {
                                    println("Player $playerTwo won")
                                    playerTwoScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        }
                    }
                }

                // Vertical-Check
                for (i in 0 until col) {
                    for (j in 0 until row) {
                        if (playerSeq % 2 == 1) {
                            if (diagram[j][i] == 1 && (j + 3) <= (row - 1)) {
                                if (diagram[j + 1][i] == 1 && diagram[j + 2][i] == 1 && diagram[j + 3][i] == 1) {
                                    println("Player $playerOne won")
                                    playerOneScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        } else {
                            if (diagram[j][i] == 2 && (j + 3) <= (row - 1)) {
                                if (diagram[j + 1][i] == 2 && diagram[j + 2][i] == 2 && diagram[j + 3][i] == 2) {
                                    println("Player $playerTwo won")
                                    playerTwoScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        }
                    }
                }

                // Diagonal_Increasing-Check
                for (i in 0 until row) {
                    for (j in 0 until col) {
                        if (playerSeq % 2 == 1) {
                            if (diagram[i][j] == 1 && (i + 3) <= (row - 1) && (j + 3) <= (col - 1)) {
                                if (diagram[i + 1][j + 1] == 1 && diagram[i + 2][j + 2] == 1 && diagram[i + 3][j + 3] == 1) {
                                    println("Player $playerOne won")
                                    playerOneScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        } else {
                            if (diagram[i][j] == 2 && (i + 3) <= (row - 1) && (j + 3) <= (col - 1)) {
                                if (diagram[i + 1][j + 1] == 2 && diagram[i + 2][j + 2] == 2 && diagram[i + 3][j + 3] == 2) {
                                    println("Player $playerTwo won")
                                    playerTwoScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        }
                    }
                }

                // Diagonal_Decreasing-Check
                for (i in 0 until row) {
                    for (j in 0 until col) {
                        if (playerSeq % 2 == 1) {
                            if (diagram[i][j] == 1 && (i + 3) <= (row - 1) && (j - 3) >= 0) {
                                if (diagram[i + 1][j - 1] == 1 && diagram[i + 2][j - 2] == 1 && diagram[i + 3][j - 3] == 1) {
                                    println("Player $playerOne won")
                                    playerOneScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        } else {
                            if (diagram[i][j] == 2 && (i + 3) <= (row - 1) && (j - 3) >= 0) {
                                if (diagram[i + 1][j - 1] == 2 && diagram[i + 2][j - 2] == 2 && diagram[i + 3][j - 3] == 2) {
                                    println("Player $playerTwo won")
                                    playerTwoScore += 2
                                    currGame++
                                    break@loop
                                }
                            } else continue
                        }
                    }
                }

                // Board_Free-Check
                var boardFree = false
                for (i in 0 until row) {
                    for (j in 0 until col) {
                        if (diagram[i][j] == 0) {
                            boardFree = true
                            break
                        }
                    }
                }
                if (!boardFree) {
                    println("It is a draw")
                    playerOneScore++
                    playerTwoScore++
                    currGame++
                    break@loop
                }

            } else {
                println("Column ${diskInt + 1} is full")
                continue
            }
            playerSeq++
        }
        if (totalG > 1) {
            println("Score")
            println("$playerOne: $playerOneScore $playerTwo: $playerTwoScore")
        }
    }
    println("Game over!")
}
