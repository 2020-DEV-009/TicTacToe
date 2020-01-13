package com.bnp.tictactoe.extensions

import com.bnp.tictactoe.data.Player
import com.bnp.tictactoe.vm.Result

private val winnerTemplates = arrayOf(
    Regex("CCC......"), //First row
    Regex("...CCC..."), //Second row
    Regex("......CCC"), //Third row
    Regex("C..C..C.."), //First column
    Regex(".C..C..C."), //Second column
    Regex("..C..C..C"), //Third column
    Regex("C...C...C"), //Start to end diagonal
    Regex("..C.C.C..")  //End to start diagonal
)

/**
 * To check if there is a winner play, the board array is converted into a string and compared with all the possible winner templates.
 * Besides, to be able to know the winner if necessary, and avoid duplicating all the patterns for both players characters, the turn character is replaced
 * for a common letter (`C`)
 * If this function returns null, then there is no winner yet
 */
internal fun Array<String>.checkForWinner(player: Player): Result? =
    if (winnerTemplates.firstOrNull { pattern ->
            pattern.matches(joinToString(separator = "").replace(player.character, "C")) } == null)
    {
        // If there is no pattern matching, but all the cells are checked, then game ends with a tie
        if (!contains("-"))
        {
            Result.Tie
        }
        else  { null }
    }
    //If the board matches any of the patterns, the current player wins
    else {
        Result.Win(player)
    }