package com.timothy.feature.ui.models

import com.timothy.framework.ktx.data.IKeep

data class HomeUIItemModel(
    val id:Int = 0,
    val title:String? = null,
    val router:String? = null,
    val childItem: MutableList<HomeUIItemModel>? = null,
): IKeep
