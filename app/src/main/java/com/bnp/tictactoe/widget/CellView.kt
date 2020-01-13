package com.bnp.tictactoe.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.bnp.tictactoe.MainActivity
import com.bnp.tictactoe.R

/**
 * Custom view to draw each of the cells inside the Board
 * Each view has an index to identify the position of the view within the parent
 */
internal class CellView @JvmOverloads constructor(
    context: Context,
    val index: Int,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    init {
        //Each cell has fixed size and a border background
        val size = resources.getDimensionPixelSize(R.dimen.cellSize)
        contentDescription = "Cell$index"
        layoutParams = LinearLayout.LayoutParams(size, size)
        background = ContextCompat.getDrawable(context, R.drawable.cell_background)
        setOnClickListener(context as? MainActivity)
    }

}