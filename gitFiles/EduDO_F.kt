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
fun presolve () {

}

const val Inf = 1000000007

@kotlin.ExperimentalStdlibApi
fun solve(testIdx : Int = 0) {
    val n = ni()

    open class Query (var type : String)

    class IQuery (var l : Int, var r : Int, var x : Int) : Query("I")
    class QQuery (var x : Int) : Query ("Q")

    val queries = ArrayList<Query>()
    val cords = TreeSet<Int>()
    cords.add(0)

    var str = ""

    var maxCoord = -1

    do {
        str = nstr()
        when (str) {
            "Q" -> queries.add(QQuery(ni()))
            "I" -> {
                val l = ni()
                val r = ni()

                cords.add(l)
                cords.add(r)

                maxCoord = max (maxCoord, r)

                queries.add(IQuery(l, r, ni()))
            }
        }
    } while (str != "E")

    cords.add(maxCoord + 1)

    val CtoU = HashMap<Int, Int>()
    val UtoC = HashMap<Int, Int>()

    for ((idx, element) in cords.withIndex()) {
        CtoU[idx] = element
        UtoC[element] = idx
    }

    for ((idx, element) in queries.withIndex()) {
        if (element is IQuery) {
            queries[idx] = IQuery (
                UtoC[element.l]!!,
                UtoC[element.r]!!,
                element.x
                    )
        }
    }

    var s = 1
    while (s < cords.size) s = s shl 1

    data class Node (var base : Int = 0, var addition : Int = Inf, var lazySet : Boolean = true, var max : Int = 0)

    val t = Array (2 * s) {Node()}

    fun push (v : Int, lx : Int, rx : Int) {
        if (t[v].addition != Inf || t[v].base != 0) {
            val mx = lx + (rx - lx) / 2

            if (t[v].lazySet) {
                t[v*2] = Node (
                    t[v].base,
                    t[v].addition,
                    true,
                    if (t[v].addition < 0) t[v].base else t[v].base + t[v].addition * (CtoU[mx]!! - CtoU[lx]!!)
                        )

                t[v*2+1] = Node (
                    t[v].base + t[v].addition * (CtoU[mx+1]!! - CtoU[lx]!!),
                    t[v].addition,
                    true,
                    if (t[v].addition < 0) t[v].base + t[v].addition * (CtoU[mx+1]!! - CtoU[lx]!!) else t[v].base + t[v].addition * (CtoU[rx]!! - CtoU[lx]!!)
                )
            } else {
                t[v*2] = Node (
                    t[v*2].base + t[v].base,
                    t[v*2].addition,
                    false,
                    t[v*2].max + t[v].base
                )

                t[v*2+1] = Node (
                    t[v*2+1].base + t[v].base,
                    t[v*2+1].addition,
                    false,
                    t[v*2+1].max + t[v].base
                )
            }

            t[v].addition = Inf
            t[v].base = 0
        }
    }

    fun setSeq (v : Int, lx : Int, rx : Int, l : Int, r : Int, base : Int, addition : Int) {
        if (lx > r || rx < l) return
        if (lx >= l && rx <= r) {
            t[v] = Node (
                base, addition,
                true,
                if (addition < 0) base else base + addition * (CtoU[rx]!! - CtoU[lx]!!)
                    )

            return
        }

        push(v, lx, rx)
        val mx = lx + (rx - lx) / 2

        if (l > mx)
            setSeq(v*2+1, mx+1, rx, l, r, base, addition)
        else {
            setSeq(v*2, lx, mx, l, r, base, addition)
            setSeq(v*2+1, mx+1, rx, l, r, base + min (CtoU[mx+1]!! - CtoU[l]!!, CtoU[mx+1]!! - CtoU[lx]!!) * addition, addition)
        }

        t[v].max = max (t[v*2].max, t[v*2+1].max)
    }

    fun addSeq (v : Int, lx : Int, rx : Int, l : Int, r : Int, base : Int) {
        if (lx > r || rx < l) return
        if (lx >= l && rx <= r) {
            t[v] = Node (
                t[v].base + base,
                t[v].addition,
                false,
                t[v].max + base
                    )

            return
        }

        push (v, lx, rx)

        val mx = lx + (rx - lx) / 2

        addSeq(v*2, lx, mx, l, r, base)
        addSeq(v*2+1, mx+1, rx, l, r, base)

        t[v].max = max (t[v*2].max, t[v*2+1].max)
    }

    fun get (v : Int, lx : Int, rx : Int, p : Int) : Int {
        if (rx == lx) return t[v].max
        else {
            push (v, lx, rx)
            val mx = lx + (rx - lx) / 2

            if (p <= mx)
                return get (v*2, lx, mx, p)
            else
                return get (v*2+1, mx+1, rx, p)
        }
    }

    fun findFirstGreaterThan (v : Int, lx : Int, rx : Int, x : Int) : Int {
        if (t[v].max <= x) return -1
        if (rx == lx) return v

        push (v, lx, rx)

        if (t[v*2].max > x)
            return findFirstGreaterThan(v*2, lx, lx + (rx - lx) / 2, x)
        else
            return findFirstGreaterThan(v*2+1, lx + (rx - lx) / 2 + 1, rx, x)
    }

    for (query in queries) {
        if (query is IQuery) {
            val l = query.l
            val r = query.r
            val x = query.x

            val base = get (1, 0, s-1, l-1)

            val preSetEnd = get (1, 0, s-1, r)

            setSeq(1, 0, s-1, l, r, base + x, x)

            val postSetEnd = get (1, 0, s-1, r)

            val dif = postSetEnd - preSetEnd
            addSeq(1, 0, s-1, r+1, cords.size-1, dif)
        } else if (query is QQuery) {
            val x = query.x

            if (t[1].max <= x)
                pln(maxCoord)
            else {
                val fgt = findFirstGreaterThan(1, 0, s-1, x)

                val step = t[fgt].addition
                val base = t[fgt-1].base

                val abs = x - base

                val VtoLx = fgt - s

                pln(CtoU[VtoLx-1]!! + (abs / step))
            }
        }
    }
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
