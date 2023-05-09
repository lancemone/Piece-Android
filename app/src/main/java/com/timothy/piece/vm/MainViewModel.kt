package com.timothy.piece.vm

import androidx.lifecycle.viewModelScope
import com.timothy.common.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.piece.vm.MainViewModel
 * @Author: MoTao
 * @Date: 2023-05-09
 * <p>
 * <p/>
 */
class MainViewModel : BaseViewModel(){
    var isDataReady = AtomicBoolean(false)

    fun initData() {
        viewModelScope.launch {
            delay(2000)
            isDataReady.compareAndSet(false, true)
        }
    }
}