package com.bnp.tictactoe.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bnp.tictactoe.R
import com.bnp.tictactoe.data.Player
import com.bnp.tictactoe.extensions.Event
import com.bnp.tictactoe.extensions.checkForWinner

internal class TicTacToeViewModel : ViewModel() {
    companion object {
        //Values to fill the board array: empty when no player has clicked, X for the first player, O for the second one
        private const val emptyValue = "-"
        private const val xValue = "X"
        private const val oValue = "O"

        internal val player1 = Player("1", xValue, R.drawable.ic_android)
        internal val player2 = Player("2", oValue, R.drawable.ic_apple)
    }

    private val _gameState: MutableLiveData<GameState> = MutableLiveData(GameState.Initial)

    val gameState: LiveData<GameState>
        get() = _gameState

    private val _result: MutableLiveData<Event<Result>> = MutableLiveData()

    val result: LiveData<Event<Result>>
        get() = _result

    private val board = Array(9) { emptyValue }
    private var currentPlayer = player1
    private var busyCells = 0

    fun onCellClicked(index: Int) {
        board[index] = currentPlayer.character
        busyCells++
        _gameState.postValue(GameState.Playing(index, currentPlayer, board))
    }

    fun checkForWinner() {
        //There is no possible winner until at least 5 cells will be checked (three checks for the first player)
        if (busyCells > 4) {
            board.checkForWinner(currentPlayer)?.apply {
                _gameState.postValue(GameState.Finished(board))
                _result.postValue(Event(this))
                return
            }
        }
        changeTurn()
    }

    private fun changeTurn() {
        currentPlayer = if (currentPlayer == player1) {
            player2
        } else {
            player1
        }
    }

    fun onResetClicked() {
        //Return to initial values
        board.forEachIndexed { index, _ -> board[index] = "-" }
        currentPlayer = player1
        busyCells = 0
        _gameState.postValue(GameState.Initial)
    }
}
