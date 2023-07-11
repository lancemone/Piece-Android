package com.timothy.piece.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.timothy.common.base.BaseFragment
import com.timothy.framework.ktx.helper.AppInfoHelper
import com.timothy.piece.R
import com.timothy.common.R as CR
import com.timothy.piece.databinding.FragmentHomeBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.piece.fragment.MainHomeFragment
 * @Author: MoTao
 * @Date: 2023-05-19
 * <p>
 * <p/>
 */
class MainUiFragment : BaseFragment(){

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onTextClick = View.OnClickListener {
            Log.d("Piece", "navigate link_new_comer")
//            findNavController().navigate(Uri.parse(getString(R.string.link_new_comer)))
//            it.findNavController().navigate(R.id.main_to_ll)
            findNavController().navigate(CR.id.action_global_fragmentLinearLayout)
//            findNavController().navigate(
//                route = resources.getString(CR.string.route_fragment_linear_layout)
//            ){
//                popUpTo(CR.id.actionToMain)
//            }
//            SnackBarHelper.show(it, "显示一下")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Piece", "is debug=" + AppInfoHelper.isDebug())
        Log.d("Piece", "MainActivity getSHA1Signature=" + AppInfoHelper.getSHA1Signature())
    }
}