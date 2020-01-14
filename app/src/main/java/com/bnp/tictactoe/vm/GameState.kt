package com.bnp.tictactoe.vm

import com.bnp.tictactoe.data.Player

internal sealed class GameState {

    object Initial: GameState()

    class Playing (val clickedIndex: Int, val currentPlayer: Player, val board: Array<String>): GameState()

    class Finished (val board: Array<String>): GameState()

}