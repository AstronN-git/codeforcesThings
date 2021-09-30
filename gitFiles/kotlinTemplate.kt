/*
    template: https://github.com/AstronN-git/codeforcesThings/blob/main/gitFiles/kotlinTemplate.kt
    author: _Astron
    for codeforces
    file creation date: ${DATE}-${HOUR}:${MINUTE}:${SECOND}
*/

import java.util.*
import java.io.*

fun main() {
    solve()
    output.flush()
}

val output = PrintWriter(BufferedOutputStream(System.out))
val input = Input()

fun solve() {
    
}

fun _println(a : Any) = output.println(a)
fun _iprintln(a : Any) {output.println(a); output.flush()}

fun _readint() : Int = input.nextInt()
fun _readlong() : Long = input.nextLong()
fun _nextline() : String = input.nextLine()
fun _nextstring() : String = input.next()

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
