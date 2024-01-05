package org.example

import grammar.Grammar
import parser.RecursiveDescent
import java.io.File


fun main() {
    val grammar = Grammar()

    grammar.readGrammarFromFile("src/main/resources/g3.in")
    // createMenu(grammar)

    val sequence = readSequenceFromFile("src/main/resources/sequence3.in")

    if (sequence != null) {
        val parser = RecursiveDescent(grammar, sequence, "src/main/resources/table3.out")
        parser.parse()
    } else {
        println("Error reading the file.")
    }
}

private fun showMenuOptions() {
    println("""
            --- Grammar Menu ---
            Please select one of the following options:
            1. Print Non-Terminals
            2. Print Terminals
            3. Print Productions
            4 .Print Productions for a Non-Terminal
            5. Check if CFG
            0. Exit
        """.trimIndent())
}

private fun createMenu(grammar: Grammar) {
    while (true) {
        showMenuOptions()
        print("Enter your choice: ")
        val inputNumber = readlnOrNull()?.toIntOrNull()

        if (inputNumber == null || inputNumber !in 1..6) {
            println("Please insert a valid number")
            continue
        }

        when (inputNumber) {
            1 -> grammar.printNonTerminals()
            2 -> grammar.printTerminals()
            3 -> grammar.printProductions()
            4 -> {
                print("Enter Non-Terminal: ")
                val nonTerminal = readlnOrNull()
                if (nonTerminal != null) {
                    grammar.printProductionsForNonTerminal(nonTerminal)
                }
                else {
                    println("Please insert a valid non-terminal.")
                    continue
                }
            }
            5 -> println("Is CFG: ${grammar.isCFG()}")
            0 -> {
                println("Exiting...")
                return
            }
            else -> println("Invalid choice. Please try again.")

        }
        print('\n')
    }
}

fun readSequenceFromFile(filePath: String): List<String>? {
    return try {
        File(filePath).readLines()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}