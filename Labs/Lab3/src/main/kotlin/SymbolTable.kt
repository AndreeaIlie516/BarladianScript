import java.io.File

class SymbolTable(
    size: Int = 107
) {
    var table = HashTable(size)

    fun addEntity(name: String, type:Type): Pair<Int, Int> {
        return table.insert(name, type)
    }

    fun hasEntity(name:String, type: Type): Pair<Int, Int>? {
        return table.get(name, type)
    }
    fun getEntity(posInBucket:Int, posInList: Int): Pair<String, Type> {
        return table.findByPair(posInBucket, posInList)
    }

    fun printToFile(file: String) {
        File("src/main/resources/" + file + "ST.out").bufferedWriter().use { out ->
            table.buckets.forEachIndexed { bucketIndex, bucket ->
                bucket.forEachIndexed { listIndex, pair ->
                    out.write("" + pair.first + " " + pair.second + '\n')
                }
            }
        }
    }
}