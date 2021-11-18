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
fun nstr() : String { while (st.hasMoreElements().not()) st = StringTokenizer(br.readLine() ?: return "", " "); return st.nextToken() }

fun IntArray.read() : IntArray {for (i in indices) this[i] = ni(); return this}
fun LongArray.read() : LongArray {for (i in indices) this[i] = nl(); return this}
fun Array<String>.read() : Array<String> {for (i in indices) this[i] = nstr(); return this}

fun nis(delimiter: String = " ") = ArrayList(nln().split(delimiter).map {it.toInt()})
fun nls(delimiter: String = " ") = ArrayList(nln().split(delimiter).map {it.toLong()})
fun nstrs(delimiter: String = " ") = ArrayList(nln().split(delimiter))

var output = PrintWriter(BufferedOutputStream(if (FILE_IN_OUT) FileOutputStream(FILE_OUT) else System.out))
val br = BufferedReader(InputStreamReader(if (FILE_IN_OUT) FileInputStream(FILE_IN) else System.`in`))
var st = StringTokenizer("")
