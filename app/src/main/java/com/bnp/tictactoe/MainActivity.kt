package com.bnp.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bnp.tictactoe.vm.TicTacToeViewModel
import com.bnp.tictactoe.widget.CellView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel by lazy { ViewModelProviders.of(this)[TicTacToeViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildBoard()
    }

    // Calculate the total number of cells and create/add the views to the board
    private fun buildBoard() {
        val boardColumns = resources.getInteger(R.integer.board_columns)
        val boardRows = resources.getInteger(R.integer.board_rows)
        val boardCells = (boardColumns * boardRows)-1
        for (i in 0..boardCells) {
            boardLayout.addView(CellView(this, i))
        }
    }

    override fun onClick(v: View?) {
        Toast.makeText(this, "Index ${(v as? CellView)?.index}", Toast.LENGTH_SHORT).show()
    }
}
