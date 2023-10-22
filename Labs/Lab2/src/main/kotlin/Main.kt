fun main(args: Array<String>) {
    val symbolTable = SymbolTable(107)

    symbolTable.addEntity("a", Type.CONSTANT)
    symbolTable.addEntity("avsga", Type.CONSTANT)
    symbolTable.addEntity("a", Type.IDENTIFIER)

    print("   Pos   |  Token  |  Type\n")
    print(symbolTable.hasEntity("a", Type.CONSTANT).toString() + "  |    a    | " + Type.CONSTANT + '\n')
    print(symbolTable.hasEntity("avsga", Type.CONSTANT).toString() + " |  avsga  | " + Type.CONSTANT + '\n')
    print(symbolTable.hasEntity("a", Type.IDENTIFIER).toString() + "  |    a    | " + Type.IDENTIFIER + '\n')
}