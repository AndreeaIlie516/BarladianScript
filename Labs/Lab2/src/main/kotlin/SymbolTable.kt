class SymbolTable(
    size: Int = 107
) {
    private var table = HashTable(size)

    fun addEntity(name: String, type:Type): Pair<Int, Int> {
        return table.insert(name, type)
    }

    fun hasEntity(name:String, type: Type): Pair<Int, Int>? {
        return table.get(name, type)
    }
    fun getEntity(posInBucket:Int, posInList: Int): Pair<String, Type> {
        return table.findByPair(posInBucket, posInList)
    }
}