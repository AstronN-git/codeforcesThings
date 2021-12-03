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

    open class Query ()

    class IQuery (var l : Int, var r : Int, var x : Int) : Query()
    class QQuery (var x : Int) : Query ()

    val queries = ArrayList<Query>()
    val cords = TreeSet<Int>()
    cords.add(0)

    var str: String

    var maxCord = -1

    do {
        str = nstr()
        when (str) {
            "Q" -> queries.add(QQuery(ni()))
            "I" -> {
                val l = ni()
                val r = ni()

                cords.add(l-1)
                cords.add(l)
                cords.add(r)
                cords.add(r+1)

                maxCord = max (maxCord, r)

                queries.add(IQuery(l, r, ni()))
            }
        }
    } while (str != "E")

    cords.add(maxCord + 1)

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

    data class Node (var base : Int = 0, var addition : Int = 0, var isSetting : Boolean = false, var isAdding : Boolean = false, var max : Int = 0)

    val t = Array (2 * s) {Node()}

    fun push (v : Int, lx : Int, rx : Int) {
        val mx = lx + (rx - lx) / 2

        if (t[v].isSetting) {
            t[v*2] = Node (
                t[v].base,
                t[v].addition,
                isSetting = true,
                isAdding = false,
                if (t[v].addition < 0)
                    t[v].base
                else
                    t[v].base + (CtoU[mx+1]!! - CtoU[lx]!!) * t[v].addition
                    )

            t[v*2+1] = Node (
                t[v].base + t[v].addition * (CtoU[mx+1] !! - CtoU[lx]!! ),
                t[v].addition,
                isSetting = true,
                isAdding = false,
                if (t[v].addition < 0)
                    t[v].base + t[v].addition * (CtoU[mx+1] !! - CtoU[lx]!!)
                else
                    t[v].base + t[v].addition * (CtoU[rx + 1]!! - CtoU[lx]!!)
                    )

            t[v] = Node (
                0, 0, false, false, t[v].max
                    )
        }

        if (t[v].isAdding) {
            t[v*2] = Node (
                t[v*2].base + t[v].base,
                t[v*2].addition + t[v].addition,
                t[v*2].isSetting,
                !t[v*2].isSetting,
                t[v*2].max + t[v].base
                    )


            t[v*2+1] = Node (
                t[v*2+1].base + t[v].base,
                t[v*2+1].addition + t[v].addition,
                t[v*2+1].isSetting,
                !t[v*2+1].isSetting,
                t[v*2+1].max + t[v].base
            )

            t[v] = Node (
                0,
                0,
                false,
                false,
                t[v].max
                    )
        }
    }

    fun get (v : Int, lx : Int, rx : Int, p : Int) : Node  {
        if (lx == rx) return t[v]
        else {
            push(v, lx, rx)
            val mx = lx + (rx - lx) / 2

            if (p <= mx)
                return get (v * 2, lx, mx, p)
            else
                return get (v*2+1, mx+1, rx, p)
        }
    }

    fun setOn (v : Int, lx : Int, rx : Int, l : Int, r : Int, base : Int, addition : Int) {
        if (lx > r || rx < l) return
        if (lx >= l && rx <= r) {
            t[v] = Node (
                base,
                addition,
                true,
                false,
                if (addition < 0) base else base + (CtoU[rx+1]!! - CtoU[lx]!!) * addition
                    )

            return
        }

        push (v, lx, rx)
        val mx = lx + (rx - lx) / 2


        if (l > mx)
            setOn(v*2+1, mx+1, rx, l, r, base, addition)
        else if (r <= mx)
            setOn(v*2, lx, mx, l, r, base, addition)
        else {
            setOn(v*2, lx, mx, l, r, base, addition)
            setOn(v*2+1, mx+1, rx, l, r, base + addition * (min (CtoU[mx+1]!! - CtoU[lx]!!, CtoU[mx+1]!! - CtoU[l]!!)), addition)
        }
        t[v].max = max (t[v*2].max, t[v*2+1].max)
    }

    fun addOn (v : Int, lx : Int, rx : Int, l : Int, r : Int, base : Int) {
        if (lx > r || rx < l) return
        if (lx >= l && rx <= r) {
            if (rx != lx) {
                push(v, lx, rx)
                t[v] = Node (
                    base,
                    0,
                    isSetting = false,
                    isAdding = true,
                    t[v].max + base
                )
            } else {
                t[v] = Node (
                    t[v].base + base,
                    t[v].addition,
                    false,
                    false,
                    t[v].max + base
                        )
            }

            return
        }

        push (v, lx, rx)
        val mx = lx + (rx - lx) / 2

        addOn(v*2, lx, mx, l, r, base)
        addOn(v*2+1, mx+1, rx, l, r, base)

        t[v].max = max (t[v*2].max, t[v*2+1].max)
    }

    fun changeRainRaise (l : Int, r : Int, x : Int) {
        val OnL = get(1, 0, s-1, l-1)
        val OnRPre = get (1, 0, s-1, r)
        setOn(1, 0, s-1, l, r, OnL.base + OnL.addition * (CtoU[l]!! - CtoU[l-1]!!), x)
        val OnRPost = get (1, 0, s-1, r)

        val dif = (OnRPost.base + OnRPost.addition * (CtoU[r+1]!! - CtoU[r]!!)) - (OnRPre.base + OnRPre.addition * (CtoU[r+1]!! - CtoU[r]!!))

        addOn (1, 0, s-1, r+1, s-1, dif)
    }

    fun firstGreaterThan (v : Int, lx : Int, rx : Int, x : Int) : Int {
        if (t[v].max <= x) return -1
        if (rx == lx) return lx


        push (v, lx, rx)
        val mx = lx + (rx - lx) / 2

        if (t[v*2].max > x)
            return firstGreaterThan(v*2, lx, mx, x)
        else
            return firstGreaterThan(v*2+1, mx+1, rx, x)
    }

    for (query in queries) {
        if (query is IQuery) {
            changeRainRaise(query.l, query.r, query.x)
        } else if (query is QQuery) {
            val x = query.x

            if (t[1].max <= x)
                pln(n)
            else {
                val fgt = firstGreaterThan(1, 0, s-1, x)
                val l = get (1, 0, s-1, fgt-1)
                val r = get(1, 0, s-1, fgt)

                if (l.addition == 0) {
                    pln(CtoU[fgt]!!-1)
                } else {
                    var t_ = CtoU[fgt-1]!!
                    val abs = x - (l.base + l.addition * (CtoU[fgt]!! - CtoU[fgt-1]!!))
                    t_ += abs / r.addition

                    pln(t_)
                }
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
