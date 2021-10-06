/*
    template: https://github.com/AstronN-git/codeforcesThings/blob/main/gitFiles/kotlinTemplate.kt
    author: _Astron
    for codeforces
    file creation date: ${DATE}-${HOUR}:${MINUTE}:${SECOND}
*/

import java.util.*
import java.io.*
import kotlin.math.*

fun main() {
    solve()
    output.flush()
}

fun solve() {

}

val output = PrintWriter(BufferedOutputStream(System.out))
val input = Input()

fun _println(a : Any) = output.println(a)
fun _println() = output.println()
fun _print(a : Any) = output.print(a)
fun _iprintln(a : Any) {output.println(a); output.flush()}

fun _nextint() : Int = input.nextInt()
fun _nextlong() : Long = input.nextLong()
fun _nextline() : String = input.nextLine()
fun _nextstring() : String = input.next()

fun _nextints() : ArrayList<Int> = ArrayList(_nextline().split(" ").map {it -> it.toInt()})
fun _nextlongs() : ArrayList<Long> = ArrayList(_nextline().split(" ").map {it -> it.toLong()})
fun _nextstrings() : ArrayList<String> = ArrayList(_nextline().split(" "))

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
