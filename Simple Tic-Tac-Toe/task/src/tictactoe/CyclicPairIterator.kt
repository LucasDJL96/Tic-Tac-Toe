package tictactoe

/**
 * Utility class representing a pair that can be iterated through cyclically
 *
 * @param first the first value of the pair
 * @param second the second value of the pair
 */
class CyclicPairIterator<E>(private val first: E, private val second: E): Iterator<E> {

    /** Constructor from a pair */
    constructor(pair: Pair<E, E>) : this(pair.first, pair.second)

    /** Index of the current element of the pair */
    private var i = 0

    /** Checks whether there is a next element. Always true */
    override fun hasNext() = true

    /** Moves to the other element of the pair and returns it */
    override fun next() =
        if (i == 0) {
            i = 1
            second
        } else {
            i = 0
            first
        }

    /** Returns the current element of the pair */
    fun current() = if (i == 0) {
        first
    } else {
        second
    }

}
