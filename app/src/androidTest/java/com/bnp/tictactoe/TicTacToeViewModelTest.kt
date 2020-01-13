package com.bnp.tictactoe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bnp.tictactoe.vm.GameState
import com.bnp.tictactoe.vm.Result
import com.bnp.tictactoe.vm.TicTacToeViewModel
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class TicTacToeViewModelTest {

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
    private val expectedXValue = "X"
    private val expectedOValue = "O"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val gameStateObserver: Observer<GameState> = mock()
    private val viewModel = TicTacToeViewModel()

    @Before
    fun setUpTaskDetailViewModel() {
        viewModel.gameState.observeForever(gameStateObserver)
    }

    @Test
    fun firstTurnIsPlayerX() {
        val index = 0

        //When
        viewModel.onCellClicked(index)

        //Then
        verifyWithCaptor(2) { state ->
            assertTrue(state is GameState.Playing)
            assertTrue((state as GameState.Playing).board[index] == expectedXValue)
        }
    }

    @Test
    fun secondTurnIsPlayerO() {
        val indexX = 0
        val indexO = 1

        //When
        clickCellAndChangeTurn(indexX)
        viewModel.onCellClicked(indexO)

        //Then
        verifyWithCaptor(3) { state ->
            assertTrue(state is GameState.Playing)
            assertTrue((state as GameState.Playing).board[indexO] == expectedOValue)
        }
    }

    @Test
    fun checkXWins() {
        //When
        runWinnerXGame()

        //Then
        verifyWithCaptor(7) { state ->
            assertTrue(state is GameState.Finished)
            val result = (state as GameState.Finished).result
            assertTrue(result is Result.Win)
            assertTrue((result as Result.Win).winner.character == expectedXValue)
        }
    }

    @Test
    fun checkOWins() {
        //When
        runWinnerOGame()

        //Then
        verifyWithCaptor(8) { state ->
            assertTrue(state is GameState.Finished)
            val result = (state as GameState.Finished).result
            assertTrue(result is Result.Win)
            assertTrue((result as Result.Win).winner.character == expectedOValue)
        }
    }

    @Test
    fun checkTie() {
        //When
        runTieGame()

        //Then
        verifyWithCaptor(11) { state ->
            assertTrue(state is GameState.Finished)
            assertTrue((state as GameState.Finished).result is Result.Tie)
        }
    }

    @Test
    fun resetToInitialState() {
        //When
        clickCellAndChangeTurn(0)
        clickCellAndChangeTurn(1)
        viewModel.onResetClicked()

        //Then
        verifyWithCaptor(4) { state ->
            assertTrue(state is GameState.Initial)
        }
    }

    private fun clickCellAndChangeTurn(index: Int) {
        viewModel.onCellClicked(index)
        viewModel.checkForWinner()
    }

    private fun verifyWithCaptor(expectedTimes: Int, block: (value: GameState) -> Unit) {
        val captor = ArgumentCaptor.forClass(GameState::class.java)
        captor.run {
            verify(gameStateObserver, times(expectedTimes)).onChanged(capture())
            block(value)
        }
    }

    private fun runWinnerXGame() {
        //Sequence: [ X, X, X, O, O, -, -, -, -]
        for (i in 0..2) {
            clickCellAndChangeTurn(i)
            if (i != 2) {
                clickCellAndChangeTurn(i + 3)
            }
        }
    }

    private fun runWinnerOGame() {
        //Sequence: [O, O, O, -, X, X, X, -, -]
        for (i in 0..2) {
            clickCellAndChangeTurn(i + 4)
            clickCellAndChangeTurn(i)
        }
    }

    private fun runTieGame() {
        //Sequence: [X, O, X, O, X, X, O, X, O]
        for (i in 0..8) {
            if (i != 4) {
                clickCellAndChangeTurn(i)
            }
        }
        clickCellAndChangeTurn(4)
    }
}