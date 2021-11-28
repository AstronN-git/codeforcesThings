import java.util.*
import java.io.*
import kotlin.collections.*
import kotlin.math.*

const val FILE_IO = false
const val FILE_IN = "input.txt"
const val FILE_OUT = "output.txt"

const val TESTS = false

@kotlin.ExperimentalStdlibApi
fun main() {
    if (TESTS) {
        presolve()
        repeat(ni(), ::solve)
    } else
        solve()
    output.flush()
}

@kotlin.ExperimentalStdlibApi
fun presolve() {

}

@kotlin.ExperimentalStdlibApi
fun solve(testIdx : Int = 0) {

}

fun pln(a : Any = "") = output.println(a)
fun pr(a : Any) = output.print(a)
fun ipln(a : Any) {output.println(a); output.flush()}

fun ni() = nstr().toInt()
fun nl() = nstr().toLong()
fun nln() = br.readLine() ?: ""
fun nstr() : String {while (!st.hasMoreElements()) st = StringTokenizer(br.readLine() ?: return "", " "); return st.nextToken()}

fun IntArray.read() : IntArray {for (i in indices) this[i] = ni(); return this}
fun LongArray.read() : LongArray {for (i in indices) this[i] = nl(); return this}
fun Array<String>.read() : Array<String> {for (i in indices) this[i] = nstr(); return this}

fun nis(del: String = " ") = ArrayList(nln().split(del).map {it.toInt()})
fun nls(del: String = " ") = ArrayList(nln().split(del).map {it.toLong()})
fun nstrs(del: String = " ") = ArrayList(nln().split(del))

var output = PrintWriter(BufferedOutputStream(if (FILE_IO) FileOutputStream(FILE_OUT) else System.out))
val br = BufferedReader(InputStreamReader(if (FILE_IO) FileInputStream(FILE_IN) else System.`in`))
var st = StringTokenizer("")
