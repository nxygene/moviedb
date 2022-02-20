package com.sberkozd.moviedb.binding

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import com.sberkozd.moviedb.util.Constants.baseImageUrl


object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        if (!text.isNullOrEmpty()) {
            Toast.makeText(view.context, text, Toast.LENGTH_LONG).show()
        }
    }


    @JvmStatic
    @BindingAdapter("imageUrl", "placeHolderDrawable", requireAll = false)
    fun loadPoster(view: ImageView, imageUrl: String?, @DrawableRes placeHolderDrawable: Int?) {
        view.load(baseImageUrl + imageUrl) {
            placeHolderDrawable?.let {
                placeholder(it)
                error(it)
                fallback(it)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}