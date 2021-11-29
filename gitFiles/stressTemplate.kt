import java.util.*
import java.io.*
import kotlin.collections.*
import kotlin.math.*
import kotlin.random.Random

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
    @kotlin.ExperimentalStdlibApi
    override fun solve(testIdx : Int) {
        // fast solution
        // call pln with this.pln() !!
    }
}

class SolutionSlow(data: ArrayList<String>) : Solution(data) {
    @kotlin.ExperimentalStdlibApi
    override fun solve(testIdx : Int) {
        // slow solution
        // call pln with this.pln() !!
    }
}

const val STRESS_LENGTH = 10000

@ExperimentalStdlibApi
fun main () {
    for (i in 0 until STRESS_LENGTH) {
        val data = ArrayList<String>()

        // data generator here
        
        val fast = SolutionFast(data).callSolution()
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
