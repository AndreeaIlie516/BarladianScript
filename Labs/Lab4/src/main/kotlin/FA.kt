import java.io.File


class FA(fileName: String) {
    private lateinit var states: List<String>
    private lateinit var alphabet: List<String>
    private lateinit var initialState: String
    private lateinit var finalState: String
    private val transitions = mutableListOf<Triple<String, String, Char>>()

    init {
        File(fileName).readLines().forEach { line ->
            when {
                line.startsWith("states") -> states = line.substringAfter("states: ").split(",")
                line.startsWith("alphabet") -> alphabet = line.substringAfter("alphabet: ").split(",")
                line.startsWith("initial state") -> initialState = line.substringAfter("initial state: ")
                line.startsWith("final state") -> finalState = line.substringAfter("final state: ")
                line.startsWith("transitions") -> parseTransitions(line)
            }
        }
    }

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
        //println("\nLine: $line")
        line.substringAfter("transitions: ").split(";").forEach { t ->
            val (from, to, weight) = t.trim().removeSurrounding("(", ")").split(",").map { it.trim() }
            //print("\n\n!!Weight: $weight, ${weight[0]}")

            transitions.add(Triple(from, to, weight[0]))
            //print("A mers add-ul")
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
                    val sequence = readlnOrNull()
                    println("Sequence is accepted: ${sequence?.let { isSequenceAccepted(it) }}")
                }
                else -> {
                    println("You exited the application")
                    return
                }
            }
            print('\n')
        }
    }

    private fun isSequenceAccepted(sequence: String): Boolean {
        var currentState = initialState

        sequence.map { it.toString() }
        for (letter in sequence) {
            var newState: String? = null

            for ((from, to, weight) in transitions) {
                if ((from == currentState) && (weight == letter)) {
                    newState = to
                    break
                }
            }
            if (newState == null) {
                return false
            }
            currentState = newState
        }
        return currentState == finalState
    }

    fun getNext(sequence: String): String? {
        var currentState = initialState
        val acceptedSequence = mutableListOf<String>()

        val listSequence = sequence.map { it.toString() }
        for (letter in listSequence) {
            var newState: String? = null

            for ((from, to, weight) in transitions) {
                if ((from == currentState) && (weight == letter[0])) {
                    newState = to
                    break
                }
            }
            if (newState == null) {
                break
            }
            currentState = newState
            acceptedSequence.add(letter)
        }

        if (currentState != finalState){
            return null
        }
        var result = ""
        for (letter in acceptedSequence) {
            result += letter
        }
        return result
    }
}