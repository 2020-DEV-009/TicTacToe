package com.bnp.tictactoe.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class Player(
    val id: String,
    val character: String,
    @DrawableRes val imageRes: Int
) : Parcelable