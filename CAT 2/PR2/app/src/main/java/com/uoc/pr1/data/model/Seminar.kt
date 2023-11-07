package com.uoc.pr1.data.model

import android.view.View
import androidx.annotation.DrawableRes

data class Seminar(val id: Int, val name: String, val duration: Int,
                   @DrawableRes
                      val image: Int?,
                   var view: View?)
