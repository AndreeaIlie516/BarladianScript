package parser

fun ConvertPIFToSequence(pif: List<Pair<Int, Pair<Int, Int>>>, tokens: List<String>, symbolTable: Map<Pair<Int, Int>, String>): List<String> {
    val sequence = mutableListOf<String>()

    pif.forEach { (code, position) ->
        when (code) {
            -1 -> { // Identifier
                // Retrieve identifier from symbol table
                val identifier = symbolTable[position] ?: "Unknown identifier"
                sequence.add(identifier)
            }
            -2 -> { // Constant
                // Retrieve constant from symbol table
                val constant = symbolTable[position] ?: "Unknown constant"
                sequence.add(constant)
            }
            else -> { // Token
                // Retrieve token from token file
                val token = tokens.getOrNull(code) ?: "Unknown token"
                sequence.add(token)
            }
        }
    }
    return sequence
}