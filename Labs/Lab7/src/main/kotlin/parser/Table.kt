package parser

import grammar.Grammar
import java.io.File
import java.util.*

class Table {

    private val rows = mutableMapOf<Int, Row>()

    fun constructTable(workingStack: Stack<Pair<String, Int>>, grammar: Grammar) {
        var index = 0

        val initialState = Row(index, workingStack.first().first, -1, -1)
        rows[0] = initialState

        val stack = Stack<Pair<String, Int>>()

        workingStack.forEach { elem ->
            if (elem.second != -1) { // is non-terminal
                stack.add(0, elem)
            }
        }

        val parents = Stack<Int>()
        parents.add(0)
        index++

        while (stack.isNotEmpty()) {
            val pair = stack.pop()
            val nonTerminal = pair.first
            val parent = parents.pop()
            var sibling = -1

            grammar.getProductionsForNonTerminal(nonTerminal)?.get(pair.second)?.forEach { elem ->
                val row = Row(index, elem, parent, sibling)
                rows[index] = row
                sibling = index
                if (grammar.isNonTerminal(elem)) parents.add(0, index)
                index++
            }
        }
    }

    override fun toString(): String {
        if (rows.isEmpty()) return "The table is empty."

        val border = "+-------+--------------------------------+--------+---------+\n"
        val stringBuilder = StringBuilder(border)
        stringBuilder.append(Row.getHeader()).append("\n").append(border)

        rows.values.forEach { row ->
            stringBuilder.append(row.toString()).append("\n")
        }
        stringBuilder.append(border)
        return stringBuilder.toString()
    }

    fun printToFile(file: String) {
        File(file).writeText(this.toString())
    }
}