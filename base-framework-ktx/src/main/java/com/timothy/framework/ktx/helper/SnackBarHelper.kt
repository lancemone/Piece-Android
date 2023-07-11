package com.timothy.framework.ktx.helper

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Toast会显示在输入法键盘上方，Snackbar不会显示在输入法键盘上方（会被输入法键盘遮挡住）
 */
object SnackBarHelper {

    fun show(view: View, text:String){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    fun showWithAction(view: View, text:String, actionText:String, action:() -> Unit){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            .setAction(actionText){
                action.invoke()
            }
            .show()
    }
}