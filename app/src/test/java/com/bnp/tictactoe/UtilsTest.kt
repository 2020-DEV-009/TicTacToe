package com.bnp.tictactoe

import com.bnp.tictactoe.data.Player
import com.bnp.tictactoe.extensions.checkForWinner
import com.bnp.tictactoe.vm.Result
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class UtilsTest {

    @Test
    fun check_x_wins() {
        //Given
        val board = arrayOf("X","X","X","O","-","O","-","-","-")
        val playerX = Player ("","X",0)

        //When
        val result = board.checkForWinner(playerX)

        //Then
        assertTrue(result is Result.Win)
        assertEquals((result as? Result.Win)?.winner, playerX)
    }

    @Test
    fun check_x_wins_with_last_move() {
        //Given
        val board = arrayOf("X","O","X","O","X","O","X","O","X")
        val playerX = Player ("","X",0)

        //When
        val result = board.checkForWinner(playerX)

        //Then
        assertTrue(result is Result.Win)
        assertEquals((result as? Result.Win)?.winner, playerX)
    }

    @Test
    fun check_o_wins() {
        //Given
        val board = arrayOf("O","O","X","O","X","X","O","X","X")
        val playerO = Player ("","O",0)

        //When
        val result = board.checkForWinner(playerO)

        //Then
        assertTrue(result is Result.Win)
        assertEquals((result as? Result.Win)?.winner, playerO)
    }

    @Test
    fun check_tie() {
        //Given
        val board = arrayOf("X","X","O","O","O","X","X","O","X")
        val playerX = Player ("","X",0)

        //When
        val result = board.checkForWinner(playerX)

        //Then
        assertTrue(result is Result.Tie)
    }

    @Test
    fun check_nobody_wins() {
        //Given
        val board = arrayOf("X","O","-","O","X","-","-","X","-")
        val playerX = Player ("","X",0)
        val playerO = Player ("","O",0)


        //When
        val resultX = board.checkForWinner(playerX)
        val resultO = board.checkForWinner(playerO)

        //Then
        assertEquals(resultX,null)
        assertEquals(resultO, null)
    }
}