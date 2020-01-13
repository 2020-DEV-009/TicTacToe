package com.bnp.tictactoe.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class TicTacToeViewModel : ViewModel() {
    companion object {
        //Values to fill the board array: empty when no player has clicked, X for the first player, O for the second one
        private const val emptyValue = "-"
        private const val xValue = "X"
        private const val oValue = "O"
    }

    private val _gameState: MutableLiveData<GameState> = MutableLiveData()

    val gameState: LiveData<GameState>
        get() = _gameState

    private val board = Array(9) { emptyValue }

    fun onCellClicked(index: Int) {

    }
}
