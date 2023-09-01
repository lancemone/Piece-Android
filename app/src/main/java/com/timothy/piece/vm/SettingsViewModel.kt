package com.timothy.piece.vm

import com.timothy.common.base.BaseViewModel
import com.timothy.common.constants.DataStoreKey
import com.timothy.common.data.LoadingStatusData
import com.timothy.common.helper.DataStoreHelper
import kotlinx.coroutines.flow.MutableStateFlow


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.settings.vm.SettingsViewModel
 * @Author: MoTao
 * @Date: 2023-08-29
 * <p>
 * <p/>
 */
class SettingsViewModel : BaseViewModel(){

    fun changeScreenShotAllow(): Boolean{
        val willAllow = !isScreenShotAllow()
        DataStoreHelper.defaultData().putBoolean(DataStoreKey.SETTINGS_SCREEN_SHOT_ALLOW, willAllow)
        return willAllow
    }

    fun isScreenShotAllow(): Boolean{
        return DataStoreHelper.defaultData().getBoolean(DataStoreKey.SETTINGS_SCREEN_SHOT_ALLOW, false)
    }

    fun changeUnlockStatue(): Boolean{
        val willEnable = !getAppOpenLockStatue()
        DataStoreHelper.defaultData().putBoolean(DataStoreKey.SETTING_APP_LOCK_ENABLE, willEnable)
        return willEnable
    }

    fun getAppOpenLockStatue(): Boolean{
        return DataStoreHelper.defaultData().getBoolean(DataStoreKey.SETTING_APP_LOCK_ENABLE, false)
    }
}