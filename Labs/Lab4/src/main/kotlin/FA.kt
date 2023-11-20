import java.io.File


class FA {
    private lateinit var states: List<String>
    private lateinit var alphabet: List<String>
    private lateinit var initialState: String
    private lateinit var finalState: String
    private val transitions = mutableListOf<Triple<String, String, Int>>()

    private fun readElements() {
        File("src/main/resources/FA.in").readLines().forEach { line ->
            when {
                line.startsWith("states") -> states = line.substringAfter("states: ").split(",")
                line.startsWith("alphabet") -> alphabet = line.substringAfter("alphabet: ").split(",")
                line.startsWith("initial state") -> initialState = line.substringAfter("initial state: ")
                line.startsWith("final state") -> finalState = line.substringAfter("final state: ")
                line.startsWith("transitions") -> parseTransitions(line)
            }
        }
    }

    private fun parseTransitions(line: String) {
        line.substringAfter("transitions: ").split(";").forEach { t ->
            val (from, to, weight) = t.trim().removeSurrounding("(", ")").split(",").map { it.trim() }
            transitions.add(Triple(from, to, weight.toInt()))
        }
    }

    private fun showMenuOptions() {
        println("""
            Welcome!
            Please select one of the following options:
            1. Display the set of states
            2. Display the alphabet
            3. Display the transitions
            4. Display the initial state
            5. Display the final state
            6. Check sequence
            7. Exit
        """.trimIndent())
    }

    fun createMenu() {
        readElements()
        while (true) {
            showMenuOptions()
            print("Your input: ")
            val inputNumber = readlnOrNull()?.toIntOrNull()

            if (inputNumber == null || inputNumber !in 1..6) {
                println("Please insert a valid number")
                continue
            }

            when (inputNumber) {
                1 -> println("Set of states: $states")
                2 -> println("Alphabet: $alphabet")
                3 -> println("Transitions: $transitions")
                4 -> println("Initial state: $initialState")
                5 -> println("Final state: $finalState")
                6 -> {
                    val sequence = readSequenceOfLetters()
                    println("Sequence is accepted: ${isSequenceAccepted(sequence)}")
                }
                else -> {
                    println("You exited the application")
                    return
                }
            }
            print('\n')
        }
    }

    fun readSequenceOfLetters(): List<String> {
        print("Sequence length: ")
        val n = readln().toInt()
        val listOfLetters = mutableListOf<String>()
        for (i in 1..n) {
            print("> ")
            val letter = readlnOrNull()
            listOfLetters.add(letter!!)
        }
        return listOfLetters
    }

    fun isSequenceAccepted(sequence: List<String>): Boolean {
        var currentState = initialState
        for (letter in sequence) {
            var transitionFound = false
            for ((from, to, weight) in transitions) {
                if ((from == currentState) && (weight.toString() == letter)) {
                    currentState = to
                    transitionFound = true
                    break
                }
            }
            if (!transitionFound) {
                return false
            }
        }
        return finalState == currentState
    }
}