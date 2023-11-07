package com.uoc.pr1.data.model

import android.view.View
import androidx.annotation.DrawableRes


enum class ItemType(val v1:Int) {
    BASIC(1),
    IMAGE(2),
    MAP(3),
    REQUEST(4)
}


data class Item(
    val type:ItemType,
    val id: Long,
    val name: String,
    @DrawableRes
    val image: Int?,
    val description: String,
    var view: View?
)