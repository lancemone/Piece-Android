package com.timothy.feature.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.R
import com.timothy.feature.ui.adapter.HomeUIExpandableListAdapter
import com.timothy.feature.ui.databinding.FragmentHomeUiBinding
import com.timothy.feature.ui.models.HomeUIItemModel


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.fragment.HomeUiFragment
 * @Author: MoTao
 * @Date: 2023-07-12
 * <p>
 * <p/>
 */
class HomeUiFragment : BaseFragment() {

    private val binding: FragmentHomeUiBinding by lazy {
        FragmentHomeUiBinding.inflate(layoutInflater)
    }

    private var listAdapter: HomeUIExpandableListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
    }

    private fun bindAdapter(){
        listAdapter = HomeUIExpandableListAdapter.createAndBindAdapter(binding.expListView, createItemList())
    }

    private fun createItemList(): MutableList<HomeUIItemModel> {
        return mutableListOf<HomeUIItemModel>().apply {
            add(
                HomeUIItemModel(
                    id = 1,
                    title = getString(com.timothy.common.R.string.layout),
                    childItem = mutableListOf(
                        HomeUIItemModel(
                            id = 101,
                            title = getString(com.timothy.common.R.string.linear_layout),
                            router = getString(com.timothy.common.R.string.route_fragment_linear_layout)
                        ),
                        HomeUIItemModel(
                            id = 102,
                            title = getString(com.timothy.common.R.string.relative_layout),
                            router = getString(com.timothy.common.R.string.route_fragment_linear_layout)
                        )
                    )
                )
            )

            add(
                HomeUIItemModel(
                    id = 2,
                    title = getString(com.timothy.common.R.string.text_view),
                    childItem = mutableListOf(
                        HomeUIItemModel(
                            id = 201,
                            title = getString(com.timothy.common.R.string.text_view),
                            router = getString(com.timothy.common.R.string.route_fragment_text_view)
                        )
                    )
                )
            )

            add(
                HomeUIItemModel(
                    id = 3,
                    title = getString(com.timothy.common.R.string.image_view),
                    childItem = mutableListOf(
                        HomeUIItemModel(
                            id = 301,
                            title = getString(com.timothy.common.R.string.image_view),
                            router = getString(com.timothy.common.R.string.route_fragment_image_view)
                        ),
                        HomeUIItemModel(
                            id = 302,
                            title = getString(com.timothy.common.R.string.shape_able_image_view),
                            router = getString(com.timothy.common.R.string.route_fragment_shapeable_image_view)
                        )
                    )
                )
            )
        }
    }
}