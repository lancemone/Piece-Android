package com.timothy.common.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    var userId:String = "",
    var userName:String? = "",
): IData, Parcelable
