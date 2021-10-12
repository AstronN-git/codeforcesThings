/*
    template: https://github.com/AstronN-git/codeforcesThings/blob/main/gitFiles/kotlinTemplate.kt
    author: _Astron
    for codeforces
    file creation date: ${DATE}-${HOUR}:${MINUTE}:${SECOND}
*/

import java.util.*
import java.io.*
import kotlin.collections.HashMap
import kotlin.math.*

const val DEBUG = false

@kotlin.ExperimentalStdlibApi
fun main() {
    if (DEBUG) output = PrintWriter(BufferedOutputStream(FileOutputStream("out.log")))

    solve()
    output.flush()
}

@kotlin.ExperimentalStdlibApi
fun solve() {
    
}

val input = Input(DEBUG)
var output : PrintWriter = PrintWriter(BufferedOutputStream(System.out))

fun _println(a : Any) = output.println(a)
fun _println() = output.println()
fun _print(a : Any) = output.print(a)
fun _iprintln(a : Any) {output.println(a); output.flush()}

fun _nextint() : Int = input.nextInt()
fun _nextlong() : Long = input.nextLong()
fun _nextline() : String = input.nextLine()
fun _nextstring() : String = input.next()

fun IntArray.read() : IntArray {for (i in this.indices) this[i] = _nextint(); return this}
fun LongArray.read() : LongArray {for (i in this.indices) this[i] = _nextlong(); return this}

fun Array<IntArray>.read() : Array<IntArray> {for (i in this.indices) {for (j in this[i].indices) this[i][j] = _nextint()}; return this}

fun _nextints() : ArrayList<Int> = ArrayList(_nextline().split(" ").map {it -> it.toInt()})
fun _nextlongs() : ArrayList<Long> = ArrayList(_nextline().split(" ").map {it -> it.toLong()})
fun _nextstrings() : ArrayList<String> = ArrayList(_nextline().split(" "))

class Input (DEBUG : Boolean) {
    private val br = if (DEBUG) BufferedReader(InputStreamReader(FileInputStream("in.log"))) else BufferedReader(InputStreamReader(System.`in`))
    private var st : StringTokenizer = StringTokenizer("")

    fun next() : String {
        while (st.hasMoreElements().not()) st = StringTokenizer(br.readLine() ?: return "", " ")
        return st.nextToken()
    }

    fun nextInt() : Int = next().toInt()
    fun nextLong() : Long = next().toLong()
    fun nextLine() : String = br.readLine() ?: ""
}
