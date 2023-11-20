import java.io.File

class Scanner(
    private val program: String,
    private val tokenList: List<String>
) {
    private val symbolTable = SymbolTable()
    private val pif = mutableListOf<Pair<Int, Pair<Int, Int>>>()

    private var index = 0
    private var currentLine = 1

    private var errors = mutableListOf<String>()
    private var errorMessage= ""

    private var oneCharacterTokenList = mutableListOf<String>()

    private val identifierCode = -1
    private val constantCode = -2

    private fun nextToken() {
        if (skipWhitespace())
            return
        if (skipComment())
            return

        if (index == program.length)
            return
        for (function in listOf(
            Scanner::checkIfToken,
            Scanner::checkIfStringConstant,
            Scanner::checkIfIntConstant,
            Scanner::checkIfIdentifier
        ))
            if (function(this))
                return
        //throw ScannerException("Lexical error: Cannot classify token", currentLine)
        index++
        errorMessage = "Lexical error: Cannot classify token at line $currentLine\n"
        if(!errors.contains(errorMessage))
            errors.add(errorMessage)
        return
    }

    fun scan(file: String) {
        createOneCharacterTokenList()
        while (index in program.indices)
            nextToken()
        if(errors.size == 0) {
            print("\nThere is no error in the program. \n\n")

            val fa = FA()
            fa.createMenu()
            print("Enter a sequence to check if it's accepted by the FA: ")

            val sequence = fa.readSequenceOfLetters()

            val isAccepted = fa.isSequenceAccepted(sequence)
            if (isAccepted) {
                println("The sequence '$sequence' is accepted by the FA.")
            } else {
                println("The sequence '$sequence' is not accepted by the FA.")
            }
        }
        else {
            print("\nErrors: \n")
            for (error in errors)
                print(error + '\n')
            print('\n')
        }
        symbolTable.printToFile(file)
        File("src/main/resources/" + file + "PIF.out").bufferedWriter().use { out ->
            pif.forEachIndexed { _, pair ->
                out.write("" + pair.first + " " + pair.second + '\n')
            }
        }
    }

    private fun skipWhitespace(): Boolean {
        if (program[index] == '\n'){
            currentLine++
            index++
            //print("Whitespace skipped\n")
            return true
        }
        while (index < program.length &&
            (program[index] == ' ' || program[index] == '\n' || program[index] == '\t')) {
            if (program[index] == '\n'){
                currentLine++
            }
            index++
            //print("Whitespace skipped\n")
            return true
        }
        return false
    }

    private fun skipComment(): Boolean {
        if (program.startsWith("//", index)) {
            while (index < program.length && program[index] != '\n'){
                index++
            }
            if(index < program.length){
                index++
            }
            currentLine++
            print("Comment skipped\n")
            return true
        }
        return false
    }

    private fun checkIfIdentifier(): Boolean {
        if (index < program.length &&
            !((oneCharacterTokenList.contains(program[index-1].toString())) || program[index-1] == ' ' || program[index-1] == '\n' || program[index-1] == '\t')) {
            return false
        }

        val regex = Regex("^([a-zA-Z_][a-zA-Z0-9_]*)").find(program.substring(index))
        if(regex != null) {
            val id = regex.value
            index += id.length

            val posInSymbolTable = symbolTable.addEntity(id, Type.IDENTIFIER)
            print("Identifier: $id\n")
            pif.add(Pair(identifierCode, posInSymbolTable))
            return true
        }
        return false
    }

    private fun checkIfIntConstant(): Boolean {
        val regex = Regex("^([+-]?[1-9][0-9]*|0)").find(program.substring(index))
        if(regex != null) {
            val const = regex.value
            index += const.length

            val posInSymbolTable = symbolTable.addEntity(const, Type.CONSTANT)
            print("Int Constant: $const\n")
            pif.add(Pair(constantCode, posInSymbolTable))
            return true
        }
        return false
    }

    private fun checkIfStringConstant(): Boolean {
        val regex = Regex("^\"(.*?)\"").find(program.substring(index))

        if(regex != null) {
            val const = regex.value
            index += const.length + 2

            val posInSymbolTable = symbolTable.addEntity(const, Type.CONSTANT)
            print("String Constant: $const\n")
            pif.add(Pair(constantCode, posInSymbolTable))
            return true
        }
        return false
    }

    private fun checkIfToken(): Boolean {
        val sortedTokenList = tokenList.sortedByDescending { it.length }
        for ((tokenIndex, token) in sortedTokenList.withIndex()) {
            val regexPattern = "^${Regex.escape(token)}"
            val regex = Regex(regexPattern)
            val match = regex.find(program.substring(index))

            if (match != null) {
                val matchedToken = match.value
                print("Token: $matchedToken\n")
                pif.add(Pair(tokenIndex, Pair(-1, -1)))
                index += matchedToken.length
                return true
            }
        }
        return false
    }
    private fun createOneCharacterTokenList() {
        for(token in tokenList) {
            if(token.length == 1) {
                oneCharacterTokenList.add(token)
            }
        }
    }
}