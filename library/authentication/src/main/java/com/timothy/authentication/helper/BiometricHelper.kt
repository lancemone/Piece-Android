package com.timothy.authentication.helper

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object BiometricHelper {

    interface BiometricAuthenticationCallback {
        fun onFailed(errorCode: Int, errString: CharSequence)

        fun onSucceeded(type: Int)
    }

    /**
     * 生物识别是否可用
     */
    fun canBiometricAuthenticate(context: Context): Boolean {
        val biometricManager: BiometricManager = BiometricManager.from(context)

        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true    // 应用可以进行生物识别技术进行身份验证
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d("Piece", "该设备上没有搭载可用的生物特征功能")
                false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.d("Piece", "生物识别功能当前不可用")
                false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("Piece", "用户没有录入生物识别数据")
                false
            }

            else -> {
                false
            }
        }
    }

    fun canDeviceCredential(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val biometricManager: BiometricManager = BiometricManager.from(context)
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    true
                }

                else -> {
                    false
                }
            }
        } else {
            // 检查 Android 10 及更低版本中是否存在 PIN 码、解锁图案或密码
            val keyguardManager: KeyguardManager? =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?
            Log.d("Piece", "KeyguardManager isDeviceSecure=${keyguardManager?.isDeviceSecure}")
            keyguardManager?.isDeviceSecure == true
        }
    }

    fun biometricAuthenticate(fragment: Fragment, callback: BiometricAuthenticationCallback?) {
        runCatching {
            val executor = ContextCompat.getMainExecutor(fragment.requireContext())
            val biometricPrompt: BiometricPrompt = BiometricPrompt(
                /* fragment = */ fragment,
                /* executor = */ executor,
                /* callback = */ object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        callback?.onFailed(errorCode = errorCode, errString = errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        callback?.onSucceeded(result.authenticationType)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        callback?.onFailed(-1, "onAuthenticationFailed")
                    }
                }
            )

            // 不能在调用 BiometricPrompt.PromptInfo.Builder 实例的同时调用 setNegativeButtonText() 和 setAllowedAuthenticators(... or DEVICE_CREDENTIAL)
            //                 .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG)
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(true)
                .setTitle("user authentication")
                .build()
            biometricPrompt.authenticate(promptInfo)
        }
    }

    /**
     * 生物识别
     * Android 10（API 级别 29）及更低版本不支持以下身份验证器类型组合：DEVICE_CREDENTIAL 和 BIOMETRIC_STRONG | DEVICE_CREDENTIAL
     */
    fun biometricAuthenticate(
        activity: FragmentActivity,
        callback: BiometricAuthenticationCallback?
    ) {
        runCatching {
            val executor = ContextCompat.getMainExecutor(activity)
            val biometricPrompt: BiometricPrompt = BiometricPrompt(
                /* activity = */ activity,
                /* executor = */ executor,
                /* callback = */ object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        callback?.onFailed(errorCode = errorCode, errString = errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        callback?.onSucceeded(result.authenticationType)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        callback?.onFailed(-1, "onAuthenticationFailed")
                    }
                }
            )

            // 不能在调用 BiometricPrompt.PromptInfo.Builder 实例的同时调用 setNegativeButtonText() 和 setAllowedAuthenticators(... or DEVICE_CREDENTIAL)
            //                 .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG)
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(true)
                .setTitle("user authentication")
                .build()
            biometricPrompt.authenticate(promptInfo)
        }
    }
}