/*
    template: https://github.com/AstronN-git/codeforcesThings/blob/main/gitFiles/kotlinTemplate.kt
    author: _Astron
    for codeforces
    file creation date: ${DATE}-${HOUR}:${MINUTE}:${SECOND}
*/

import java.util.*
import java.io.*
import kotlin.math.*

const val DEBUG = false

@kotlin.ExperimentalStdlibApi
fun main() {
    solve()
    output.flush()
}

@kotlin.ExperimentalStdlibApi
fun solve() {

}

var output : PrintWriter = PrintWriter(BufferedOutputStream(if (DEBUG) FileOutputStream("out.log") else System.out))

fun _println(a : Any) = output.println(a)
fun _println() = output.println()
fun _print(a : Any) = output.print(a)
fun _iprintln(a : Any) {output.println(a); output.flush()}

fun _nextint() : Int = _nextstring().toInt()
fun _nextlong() : Long = _nextstring().toLong()
fun _nextline() : String = br.readLine() ?: ""
fun _nextstring() : String { while (st.hasMoreElements().not()) st = StringTokenizer(br.readLine() ?: return "", " "); return st.nextToken() }

fun IntArray.read() : IntArray {for (i in this.indices) this[i] = _nextint(); return this}
fun LongArray.read() : LongArray {for (i in this.indices) this[i] = _nextlong(); return this}

fun Array<IntArray>.read() : Array<IntArray> {for (i in this.indices) {for (j in this[i].indices) this[i][j] = _nextint()}; return this}

fun _nextints() : ArrayList<Int> = ArrayList(_nextline().split(" ").map {it -> it.toInt()})
fun _nextlongs() : ArrayList<Long> = ArrayList(_nextline().split(" ").map {it -> it.toLong()})
fun _nextstrings() : ArrayList<String> = ArrayList(_nextline().split(" "))

val br = BufferedReader(InputStreamReader(if (DEBUG) FileInputStream("in.log") else System.`in`))
var st = StringTokenizer("")
