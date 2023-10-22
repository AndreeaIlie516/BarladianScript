enum class Type(val code: Int) {
    IDENTIFIER(1),
    CONSTANT(2),
    NONE(-1)
}

class HashTable(
    private var size: Int = 107
) {
    var buckets: Array<MutableList<Pair<String, Type>>> = Array(size) { mutableListOf() }

    private fun getHashSum(element: String): Int {
        var sum = 0
        for(i in element.indices) {
            sum += element[i].code
        }
        return sum
    }
    fun hash(element: String): Int {
        return getHashSum(element) % size
    }

    fun get(element: String, type: Type): Pair<Int, Int>? {
        val posInBucket = hash(element)

        buckets[posInBucket].forEachIndexed { posInList, pair ->

            if (pair.first == element && pair.second==type)
                return Pair(posInBucket, posInList)
        }
        return null
    }

    fun findByPair(posInBucket: Int, posInList: Int): Pair<String, Type> {
        if (posInBucket >= size)
            throw Exception("Invalid position given")
        if (posInList >= buckets[posInBucket].size)
            throw Exception("Invalid position given")
        return buckets[posInBucket][posInList]
    }

    fun insert(element:String, type: Type): Pair<Int, Int> {
        val lookup = get(element, type)
        if(lookup != null) {
            return lookup
        }

        val posInBucket = hash(element)
        val posInList = buckets[posInBucket].size
        buckets[posInBucket].add(Pair(element, type))
        return Pair(posInBucket, posInList)
    }
}