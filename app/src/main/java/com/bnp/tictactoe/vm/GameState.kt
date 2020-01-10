package com.bnp.tictactoe.vm

internal sealed class GameState {

    object Initial: GameState()

    class Playing (val board: Array<String>): GameState()

    class Finished (val result: Result): GameState()

}