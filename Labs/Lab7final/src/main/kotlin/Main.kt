import grammar.Grammar
import parser.ConvertPIFToSequence
import parser.RecursiveDescent
import scanner.Scanner
import scanner.ScannerException
import java.io.File


fun main() {
    runSequence()
    runPIF()
}

private fun runSequence() {
    val grammar = Grammar()

    grammar.readGrammarFromFile("src/main/resources/g1.in")

    var sequence = readSequenceFromFile("src/main/resources/sequence1.in")

    if (sequence != null) {
        val parser = RecursiveDescent(grammar, sequence, "src/main/resources/table1.out")
        parser.parse()
    } else {
        println("Error reading the file.")
    }
}

private fun runPIF() {
    val tokens = File("src/main/resources/token.in").readLines()
    val program = File("src/main/resources/p1.txt").readText()

    val scanner = Scanner(program, tokens)

    try {
        scanner.scan("p1")
    } catch (exception: ScannerException) {
        println("${exception.message}\nline: ${exception.code}")
        return
    }

    val ST = readSymbolTable("src/main/resources/p1ST.out")
    val PIF = readPIF("src/main/resources/p1PIF.out")
    val convertedSequence = ConvertPIFToSequence(PIF, tokens, ST)

    val grammar = Grammar()

    grammar.readGrammarFromFile("src/main/resources/g2.in")

    val parser = RecursiveDescent(grammar, convertedSequence, "src/main/resources/table2.out")
    parser.parse()
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

fun readPIF(filePath: String): List<Pair<Int, Pair<Int, Int>>> {
    val pif = mutableListOf<Pair<Int, Pair<Int, Int>>>()
    File(filePath).forEachLine { line ->
        val newLine = line.replace("(", "").replace(")", ""). replace(",", "")
        val parts = newLine.trim().split(" ")
        val code = parts[0].toInt()
        val hashKey = parts[1].toInt()
        val position = parts[2].toInt()
        pif.add(Pair(code, Pair(hashKey, position)))
    }
    return pif
}

fun readSymbolTable(filePath: String): Map<Pair<Int, Int>, String> {
    val symbolTable = mutableMapOf<Pair<Int, Int>, String>()
    File(filePath).forEachLine { line ->
        val newLine = line.replace("(", "").replace(")", ""). replace(",", "")
        val parts = newLine.trim().split(" ")
        val hashKey = parts[0].toInt()
        val position = parts[1].toInt()
        val value = parts[2]
        symbolTable[Pair(hashKey, position)] = value
    }
    return symbolTable
}

fun readTokens(filePath: String): List<String> {
    val tokens = mutableListOf<String>()
    File(filePath).forEachLine { line ->
        tokens.add(line.trim())
    }
    return tokens
}