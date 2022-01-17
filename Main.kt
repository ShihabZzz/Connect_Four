package connectfour

fun main() {
    println("Connect Four")
    println("First player's name: ")
    val p1 = readLine()!!
    println("Second player's name: ")
    val p2 = readLine()!!
    println(
        "Set the board dimensions (Rows x Columns)\n" +
                "Press Enter for default (6 x 7)"
    )
    val cro = 6
    val cco = 7
    var ro = 6
    var co = 7
    var flag = false
    var ask = readLine()!!.trimIndent()
    var si = ask.length
    var askP = ""

    if (si == 0) {
        println(
            "$p1 VS $p2\n" +
                    "$cro X $cco board"
        )
    } else {
        val regex1 = Regex(".?.x..?")
        val regex2 = Regex(".?.X..?")
        while (!flag) {
            //var cntf = 0
            for (idx in ask.indices) {
                /*if (ask[idx] == ' ')
                    continue
                else {
                    if ((ask[idx] in '0'..'9') || (ask[idx] in 'a'..'z') || (ask[idx] in 'A'..'Z'))
                        askP += ask[idx]
                    else
                        cntf++
                }*/
                if ((ask[idx] in '0'..'9') || (ask[idx] in 'a'..'z') || (ask[idx] in 'A'..'Z'))
                    askP += ask[idx]
            }
            var cnt = 0
            for (idx in askP.indices) {
                if (askP[idx] in '0'..'9')
                    continue
                else
                    cnt++
            }
            //println(askP)
            si = askP.length
            if (si == 0) {
                println(
                    "$p1 VS $p2\n" +
                            "$cro X $cco board"
                )
                flag = true
            } else if ((askP.matches(regex1) || askP.matches(regex2)) && cnt <= 1) {
                ro = askP[0].digitToInt()
                co = askP[si - 1].digitToInt()

                if (ro < 5 || ro > 9) {
                    println(
                        "Board rows should be from 5 to 9\n" +
                                "Set the board dimensions (Rows x Columns)\n" +
                                "Press Enter for default (6 x 7)"
                    )
                    ask = readLine()!!.trimIndent()
                    askP = ""
                } else if (co < 5 || co > 9) {
                    println(
                        "Board columns should be from 5 to 9\n" +
                                "Set the board dimensions (Rows x Columns)\n" +
                                "Press Enter for default (6 x 7)"
                    )
                    ask = readLine()!!.trimIndent()
                    askP = ""
                } else {
                    println("$p1 VS $p2")
                    println("$ro X $co board")
                    flag = true
                }
            } else {
                println(
                    "Invalid input\n" +
                            "Set the board dimensions (Rows x Columns)\n" +
                            "Press Enter for default (6 x 7)"
                )
                ask = readLine()!!.trimIndent()
                askP = ""
            }
        }
    }
}
