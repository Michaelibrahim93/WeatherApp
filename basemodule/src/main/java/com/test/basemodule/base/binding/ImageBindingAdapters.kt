package com.test.basemodule.base.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.test.basemodule.utils.LogUtils
import java.io.File

/**
 * created by george
 * Binding adapters that work with a fragment instance.
 */
object ImageBindingAdapters {
    private const val TAG = "ImageBindingAdapters"
    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "placeholderImage", "errorImage", "isCircular"],
        requireAll = false
    )
    fun loadImageWithGlide(
        imageView: ImageView,
        obj: Any?,
        placeholder: Any?,
        errorImage: Any?,
        isCircular: Boolean = false
    ) {
        val options = RequestOptions()
        if (placeholder is Drawable) options.placeholder(placeholder)
        if (placeholder is Int) options.placeholder(placeholder)

        if (errorImage is Drawable) options.error(errorImage)
        if (errorImage is Int) options.error(errorImage)

        val manager = Glide.with(imageView).applyDefaultRequestOptions(options)
        val builder: RequestBuilder<Drawable>


        builder = when (obj) {
            is String -> manager.load(obj)
            is Uri -> manager.load(obj)
            is Drawable -> manager.load(obj)
            is Bitmap -> manager.load(obj)
            is Int -> manager.load(obj)
            is File -> manager.load(obj)
            is Array<*> -> manager.load(obj)
            else -> manager.load(obj)
        }

        val listener = object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                LogUtils.w(TAG, "failed loading $model")
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {

                LogUtils.d(TAG, "onResourceReady loading $model")
                return false
            }

        }

        if (isCircular ) {
            builder.listener(listener).apply(RequestOptions().circleCrop()).into(imageView)
        }else {
            builder.listener(listener).into(imageView)
        }
    }
}

