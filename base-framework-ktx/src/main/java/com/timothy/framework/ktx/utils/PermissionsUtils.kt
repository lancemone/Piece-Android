package com.timothy.framework.ktx.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionsUtils {

    private var permissions: Array<String>? = null
    private var mCallback: PermissionsRequestCallback? = null

    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private val context: Context

    private constructor(permissions:Array<String>, activity: ComponentActivity){
        this.permissions = permissions
        this.context = activity
        requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            onPermissionsResult(it)
        }
    }

    private constructor(permissions:Array<String>, fragment: Fragment){
        this.permissions = permissions
        this.context = fragment.requireContext()
        requestPermissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            onPermissionsResult(it)
        }
    }

    /**
     * 设置授权结果回调，
     */
    fun callback(callback: PermissionsRequestCallback?): PermissionsUtils{
        mCallback = callback
        return this
    }

    fun request(){
        if (permissions.isNullOrEmpty()){
            return
        }
        val denies = mutableListOf<String>()
        permissions?.forEach {
            if (!isPermissionGranted(it, context)){
                denies.add(it)
            }
        }
        if (denies.isNotEmpty()){
            requestPermissionLauncher.launch(denies.toTypedArray())
        }else{
            mCallback?.onAllGranted()
        }
    }

    private fun onPermissionsResult(grantedMap: Map<String, Boolean>){
        val granted = grantedMap.filterValues {
            it
        }.keys.toTypedArray()
        val denied = grantedMap.filterValues {
            !it
        }.keys.toTypedArray()
        mCallback?.let {cb ->
            if (denied.isEmpty()){
                cb.onAllGranted()
            }else{
                cb.onGranted(granted)
                cb.onDenied(null, denied)
            }
        }
    }

    companion object{
        val CAMERA = arrayOf(
            Manifest.permission.CAMERA
        )
        val CONTACTS = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS
        )
        val LOCATION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val MICROPHONE = arrayOf(
            Manifest.permission.RECORD_AUDIO
        )
        val PHONE = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ANSWER_PHONE_CALLS
        )
        val CALENDAR = arrayOf(
            Manifest.permission.READ_CALENDAR,  // 读取日历
            Manifest.permission.WRITE_CALENDAR  // 编辑日历
        )
        val SMS = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS
        )
        val STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        /** 判断此权限[permission]是否已被授权*/
        @SuppressLint("ObsoleteSdkInt")
        fun isPermissionGranted(permission: String, context: Context): Boolean{
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                return true
            }
            if (context.packageManager.isPermissionRevokedByPolicy(permission, context.packageName)){
                return true
            }
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }


    }

    interface PermissionsRequestCallback{
        fun onGranted(granted: Array<out String>)

        fun onAllGranted()

        fun onDenied(deniedForever: Array<out String>?, denied: Array<out String>?)
    }
}