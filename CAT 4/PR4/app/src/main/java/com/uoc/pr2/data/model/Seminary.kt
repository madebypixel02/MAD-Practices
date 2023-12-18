package com.uoc.pr2.data.model

import android.view.View
import androidx.annotation.DrawableRes

data class Seminary(val id: Int, val name: String, val duration:Int,
                    val image_path: String?,
                    @DrawableRes
                    val image: Int? = null,
                    var view: View? = null)