import Grammar.Grammar

fun main() {
    val grammar = Grammar()
    grammar.readGrammarFromFile("src/main/resources/g1.in")
    createMenu(grammar)
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