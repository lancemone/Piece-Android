package com.timothy.feature.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.timothy.feature.ui.R
import com.timothy.feature.ui.databinding.LayoutItemUiHomeChildBinding
import com.timothy.feature.ui.databinding.LayoutItemUiHomeGroupBinding
import com.timothy.feature.ui.models.HomeUIItemModel
import okhttp3.internal.http.HttpMethod
import java.lang.Exception


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.adapter.HomeUIExpandableListAdapter
 * @Author: MoTao
 * @Date: 2023-10-08
 * <p>
 * <p/>
 */
class HomeUIExpandableListAdapter(context: Context, items: MutableList<HomeUIItemModel>?) :
    BaseExpandableListAdapter() {

        companion object{
            fun createAndBindAdapter(listView: ExpandableListView, items: MutableList<HomeUIItemModel>?): HomeUIExpandableListAdapter{
                val adapter = HomeUIExpandableListAdapter(listView.context, items)
                listView.setAdapter(adapter)
                listView.setOnChildClickListener { _, v, _, _, _ ->
                    val router = v?.getTag(R.id.id_ui_home_child_tag)
                    Log.d("Piece", "listView OnChildClickListener $router")
                    if (router != null) {
                        v.findNavController().navigate(router.toString())
                    }
                    true
                }
                return adapter
            }
        }

    private var mData: MutableList<HomeUIItemModel> = mutableListOf()
    private var mContext: Context

    init {
        mContext = context
        if (!items.isNullOrEmpty()){
            mData = items
        }
    }

    override fun getGroupCount(): Int {
        return mData.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return mData[groupPosition].childItem?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return mData[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any? {
        return mData[groupPosition].childItem?.get(childPosition)
    }

    override fun getGroupId(p0: Int): Long {
        return mData[p0].id.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return mData[groupPosition].childItem?.get(childPosition)?.id?.toLong() ?: -1
    }

    /**
     * @return 确定id 是否总是指向同一个对象
     */
    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val groupView = convertView
            ?: LayoutItemUiHomeGroupBinding.inflate(LayoutInflater.from(mContext), parent, false).root
        val tvTitle: AppCompatTextView? = groupView.findViewById(R.id.tv_title)
        tvTitle?.text = mData[groupPosition].title
        val ivIndicator: AppCompatImageView? = groupView.findViewById(R.id.iv_indicator)
        // 如果展开则顺时针旋转90°选择，否则不旋转
        ivIndicator?.rotation = if (isExpanded) 90F else 0F
        return groupView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val childView = convertView ?: LayoutItemUiHomeChildBinding.inflate(LayoutInflater.from(mContext), parent, false).root
        val tvTitle: AppCompatTextView? = childView.findViewById(R.id.tv_title)
        tvTitle?.text = mData[groupPosition].childItem?.get(childPosition)?.title
        val bottomDivider: View? = childView.findViewById(R.id.bottom_divider)
        bottomDivider?.isVisible = !isLastChild
        childView.setTag(R.id.id_ui_home_child_tag, mData[groupPosition].childItem?.get(childPosition)?.router)
        return childView
    }

    /**
     * 指定位置的子元素是否可选
     *
     * @param groupPosition 子元素组所在的位置
     * @param childPosition 子元素的位置
     * @return 返回是否可选
     */
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}