package com.timothy.common.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timothy.common.data.UserInfo

object UserInfoHelper {

    val userInfoLiveData: LiveData<UserInfo> = MutableLiveData<UserInfo>()
}