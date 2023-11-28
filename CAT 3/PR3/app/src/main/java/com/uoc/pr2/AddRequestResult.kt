package com.uoc.pr2

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val PARAM_ADDREQUESTRESULT_CLASS = "com.uoc.pr1.parcel_addrequestresult"

@Parcelize
data class AddRequestResult(val title: String,val description: String,val uri: Uri?): Parcelable
