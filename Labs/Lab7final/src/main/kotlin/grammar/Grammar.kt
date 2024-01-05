package grammar

import java.io.File

class Grammar {
    private val nonTerminals: MutableSet<String> = mutableSetOf()
    private val terminals: MutableSet<String> = mutableSetOf()
    private val productions: MutableMap<String, MutableList<List<String>>> = mutableMapOf()
    private lateinit var startSymbol: String

    fun readGrammarFromFile(filePath: String) {
        try {
            val lines = File(filePath).readLines()

            nonTerminals.addAll(lines[0].split(" "))
            terminals.addAll(lines[1].split(" "))
            startSymbol = lines[2].trim()

            lines.drop(3).forEach { line ->
                val (lhs, rhs) = line.trim().split("->").map { it.trim() }
                rhs.split("|").forEach { production ->
                    val rhsParts = production.trim().split("\\s+".toRegex())
                    productions.getOrPut(lhs) { mutableListOf() }.add(rhsParts)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun printNonTerminals() = println("Non-terminals: $nonTerminals")
    fun getNonTerminals(): Set<String> = nonTerminals

    fun printTerminals() = println("Terminals: $terminals")
    fun getTerminals(): Set<String> = terminals

    fun printProductions() = println("Productions: $productions")
    fun getProductions(): Map<String, List<List<String>>> = productions

    fun printProductionsForNonTerminal(nonTerminal: String) =
        println("Productions for $nonTerminal: ${productions[nonTerminal]}")
    fun getProductionsForNonTerminal(nonTerminal: String): List<List<String>>? = productions[nonTerminal]

    fun getStartSymbol(): String = startSymbol

    fun isCFG(): Boolean = productions.keys.all { it in nonTerminals && " " !in it }

    fun isNonTerminal(element: String): Boolean = element in nonTerminals
    fun isTerminal(element: String): Boolean = element in terminals
}