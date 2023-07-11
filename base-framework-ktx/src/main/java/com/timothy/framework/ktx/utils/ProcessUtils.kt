package com.timothy.framework.ktx.utils

import android.app.ActivityManager
import android.app.ApplicationExitInfo
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.annotation.RequiresApi
import com.timothy.framework.ktx.factory.DataStoreFactory

object ProcessUtils {

    /**
     *
     * 获取应用上次存活时间
     *
     * 1。在android 11(包活)之后可以使用
     * 2。App.attachBaseContext中调用 && 主进程中调用 && 在super.attachBaseContext(base)之前
     * 3. 注意：如果mainProcessName 使用context.packageName 要保证进程名称和包名一致。
     *
     * @return
     * Triple<Long,Int,String>
     *     long 上次存活时长，单位毫秒
     *     int 被杀原因
     *     String 被杀描述
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun getLastLivingTime(
        application: Context?,
        mainProcessName: String?
    ):Triple<Long, Int, ApplicationExitInfo?>?{
        if (application == null || mainProcessName.isNullOrEmpty()) {
            return null
        }

        var lastLivingTime = -1L
        var reason = -1 //被杀原因
        var info: ApplicationExitInfo? = null
        runCatching {
            val am = application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            val applicationExitInfoList: List<ApplicationExitInfo> =
                am!!.getHistoricalProcessExitReasons(application.packageName, 0, 10)
            val store = DataStoreFactory.getPreferencesDataStore("app_base_info", application)
            val lastStartTime = store.getLong("app_last_start_time", -1L)
            val lastPid = store.getInt("app_last_pid", -1)
            store.putLong("app_last_start_time", System.currentTimeMillis())
            store.putInt("app_last_pid", Process.myPid())
            if (applicationExitInfoList.isNotEmpty()){
                var lastDeathTime = -1L //和上次进程启动最接近的死亡时间
                //查找和上次最接近的一次启动
                applicationExitInfoList.forEachIndexed { index, item ->
                    if (item.pid == lastPid) {
                        lastDeathTime = item.timestamp
                        reason = item.reason
                        info = item
                        return@forEachIndexed
                    }
                }
                if (lastDeathTime != -1L){
                    lastLivingTime = lastDeathTime - lastStartTime
                }
            }
        }
        if (lastLivingTime <= 0) {
            return null
        }
        return Triple(lastLivingTime, reason, info)
    }
}