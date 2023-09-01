package com.timothy.authentication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.timothy.authentication.databinding.FragmentAuthenticationBinding
import com.timothy.authentication.helper.BiometricHelper
import com.timothy.common.base.BaseFragment
import com.timothy.common.R as CR


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.authentication.fragment.AuthenticationFragment
 * @Author: MoTao
 * @Date: 2023-07-17
 * <p>
 * <p/>
 */
class AuthenticationFragment: BaseFragment() {

    private val binding:FragmentAuthenticationBinding by lazy {
        FragmentAuthenticationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            BiometricHelper.canDeviceCredential(it)
            if (BiometricHelper.canBiometricAuthenticate(it)){
                BiometricHelper.biometricAuthenticate(this,
                    callback = object : BiometricHelper.BiometricAuthenticationCallback {
                        override fun onFailed(errorCode: Int, errString: CharSequence) {
                            Log.d(
                                "Piece",
                                "BiometricAuthenticationCallback code=$errorCode; err=$errString"
                            )
                            showFail(errString = "$errString")
                        }

                        override fun onSucceeded(type: Int) {
                            Log.d("Piece", "BiometricAuthenticationCallback onSucceeded type=$type")
                            findNavController().popBackStack()
                        }
                    })
            }else{
                checkBiometricAuthenticateFail()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, backPressedCallback
        )
        return binding.root
    }

    private val backPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showFail(errString:String){
        binding.rlFail.visibility = View.VISIBLE
        binding.failTip.text = errString
    }

    private fun checkBiometricAuthenticateFail(){
        backPressedCallback.isEnabled = false
        findNavController().navigate(CR.id.action_global_to_main_popup)
    }
}