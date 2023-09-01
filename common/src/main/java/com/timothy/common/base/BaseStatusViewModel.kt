package com.timothy.common.base

import com.timothy.common.data.LoadingStatusData
import kotlinx.coroutines.flow.MutableStateFlow


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.common.base.BaseStatusViewModel
 * @Author: MoTao
 * @Date: 2023-08-29
 * <p>
 * <p/>
 */
abstract class BaseStatusViewModel: BaseViewModel() {
    val loadingStatusFlow: MutableStateFlow<LoadingStatusData> = MutableStateFlow(LoadingStatusData.LoadingStart())
}