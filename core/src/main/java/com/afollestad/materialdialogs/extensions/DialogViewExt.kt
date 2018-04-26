package com.afollestad.materialdialogs.extensions

import android.content.Context
import android.graphics.Point
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.R.dimen

@Suppress("UNCHECKED_CAST")
internal fun <T> MaterialDialog.inflate(
  context: Context, @LayoutRes res: Int,
  root: ViewGroup? = null
): T {
  return LayoutInflater.from(context).inflate(res, root, false) as T
}

internal fun MaterialDialog.setWindowConstraints() {
  val wm = this.window!!.windowManager
  val display = wm.defaultDisplay
  val size = Point()
  display.getSize(size)
  val windowWidth = size.x
  val windowHeight = size.y

  with(context.resources) {
    val windowVerticalPadding = getDimensionPixelSize(
        dimen.md_dialog_vertical_margin
    )
    val windowHorizontalPadding = getDimensionPixelSize(
        dimen.md_dialog_horizontal_margin
    )
    val maxWidth = getDimensionPixelSize(dimen.md_dialog_max_width)
    val calculatedWidth = windowWidth - windowHorizontalPadding * 2

    this@setWindowConstraints.view.maxHeight = windowHeight - windowVerticalPadding * 2
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(this@setWindowConstraints.window!!.attributes)
    lp.width = Math.min(maxWidth, calculatedWidth)
    this@setWindowConstraints.window!!.attributes = lp
  }
}

internal fun MaterialDialog.addContentScrollView() {
  if (this.contentScrollView != null) {
    return
  }
  this.contentScrollView = inflate(context, R.layout.md_dialog_stub_scrollview, this.mainFrame)
  this.contentScrollView!!.rootView = this.view
  this.contentScrollViewFrame = this.contentScrollView!![0]
  this.mainFrame.addView(this.contentScrollView)
}
