import java.util.*
import java.io.*
import kotlin.collections.*
import kotlin.math.*
import java.util.ArrayDeque

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
fun presolve () {

}

@kotlin.ExperimentalStdlibApi
fun solve(testIdx : Int = 0) {
    class MinMaxStack {
        val st = ArrayDeque<Long>()
        val st_min = ArrayDeque<Long>()
        val st_max = ArrayDeque<Long>()

        fun push (value : Long) {
            st_min.push(if (st.isEmpty()) value else min (value, st_min.peek()))
            st_max.push(if (st.isEmpty()) value else max (value, st_max.peek()))
            st.push(value)
        }

        val min get () = st_min.peek()
        val max get () = st_max.peek()

        fun pop () : Long {
            st_min.pop()
            st_max.pop()
            return st.pop()
        }

        fun peek () : Long = st.peek()

        fun isEmpty () : Boolean = st.isEmpty()
    }

    class TwoSrackQueue {
        val s1 = MinMaxStack ()
        val s2 = MinMaxStack ()

        fun push (value : Long) = s2.push(value)

        private fun move () {
            while (!s2.isEmpty()) {
                s1.push(s2.pop())
            }
        }

        val min : Long get () {
            if (!s1.isEmpty() && !s2.isEmpty())
                return min (s1.min, s2.min)
            if (!s1.isEmpty())
                return s1.min
            if (!s2.isEmpty())
                return s2.min

            return -1
        }

        val max : Long get () {
            if (!s1.isEmpty() && !s2.isEmpty())
                return max (s1.max, s2.max)
            if (!s1.isEmpty())
                return s1.max
            if (!s2.isEmpty())
                return s2.max

            return -1
        }

        fun pop () {
            if (s1.isEmpty())
                move ()

            s1.pop()
        }

        val elements : Int get() = s1.st.size + s2.st.size
    }

    val n = ni()
    val k = nl()
    val arr = LongArray(n).read()

    var l = 0
    var res = 0L

    val queue = TwoSrackQueue()

    for (r in 0 until n) {
        queue.push(arr[r])
        while (queue.max - queue.min > k && queue.elements != 0) {
            queue.pop()
            l++
        }

        res += queue.elements
    }

    pln(res)
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
