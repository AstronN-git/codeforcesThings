/*
    template: https://github.com/AstronN-git/codeforcesThings/blob/main/gitFiles/kotlinTemplate.kt
    author: _Astron
    for codeforces
    file creation date: ${DATE}-${HOUR}:${MINUTE}:${SECOND}
*/

import java.util.*
import java.io.*

fun main() {
    val input = Input()
    solve(input)
    output.flush()
}

val output = PrintWriter(BufferedOutputStream(System.out))

fun solve(input : Input) {
    
}

class Input {
    private val br = BufferedReader(InputStreamReader(System.`in`))
    private var st : StringTokenizer = StringTokenizer("")

    fun next() : String {
        while (st.hasMoreElements().not()) st = StringTokenizer(br.readLine() ?: return "", " ")
        return st.nextToken()
    }

    fun nextInt() : Int = next().toInt()
    fun nextLong() : Long = next().toLong()
    fun nextLine() : String = br.readLine() ?: ""
}
