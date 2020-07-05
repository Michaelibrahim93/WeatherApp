package com.test.basemodule.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtils {
    fun showSoftKeyboard(editText: EditText) {
        val context = editText.context
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        editText.setSelection(editText.text.length)
        editText.requestFocus()
    }
}