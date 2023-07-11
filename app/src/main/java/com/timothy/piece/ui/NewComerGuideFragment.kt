package com.timothy.piece.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.timothy.nativelib.NativeLib
import com.timothy.piece.R
import com.timothy.piece.databinding.FragmentNewComerBinding
import okio.Utf8


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.piece.ui.NewComerGuideFragment
 * @Author: MoTao
 * @Date: 2023-05-19
 * <p>
 * <p/>
 */
class NewComerGuideFragment : Fragment(){

    private lateinit var binding:FragmentNewComerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Piece", "NewComerGuideFragment onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_comer, container, false)
        return binding.root
    }

    private var entStr = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.jiamiClick = View.OnClickListener {
            entStr = NativeLib.strEncrypt(String("Hello, World!".toByteArray(), Charsets.UTF_8))
            Log.d("Piece", "entStr=$entStr")
            binding.encText = entStr
        }

        binding.jiemiClick = View.OnClickListener {
            if (entStr.isNotBlank()){
                binding.encText = NativeLib.strDecrypt(entStr)
            }
        }
    }
}