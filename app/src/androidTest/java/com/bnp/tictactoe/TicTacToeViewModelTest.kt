package com.bnp.tictactoe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bnp.tictactoe.extensions.Event
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
    private val resultObserver: Observer<Event<Result>> = mock()
    private val viewModel = TicTacToeViewModel()

    @Before
    fun setUpTaskDetailViewModel() {
        viewModel.gameState.observeForever(gameStateObserver)
        viewModel.result.observeForever(resultObserver)
    }

    @Test
    fun firstTurnIsPlayerX() {
        val index = 0

        //When
        viewModel.onCellClicked(index)

        //Then
        verifyWithCaptor(gameStateObserver, 2) { state ->
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
        verifyWithCaptor(gameStateObserver, 3) { state ->
            assertTrue(state is GameState.Playing)
            assertTrue((state as GameState.Playing).board[indexO] == expectedOValue)
        }
    }

    @Test
    fun checkXWins() {
        //When
        runWinnerXGame()

        //Then
        verifyWithCaptor(gameStateObserver, 7) { state ->
            assertTrue(state is GameState.Finished)
        }
        verifyWithCaptor(resultObserver, 1) { value ->
            val result = value.getContentIfNotHandled()
            assertTrue(result is Result.Win)
            assertTrue((result as Result.Win).winner.character == expectedXValue)
        }
    }

    @Test
    fun checkOWins() {
        //When
        runWinnerOGame()

        //Then
        verifyWithCaptor(gameStateObserver, 8) { state ->
            assertTrue(state is GameState.Finished)
        }
        verifyWithCaptor(resultObserver, 1) { value ->
            val result = value.getContentIfNotHandled()
            assertTrue(result is Result.Win)
            assertTrue((result as Result.Win).winner.character == expectedOValue)
        }
    }

    @Test
    fun checkTie() {

        //When
        runTieGame()

        //Then
        verifyWithCaptor(gameStateObserver, 11) { state ->
            assertTrue(state is GameState.Finished)
        }
        verifyWithCaptor(resultObserver, 1) { result ->
            assertTrue(result.getContentIfNotHandled() is Result.Tie)
        }
    }

    @Test
    fun resetToInitialState() {
        //When
        clickCellAndChangeTurn(0)
        clickCellAndChangeTurn(1)
        viewModel.onResetClicked()

        //Then
        verifyWithCaptor(gameStateObserver,4) { state ->
            assertTrue(state is GameState.Initial)
        }
    }

    private fun clickCellAndChangeTurn(index: Int) {
        viewModel.onCellClicked(index)
        viewModel.checkForWinner()
    }

    private inline fun <reified T : Any> verifyWithCaptor(
        observer: Observer<T>,
        expectedTimes: Int,
        block: (value: T) -> Unit
    ) {
        val captor = ArgumentCaptor.forClass(T::class.java)
        captor.run {
            verify(observer, times(expectedTimes)).onChanged(capture())
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