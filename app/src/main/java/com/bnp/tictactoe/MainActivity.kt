package com.bnp.tictactoe

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bnp.tictactoe.vm.GameState
import com.bnp.tictactoe.vm.Result
import com.bnp.tictactoe.vm.TicTacToeViewModel
import com.bnp.tictactoe.widget.CellView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel by lazy { ViewModelProviders.of(this)[TicTacToeViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildBoard()
        viewModel.gameState.observe(this, Observer(::updateUI))
    }

    // Calculate the total number of cells and create/add the views to the board
    private fun buildBoard() {
        val boardColumns = resources.getInteger(R.integer.board_columns)
        val boardRows = resources.getInteger(R.integer.board_rows)
        val boardCells = (boardColumns * boardRows) - 1
        for (i in 0..boardCells) {
            boardLayout.addView(CellView(this, i))
        }
        //In case the activity is recreated and the state would be `Playing` the cells need to be re-painted
        (viewModel.gameState.value as? GameState.Playing)?.board?.apply {
            drawBoard(this)
        }
    }

    private fun drawBoard(board: Array<String>) {
        boardLayout.children.forEachIndexed { index, view ->
            when (board[index]) {
                TicTacToeViewModel.player1.character -> (view as? CellView)?.setImageResource(TicTacToeViewModel.player1.imageRes)
                TicTacToeViewModel.player2.character -> (view as? CellView)?.setImageResource(TicTacToeViewModel.player2.imageRes)
            }
        }
    }

    private fun updateUI(gameState: GameState) {
        when (gameState) {
            is GameState.Initial -> cleanCells()
            is GameState.Playing -> updateCell(gameState.clickedIndex, gameState.currentPlayer.imageRes)
            is GameState.Finished -> finishGame(gameState.result)
        }
    }

    private fun cleanCells() {
        boardLayout.children.forEach { cell -> (cell as? CellView)?.clean() }
    }

    private fun updateCell(index: Int, @DrawableRes imageRes: Int) {
        (boardLayout.getChildAt(index) as? CellView)?.setImage(imageRes)
        viewModel.checkForWinner()
    }

    private fun finishGame(result: Result) {
        boardLayout.children.forEach { cell -> (cell as? CellView)?.isClickable = false }
        showDialog(
            when (result) {
                is Result.Tie -> getString(R.string.result_dialog_content_tie)
                is Result.Win -> getString(R.string.result_dialog_content_winner, result.winner.id)
            }
        )
    }

    private fun showDialog(content: String) {
        AlertDialog.Builder(this).apply {
            setMessage(content)
            setTitle(R.string.result_dialog_title)
            create()
        }.show()
    }

    override fun onClick(v: View?) {
        if (v is CellView) {
            viewModel.onCellClicked(v.index)
        }
    }
}
