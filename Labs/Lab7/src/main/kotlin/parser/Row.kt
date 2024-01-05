package parser

data class Row(
    val index: Int,
    val info: String,
    val parent: Int,
    val sibling: Int
) {
    companion object {
        fun getHeader(): String {
            return "| Index | Info                           | Parent | Sibling |"
        }
    }

    override fun toString(): String {
        return String.format("| %-5d | %-30s | %-6d | %-7d |", index, info, parent, sibling)
    }
}