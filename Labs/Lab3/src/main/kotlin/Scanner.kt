import java.io.File

class Scanner(
    private val program: String,
    private val tokenList: List<String>
) {
    private val symbolTable = SymbolTable()

    private val pif = mutableListOf<Pair<Int, Pair<Int, Int>>>()

    private val table = mutableListOf<String>()
    private var index = 0
    private var currentLine = 1

    private fun nextToken() {
        skipWhitespace()
        skipComment()

        if (index == program.length)
            return
        for (function in listOf(
            Scanner::checkIfToken,
            Scanner::checkIfStringConstant,
            Scanner::checkIfIntConstant,
            Scanner::checkIfIdentifier))
            if (function(this))
                return
        throw ScannerException("Lexical error: Cannot classify token", currentLine)
    }

    fun scan(file: String) {
        while (index in program.indices)
            nextToken()
        symbolTable.printToFile(file)
        File("src/main/resources/" + file + "PIF.out").bufferedWriter().use { out ->
           pif.forEachIndexed { index, pair ->
                out.write("" + pair.first + " " + pair.second + '\n')
            }
        }
    }

    private fun skipWhitespace() {
        while (index < program.length && program[index].isWhitespace()) {
            if (program[index] == '\n')
                ++currentLine
            ++index
        }
    }

    private fun skipComment() {
        if (program.startsWith("//", index)) {
            while (index < program.length && program[index] != '\n')
                ++index
            ++currentLine
            return
        }
    }

    private fun checkIfIdentifier(): Boolean {
        val regex = Regex("^([a-zA-Z_][a-zA-Z0-9_]*)").find(program.substring(index))
        if(regex != null) {
            val id = regex.groups[1]!!.value
            index += id.length

            val posInSymbolTable = symbolTable.addEntity(id, Type.IDENTIFIER)
            print("\nIdentifier: $id, pos: $posInSymbolTable \n")
            //pif.add(Pair(position.positionType.code, position.pair))
            pif.add(Pair(getPosInTable(id), symbolTable.hasEntity(id, Type.IDENTIFIER)) as Pair<Int, Pair<Int, Int>>)
            return true
        }
        return false
    }

    private fun checkIfIntConstant(): Boolean {
        val regex = Regex("^([+-]?[1-9][0-9]*|0)").find(program.substring(index))
        if(regex != null) {
            val const = regex.groups[1]!!.value
            index += const.length

            val posInSymbolTable = symbolTable.addEntity(const, Type.CONSTANT)
            print("\nInt Constant: $const, pos: $posInSymbolTable \n")
            pif.add(Pair(getPosInTable(const), symbolTable.hasEntity(const, Type.IDENTIFIER)) as Pair<Int, Pair<Int, Int>>)
            return true
        }
        return false
    }

    private fun checkIfStringConstant(): Boolean {
        val pattern = """^"([^"]*)""""
        val regex = Regex(pattern).find(program.substring(index))
        if(regex != null) {
            val const = regex.groups[1]!!.value
            index += const.length + 2

            val posInSymbolTable = symbolTable.addEntity(const, Type.CONSTANT)

            print("\nString Constant: $const, pos: $posInSymbolTable \n")
            pif.add(Pair(getPosInTable(const), symbolTable.hasEntity(const, Type.IDENTIFIER)) as Pair<Int, Pair<Int, Int>>)
            return true
        }
        return false
    }

    private fun checkIfToken(): Boolean {
        val regex = Regex("^([a-zA-Z]*)").find(program.substring(index))
        for ((tokenIndex, token) in tokenList.withIndex()) {
            if (program[index].toString() == token) {
                print("Token: ${program[index]}\n")
                pif.add(Pair(tokenIndex, Pair(-1,-1)))
                index++
                return true
            }
             else {
                if ((program[index].toString() + program[index+1].toString()) == token) {
                    print("Token: ${program[index]}\n")
                    pif.add(Pair(tokenIndex, Pair(-1,-1)))
                    index += 2
                    return true
                }
                else {
                    if(regex != null) {
                        val tokenInProgram = regex.groups[1]!!.value
                        if(tokenInProgram == token) {
                            print("Token: $tokenInProgram\n")
                            pif.add(Pair(tokenIndex, Pair(-1,-1)))
                            index += tokenInProgram.length
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun getPosInTable(name: String): Int {
        for ((tableIndex, elem) in table.withIndex()) {
            if(elem == name)
                return tableIndex
        }
        return -1
    }
}