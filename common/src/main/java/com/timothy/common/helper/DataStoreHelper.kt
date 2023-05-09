package com.timothy.common.helper

import com.timothy.common.BaseApp
import com.timothy.framework.ktx.base.DataStoreBasePreferences
import com.timothy.framework.ktx.factory.DataStoreFactory

object DataStoreHelper {

    fun defaultData():DataStoreBasePreferences = DataStoreFactory.getDefaultPreferences()

    fun userInfoData(): DataStoreBasePreferences = DataStoreFactory.getPreferencesDataStore("user_info_preferences")
}