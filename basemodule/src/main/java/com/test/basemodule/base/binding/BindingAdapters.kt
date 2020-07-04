package com.test.basemodule.base.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

/**
 * created by george
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showGone(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun showInvisible(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("errorText")
    fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
        view.isErrorEnabled = !errorMessage.isNullOrEmpty()
        view.error = errorMessage
    }

    @JvmStatic
    @BindingAdapter("isSelected")
    fun setIsSelected(view: View, isSelected: Boolean) {
        view.isSelected = isSelected
    }

    @JvmStatic
    @BindingAdapter("imageRes")
    fun setImageRes(view: ImageView, res: Int) {
        view.setImageResource(res)
    }
}

