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
        val captor = ArgumentCaptor.forClass(GameState::class.java)
        captor.run {
            verify(gameStateObserver, times(2)).onChanged(capture())
            assertTrue(value is GameState.Playing)
            assertTrue(
                (value as GameState.Playing).board[index] == expectedXValue
            )
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
        val captor = ArgumentCaptor.forClass(GameState::class.java)
        captor.run {
            verify(gameStateObserver, times(3)).onChanged(capture())
            assertTrue(value is GameState.Playing)
            assertTrue((value as GameState.Playing).board[indexO] == expectedOValue)
        }
    }

    @Test
    fun checkXWins() {
        //When
        runWinnerXGame()

        //Then
        val captor = ArgumentCaptor.forClass(GameState::class.java)
        captor.run {
            verify(gameStateObserver, times(7)).onChanged(capture())
            assertTrue(value is GameState.Finished)
            val result = (value as GameState.Finished).result
            assertTrue(result is Result.Win)
            assertTrue((result as Result.Win).winner.character == expectedXValue)
        }
    }

    @Test
    fun checkOWins() {
        //When
        runWinnerOGame()

        //Then
        val captor = ArgumentCaptor.forClass(GameState::class.java)
        captor.run {
            verify(gameStateObserver, times(8)).onChanged(capture())
            assertTrue(value is GameState.Finished)
            val result = (value as GameState.Finished).result
            assertTrue(result is Result.Win)
            assertTrue((result as Result.Win).winner.character == expectedOValue)
        }
    }

    @Test
    fun checkTie() {
        //When
        runTieGame()

        //Then
        val captor = ArgumentCaptor.forClass(GameState::class.java)
        captor.run {
            verify(gameStateObserver, times(11)).onChanged(capture())
            assertTrue(value is GameState.Finished)
            assertTrue((value as GameState.Finished).result is Result.Tie)
        }
    }

    @Test
    fun resetToInitialState() {
        //When
        clickCellAndChangeTurn(0)
        clickCellAndChangeTurn(1)

        //Then
        val captor = ArgumentCaptor.forClass(GameState::class.java)
        captor.run {
            verify(gameStateObserver, times(4)).onChanged(capture())
            assertTrue(value is GameState.Initial)
        }
    }

    private fun clickCellAndChangeTurn(index: Int) {
        viewModel.onCellClicked(index)
        viewModel.checkForWinner()
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