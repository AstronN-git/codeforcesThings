import java.util.*
import java.io.*
import kotlin.collections.*
import kotlin.math.*

const val FILE_IN_OUT = false
const val FILE_IN = "input.txt"
const val FILE_OUT = "output.txt"

const val TESTS = true

@kotlin.ExperimentalStdlibApi
fun main() {
    if (TESTS) {
        presolve()
        var t = _nextint()
        while (t-- > 0) solve()
    } else
        solve()
    output.flush()
}

@kotlin.ExperimentalStdlibApi
fun presolve() {

}

@kotlin.ExperimentalStdlibApi
fun solve() {
    
}

var output : PrintWriter = PrintWriter(BufferedOutputStream(if (FILE_IN_OUT) FileOutputStream(FILE_OUT) else System.out))

fun _println(a : Any) = output.println(a)
fun _println() = output.println()
fun _print(a : Any) = output.print(a)
fun _iprintln(a : Any) {output.println(a); output.flush()}

fun _nextint() : Int = _nextstring().toInt()
fun _nextlong() : Long = _nextstring().toLong()
fun _nextline() : String = br.readLine() ?: ""
fun _nextstring() : String { while (st.hasMoreElements().not()) st = StringTokenizer(br.readLine() ?: return "", " "); return st.nextToken() }
fun IntArray.read() : IntArray {for (i in indices) this[i] = _nextint(); return this}
fun LongArray.read() : LongArray {for (i in indices) this[i] = _nextlong(); return this}
fun Array<String>.read() : Array<String> {for (i in indices) this[i] = _nextstring(); return this}

fun Array<IntArray>.read() : Array<IntArray> {for (i in indices) for (j in this[i].indices) this[i][j] = _nextint(); return this}

fun IntArray.arrToArr() : Array<Int> {val res = Array<Int>(size) {0}; for (i in indices) res[i] = this[i]; return res}

fun _nextints() : ArrayList<Int> = ArrayList(_nextline().split(" ").map {it.toInt()})
fun _nextlongs() : ArrayList<Long> = ArrayList(_nextline().split(" ").map {it.toLong()})
fun _nextstrings() : ArrayList<String> = ArrayList(_nextline().split(" "))

val br = BufferedReader(InputStreamReader(if (FILE_IN_OUT) FileInputStream(FILE_IN) else System.`in`))
var st = StringTokenizer("")
