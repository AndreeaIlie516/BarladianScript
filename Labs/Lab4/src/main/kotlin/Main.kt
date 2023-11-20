import java.io.File

fun main(args: Array<String>) {
//    val fa = FA("src/main/resources/identifier.in")
//    fa.createMenu()

    val tokens = File("src/main/resources/token.in").readLines()
    for (i in 3..3) {
        val program = File("src/main/resources/p$i.txt").readText()

        val scanner = Scanner(program, tokens)

        try {
            scanner.scan("p$i")
        } catch (exception: ScannerException) {
            println("${exception.message}\nline: ${exception.code}")
            return
        }
    }

    val program = File("src/main/resources/p1err.txt").readText()

    val scanner = Scanner(program, tokens)

    try {
        scanner.scan("p1err")
    } catch (exception: ScannerException) {
        println("${exception.message}\nline: ${exception.code}")
        return
    }
}