package com.timothy.framework.ktx.helper

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.timothy.framework.ktx.FWKtxApplication
import com.timothy.framework.ktx.ext.KTXExt.toHexString
import java.security.MessageDigest



object AppInfoHelper {

    fun isDebug(): Boolean{
        return (FWKtxApplication.application.applicationInfo.flags and  ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }

    fun getPackageName():String{
        return try {
            FWKtxApplication.application.packageName
        }catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }

    fun getMyPackageInfo(flags: Int = 0): PackageInfo?{
        return try {
            val pn = getPackageName()
            if (pn.isBlank()){
                null
            }else{
                FWKtxApplication.application.packageManager.getPackageInfo(pn, flags)
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("NewApi")
    fun getVersionName(): String{
        return try {
            FWKtxApplication.application
                .packageManager
                .getPackageInfo(getPackageName(), PackageManager.PackageInfoFlags.of(0)).versionName
        }catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }

    @SuppressLint("NewApi")
    fun getVersionCode(): Long{
        return try {
            FWKtxApplication.application
                .packageManager
                .getPackageInfo(getPackageName(), PackageManager.PackageInfoFlags.of(0)).longVersionCode
        }catch (e: Exception){
            e.printStackTrace()
            0L
        }
    }

    @SuppressLint("NewApi")
    fun getInstallTime(): Long{
        return try {
            FWKtxApplication.application
                .packageManager
                .getPackageInfo(getPackageName(), PackageManager.PackageInfoFlags.of(0)).firstInstallTime
        }catch (e: Exception){
            0
        }
    }

    private var signSHA1:String? = null
    /**
     * 获取应用签名 SHA1
     */
    fun getSHA1Signature():String?{
        if (signSHA1.isNullOrBlank()){
            val md = MessageDigest.getInstance("SHA1")
            val publicKey: ByteArray? = try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val pi = getMyPackageInfo(PackageManager.GET_SIGNING_CERTIFICATES)
                    if (pi != null){
                        val cert = pi.signingInfo.apkContentsSigners[0].toByteArray()
                        md.digest(cert)
                    }else{
                        null
                    }
                } else {
                    val pi = getMyPackageInfo(PackageManager.GET_SIGNATURES)
                    if (pi != null){
                        val cert = pi.signatures[0].toByteArray()
                        md.digest(cert)
                    }else{
                        null
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
            publicKey?.let {
                signSHA1 = it.joinToString("") { by ->
                    by.toHexString()
                }
            }
        }
        return signSHA1
    }
}