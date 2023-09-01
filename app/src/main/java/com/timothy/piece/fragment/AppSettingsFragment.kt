package com.timothy.piece.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.timothy.authentication.helper.BiometricHelper
import com.timothy.common.base.BaseFragment
import com.timothy.piece.databinding.FragmentSettingsBinding
import com.timothy.piece.vm.SettingsViewModel


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.piece.fragment.AppSettingsFragment
 * @Author: MoTao
 * @Date: 2023-07-12
 * <p>
 * <p/>
 */
class AppSettingsFragment: BaseFragment() {

    private val binding: FragmentSettingsBinding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }

    private val viewModel:SettingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View {
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitchScreenshot()
        initSwitchFingerprintUnlock()
    }

    private fun initSwitchScreenshot(){
        binding.switchScreenShotAllow.isChecked = viewModel.isScreenShotAllow()
        binding.switchScreenShotAllow.setOnClickListener {
            binding.switchScreenShotAllow.isChecked = viewModel.changeScreenShotAllow()
        }
    }

    private fun initSwitchFingerprintUnlock(){
        if (BiometricHelper.canBiometricAuthenticate(requireContext())){
            binding.switchFingerprintUnlock.visibility = View.VISIBLE
            binding.switchFingerprintUnlock.isChecked = viewModel.getAppOpenLockStatue()
            binding.switchFingerprintUnlock.setOnClickListener {
                binding.switchFingerprintUnlock.isChecked = viewModel.changeUnlockStatue()
            }
        }else{
            binding.switchFingerprintUnlock.visibility = View.GONE
        }
    }
}