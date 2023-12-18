package com.uoc.pr2.data.model

import android.view.View
import androidx.annotation.DrawableRes


enum class ItemType(val v1:Int) {
    BASIC(1),
    IMAGE(2),
    MAP(3),
    REQUEST(4)
}


data class Item(
    val type: ItemType,
    val id: Int,
    val name: String,
    val description: String,
    val image_path: String? = "",
    @DrawableRes
    val image: Int? = null,
    var view: View? = null,
    val title: String
)