package com.bnp.tictactoe.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class TicTacToeViewModel : ViewModel() {

    private val _gameState: MutableLiveData<GameState> = MutableLiveData()

    val gameState: LiveData<GameState>
        get() = _gameState

}
