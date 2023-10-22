import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SymbolTableTest {
    @Test
    fun test() {
        val symbolTable = SymbolTable(107)

        symbolTable.addEntity("a", Type.CONSTANT)
        symbolTable.addEntity("avsga", Type.CONSTANT)
        symbolTable.addEntity("a", Type.IDENTIFIER)

        assertEquals(symbolTable.getEntity(97, 0).first, "a")
        assertEquals(symbolTable.getEntity(97, 0).second, Type.CONSTANT)
        assertEquals(symbolTable.getEntity(102, 0).first, "avsga")
        assertEquals(symbolTable.getEntity(102, 0).second, Type.CONSTANT)
        assertEquals(symbolTable.getEntity(97, 1).first, "a")
        assertEquals(symbolTable.getEntity(97, 1).second, Type.IDENTIFIER)

        symbolTable.hasEntity("a", Type.CONSTANT)?.let { assertEquals(it.first, 97) }
        symbolTable.hasEntity("a", Type.CONSTANT)?.let { assertEquals(it.second, 0) }
        symbolTable.hasEntity("avsga", Type.CONSTANT)?.let { assertEquals(it.first, 102) }
        symbolTable.hasEntity("avsga", Type.CONSTANT)?.let { assertEquals(it.second, 0) }
        symbolTable.hasEntity("a", Type.IDENTIFIER)?.let { assertEquals(it.first, 97) }
        symbolTable.hasEntity("a", Type.IDENTIFIER)?.let { assertEquals(it.second, 1) }
    }

}