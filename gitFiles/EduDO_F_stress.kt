import java.util.*
import java.io.*
import java.lang.Exception
import kotlin.collections.*
import kotlin.math.*
import kotlin.random.Random
import kotlin.random.nextInt

sealed class Solution (data : ArrayList<String>) {
    private var dataPointer = 0
    private val data : ArrayList<String>
    private val result = ArrayList<String>()

    init {
        this.data = data
    }

    @ExperimentalStdlibApi
    fun callSolution () : ArrayList<String> {
        solve()
        return result
    }

    @ExperimentalStdlibApi
    abstract fun solve (testIdx: Int = 0)

    protected fun pln(a : Any = "") = result.add(a.toString())

    protected fun ni() = nstr().toInt()
    protected fun nl() = nstr().toLong()
    protected fun nln() = nextToken()
    protected fun nstr() : String { while (st.hasMoreElements().not()) st = StringTokenizer(nextToken(), " "); return st.nextToken() }

    protected fun IntArray.read() : IntArray {for (i in indices) this[i] = ni(); return this}
    protected fun LongArray.read() : LongArray {for (i in indices) this[i] = nl(); return this}
    protected fun Array<String>.read() : Array<String> {for (i in indices) this[i] = nstr(); return this}

    protected fun nis(delimiter: String = " ") = ArrayList(nln().split(delimiter).map {it.toInt()})
    protected fun nls(delimiter: String = " ") = ArrayList(nln().split(delimiter).map {it.toLong()})
    protected fun nstrs(delimiter: String = " ") = ArrayList(nln().split(delimiter))

    private var st = StringTokenizer("")
    private fun nextToken () = data[dataPointer++]
}

class SolutionFast(data: ArrayList<String>) : Solution(data) {
    val Inf = 1000000007

    @kotlin.ExperimentalStdlibApi
    @Throws(Exception::class)
    override fun solve(testIdx : Int) {
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

                    this.pln(CtoU[VtoLx-1]!! + (abs / step))
                }
            }
        }
    }
}

class SolutionSlow(data: ArrayList<String>) : Solution(data) {
    @kotlin.ExperimentalStdlibApi
    override fun solve(testIdx : Int) {
        val n = ni()

        val arr = IntArray(n+1)

        var str = ""

        do {
            str = nstr()

            when (str) {
                "I" -> {
                    val l = ni()
                    val r = ni()
                    val x = ni()

                    for (j in l..r) {
                        arr[j] = x
                    }
                }

                "Q" -> {
                    val x = ni()

                    var flag = false

                    var T = 0

                    for (i in 0 until n) {
                        T += arr[i]

                        if (T > x) {
                            this.pln(i-1)
                            flag = true
                            break
                        }
                    }

                    if (!flag) pln(n)
                }
            }
        } while (str != "E")
    }
}

const val STRESS_LENGTH = 10000
const val MAXN = 10
const val MAXK = 5
const val MAXH = 10


@ExperimentalStdlibApi
fun main () {
    for (i in 0 until STRESS_LENGTH) {
        val data = ArrayList<String>()

        val n = Random.nextInt(MAXN) + 1

        data.add(n.toString())

        val k = Random.nextInt(MAXK) + 1

        for (j in 0 until k) {
            when (Random.nextInt(2)) {
                0 -> {
                    val l = Random.nextInt(n)+1
                    val r = Random.nextInt(l, n)
                    val h = Random.nextInt(MAXH) + 1

                    data.add("I $l $r $h")
                }

                1 -> {
                    data.add("Q ${Random.nextInt(MAXH)}")
                }
            }
        }

        data.add("E")

        var fast : ArrayList<String> = ArrayList()

        try {
            fast = SolutionFast(data).callSolution()
        } catch (e : Exception) {
            println("Runtime!\nData set:\n")
            println(data.joinToString("\n"))

            return
        }

        val slow = SolutionSlow(data).callSolution()

        for (j in 0 until fast.size) {
            if (fast[j] != slow[j]) {
                println("Error!\nData set:\n")
                println(data.joinToString("\n"))
                println("Your answer:\n${fast.joinToString()}")
                println("Jury answer:\n${slow.joinToString()}")

                return
            }
        }
    }

    println("SUCCESS")
}
