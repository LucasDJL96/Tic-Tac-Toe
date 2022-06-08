package tictactoe

/** Main function. Controls the flow of the program */
fun main() {
    println("Enter cells:")
    val board = Board()

    val players = CyclicPairIterator(Board.Cell.X, Board.Cell.O)

    while (board.analyzeState() == Board.State.UNFINISHED) {
        val player = players.current()
        board.printBoard()
        println("Enter the coordinates:")
        try {
            val (row, col) = readln().split(" ").map(String::toInt)
            board.putMove(player, row, col)
            players.next()
        } catch (e: NumberFormatException) {
            println("You should enter numbers!")
            continue
        } catch (e: Board.OccupiedCellException) {
            println("This cell is occupied! Choose another one!")
            continue
        } catch (e: IllegalArgumentException) {
            println("Coordinates should be from 1 to 3!")
            continue
        }
    }

    board.printBoard()
    println(when (board.analyzeState()) {
        Board.State.UNFINISHED -> throw IllegalStateException()
        Board.State.DRAW -> "Draw"
        Board.State.X_WINS -> "X wins"
        Board.State.O_WINS -> "O wins"
        Board.State.IMPOSSIBLE -> throw IllegalStateException()
    })
}
