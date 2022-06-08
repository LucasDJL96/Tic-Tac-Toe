package tictactoe

/**
 * Class representing the board for the game
 *
 * @param initialState a String representing the initial state of the game.
 * The string should consist of 9 characters of '_', 'X', 'O' in a row. The state
 * initial state of the game is computed by filling the rows in order.
 */
class Board(private val initialState: String = "_________") {

    /** Double list representing the current cells on the board */
    private val cells: List<MutableList<Cell>> = buildList {
        val chars = initialState.iterator()
        for (i in 0..2) {
            val row = mutableListOf<Cell>()
            for (j in 0..2) row.add(Cell.fromChar[chars.nextChar()]!!)
            add(row)
        }
    }

    /**
     * Puts a cell on the board according to the row and column specified
     *
     * @param cell the new cell
     * @param _row the row of the cell
     * @param _col the column of the cell
     *
     * @throws IllegalArgumentException if the row or column are not between 1 and 3
     * @throws IllegalArgumentException if the cell is an empty cell
     * @throws OccupiedCellException if the row and column already contain a non-empty cell
     */
    fun putMove(cell: Cell, _row: Int, _col: Int) {
        require(_row in 1..3 && _col in 1..3)
        require(cell != Cell.EMPTY)
        val row = _row - 1
        val col = _col - 1
        if (cells[row][col] != Cell.EMPTY) throw OccupiedCellException()
        cells[row][col] = cell
    }

    /** Prints the current board */
    fun printBoard() {
        println("---------")
        for (i in 0..2) {
            print("| ")
            for (j in 0..2) {
                val symbol = cells[i][j].outSymbol
                print("$symbol ")
            }
            println("|")
        }
        println("---------")
    }

    /**
     * Analyzes the current state of the game
     *
     * @return State the current state of the game
     */
    fun analyzeState(): State {
        val flatBoard = cells.flatten()
        val emptyCells = flatBoard.count(Cell.EMPTY::equals)
        val xCells = flatBoard.count(Cell.X::equals)
        val oCells = flatBoard.count(Cell.O::equals)
        val hasWonX = hasThreeInRow(Cell.X)
        val hasWonO = hasThreeInRow(Cell.O)

        return if (oCells > xCells) State.IMPOSSIBLE
        else if (xCells > oCells + 1) State.IMPOSSIBLE
        else if (hasWonX && hasWonO) State.IMPOSSIBLE
        else if (hasWonX) State.X_WINS
        else if (hasWonO) State.O_WINS
        else if (emptyCells > 0) State.UNFINISHED
        else State.DRAW
    }

    /**
     * Checks if a cell occurs three times in a row, column or diagonal
     *
     * @param cell the cell
     *
     * @return Boolean whether the cell occurs three times in a row, column or diagonal
     */
    private fun hasThreeInRow(cell: Cell): Boolean {
        for (i in 0..2) {
            if (cells[i].all(cell::equals)) return true
        }
        for (j in 0..2){
            val col = buildList {
                for (i in 0..2) add(cells[i][j])
            }
            if (col.all(cell::equals)) return true
        }
        val diagonal1 = buildList {
            for (i in 0..2) {
                add(cells[i][i])
            }
        }
        if (diagonal1.all(cell::equals)) return true

        val diagonal2 = buildList {
            for (i in 0..2) {
                add(cells[i][2 - i])
            }
        }
        if (diagonal2.all(cell::equals)) return true

        return false
    }

    /**
     * Enum class representing a cell
     *
     * @param inSymbol represents the symbol to use to read this cell from input
     * @param outSymbol represent the symbol to use to write this cell to output
     */
    enum class Cell(val inSymbol: Char, val outSymbol: Char = inSymbol) {
        /** X cell */
        X('X'),
        /** O cell */
        O('O'),
        /** Empty cell */
        EMPTY('_', ' ');

        companion object {
            /** Map to get cells from their inSymbol */
            val fromChar = buildMap {
                for (cell in Cell.values()) {
                    put(cell.inSymbol, cell)
                }
            }
        }
    }

    /** Enum class representing the state of a game */
    enum class State {
        UNFINISHED,
        DRAW,
        X_WINS,
        O_WINS,
        IMPOSSIBLE
    }

    /** Exception for when an occupied cell is trying to be overwritten */
    class OccupiedCellException : IllegalArgumentException()

}
