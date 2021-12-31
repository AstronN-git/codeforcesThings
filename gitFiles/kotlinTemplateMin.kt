import java.util.*
import java.io.*
import kotlin.collections.*
import kotlin.math.*

const val TESTS = false

@ExperimentalStdlibApi
fun main() {
    repeat(if (TESTS) ni() else 1, ::solve)
    out.flush()
}

@ExperimentalStdlibApi
fun solve(testIdx : Int = 0) {

}

operator fun String.get (range : IntRange) = this.substring(range)

fun pln(a : Any = "") = out.println(a)
fun pr(a : Any) = out.print(a)
fun ipln(a : Any) {pln(a); out.flush()}

fun ni() = nstr().toInt()
fun nl() = nstr().toLong()
fun nln() = br.readLine() ?: ""
fun nstr() : String {while (!st.hasMoreElements()) st = StringTokenizer(br.readLine() ?: return "", " "); return st.nextToken()}

fun nis(del: String = " ") = ArrayList(nln().split(del).map {it.toInt()})
fun nls(del: String = " ") = ArrayList(nln().split(del).map {it.toLong()})
fun nstrs(del: String = " ") = ArrayList(nln().split(del))

var out = PrintWriter(BufferedOutputStream(System.out))
val br = BufferedReader(InputStreamReader(System.`in`))
var st = StringTokenizer("")
