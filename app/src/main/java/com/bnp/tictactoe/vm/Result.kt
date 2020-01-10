package com.bnp.tictactoe.vm

import com.bnp.tictactoe.data.Player

internal sealed class Result {

    object Tie: Result()

    class Win (val winner: Player): Result()
}