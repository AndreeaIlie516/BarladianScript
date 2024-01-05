package parser

import grammar.Grammar
import java.util.*

class RecursiveDescent(private val grammar: Grammar, private val sequence: List<String>, private val file: String) {
    private var position = 0
    private val workingStack = Stack<Pair<String, Int>>()
    private val inputStack = Stack<String>()
    private var state: StateType
    private val table = Table()

    internal enum class StateType {
        q, b, f, e
    }

    init {
        this.state = StateType.q
    }

    fun parse() {
        inputStack.push(grammar.getStartSymbol())
        println(partingStatus())

        while (state != StateType.f && state != StateType.e) {
            if (state == StateType.q) {
                if (isEndOfInput && inputStack.isEmpty()) {
                    success()
                } else {
                    if (!inputStack.isEmpty()) {
                        val topElem = inputStack.peek()
                        if (grammar.isNonTerminal(topElem)) {
                            expand()
                        } else if (position < sequence.size && topElem == sequence[position]) {
                            advance()
                        } else {
                            momentaryInsuccess()
                        }
                    }
                }
            } else if (state == StateType.b) {
                val topElem = workingStack.peek().first
                if (grammar.isTerminal(topElem)) {
                    back()
                } else {
                    anotherTry()
                }
            }
        }

        if (state == StateType.e) {
            println("Parsing Error")
        } else {
            println("Sequence Accepted")
            buildStringOfProductions(workingStack)
            table.constructTable(workingStack, grammar)
            print(table.toString())
            table.printToFile(file)
        }
    }

    private fun buildStringOfProductions(workingStack: Stack<Pair<String, Int>>) {
        val sb = StringBuilder("Productions String: ")
        for ((elemName, second) in workingStack) {
            if (grammar.isNonTerminal(elemName)) sb.append(elemName).append(second + 1).append(" ")
        }
        println(sb)
    }

    private fun expand() {
        println("-----Expand----\n")
        val nonTerminal = inputStack.pop()
        val firstProduction = grammar.getProductionsForNonTerminal(nonTerminal)!![0]
        val pair = Pair(nonTerminal, 0)
        workingStack.push(pair)
        for (i in firstProduction.indices.reversed()) {
            inputStack.push(firstProduction[i])
        }
        println(partingStatus())
    }

    private fun advance() {
        println("-----Advance----\n")
        val terminal = inputStack.pop()
        position++
        val pair = Pair(terminal, -1)
        workingStack.push(pair)
        println(partingStatus())
    }

    private fun momentaryInsuccess() {
        println("-----Momentary Insuccess----\n")
        state = StateType.b
        println(partingStatus())
    }

    private fun back() {
        println("-----Back----\n")
        val terminal = workingStack.pop().first
        position--
        inputStack.push(terminal)
        println(partingStatus())
    }

    private fun anotherTry() {
        println("-----Another Try----\n")
        println("Input stack: ${inputStack.reversed()}")
        val pair = workingStack.pop()
        if (pair.second != -1) {
            val nonTerminal = pair.first
            val productionNumber = pair.second

            val productions = grammar.getProductionsForNonTerminal(nonTerminal)

            val isLastProduction = productionNumber + 1 == productions!!.size

            val lastProductions = productions[productionNumber]
            if (!isLastProduction) {
                state = StateType.q

                val nextProduction = productions[productionNumber + 1]


                val newPair = Pair(nonTerminal, productionNumber + 1)
                workingStack.push(newPair)

                for (i in lastProductions.indices) {
                    inputStack.pop()
                }

                for (elem in nextProduction.reversed()) {
                    inputStack.push(elem)
                }
            } else {
                for (i in lastProductions.indices) {
                    inputStack.pop()
                }
                inputStack.push(nonTerminal)
            }
            if (position == 0 && nonTerminal == grammar.getStartSymbol()) state = StateType.e
        }
        println(partingStatus())
    }

    private fun success() {
        state = StateType.f
        println(partingStatus())
    }

    private val isEndOfInput: Boolean
        get() = position >= sequence.size

    private fun partingStatus(): String {
        return "State: $state, Position: $position, Working stack: $workingStack, Input stack: ${inputStack.reversed()}\n"
    }
}
