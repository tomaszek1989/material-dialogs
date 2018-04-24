package com.afollestad.materialdialogs.extensions

import android.support.annotation.DimenRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewTreeObserver
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.Button
import android.widget.ScrollView
import com.afollestad.materialdialogs.internal.MDScrollView

/**
 * @author Aidan Follestad (afollestad)
 */

@Suppress("UNCHECKED_CAST")
internal fun <R : View> ViewGroup.inflate(@LayoutRes res: Int): R {
  return LayoutInflater.from(context).inflate(res, this, false) as R
}

internal fun <T : View> T.updatePadding(
  left: Int = this.paddingLeft,
  top: Int = this.paddingTop,
  right: Int = this.paddingRight,
  bottom: Int = this.paddingBottom
) {
  this.setPadding(left, top, right, bottom)
}

//internal fun <T : View> T.updateMargin(
//  left: Int = -1,
//  top: Int = -1,
//  right: Int = -1,
//  bottom: Int = -1
//) {
//  val layoutParams = this.layoutParams as MarginLayoutParams
//  if (left != -1) {
//    layoutParams.leftMargin = left
//  }
//  if (top != -1) {
//    layoutParams.topMargin = top
//  }
//  if (right != -1) {
//    layoutParams.rightMargin = right
//  }
//  if (bottom != -1) {
//    layoutParams.bottomMargin = bottom
//  }
//  this.layoutParams = layoutParams
//}

internal inline fun <T : View> T.waitForLayout(crossinline f: () -> Unit) = with(viewTreeObserver) {
  addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
    override fun onGlobalLayout() {
      removeOnGlobalLayoutListener(this)
      f()
    }
  })
}

internal fun <T : View> T.dimenPx(@DimenRes res: Int): Int {
  return context.resources.getDimensionPixelSize(res)
}

internal fun <T : View> T.isVisible(): Boolean {
  return if (this is Button) {
    this.visibility == View.VISIBLE && this.text.trim().isNotBlank()
  } else {
    this.visibility == View.VISIBLE
  }
}

@Suppress("UNCHECKED_CAST")
internal operator fun <T : ViewGroup, R : View> T.get(index: Int): R? {
  return getChildAt(index) as? R
}