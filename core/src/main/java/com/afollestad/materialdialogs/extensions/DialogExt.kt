package com.afollestad.materialdialogs.extensions

import android.content.Context
import android.graphics.Point
import android.support.annotation.ArrayRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import com.afollestad.materialdialogs.KEY_AUTO_DISMISS
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.R.dimen
import com.afollestad.materialdialogs.R.id
import com.afollestad.materialdialogs.internal.MDScrollView

@Suppress("UNCHECKED_CAST")
internal fun <T> MaterialDialog.inflate(
  context: Context, @LayoutRes res: Int,
  root: ViewGroup? = null
): T {
  return LayoutInflater.from(context).inflate(res, root, false) as T
}

internal fun MaterialDialog.getString(@StringRes res: Int, @StringRes fallback: Int = 0): CharSequence? {
  return context.resources.getText(if (res == 0) fallback else res)
}

internal fun MaterialDialog.getStringArray(@ArrayRes res: Int): Array<CharSequence> {
  return context.resources.getTextArray(res)
}

internal fun MaterialDialog.setText(
  @IdRes viewId: Int,
  @StringRes textRes: Int = 0,
  text: CharSequence? = null,
  @StringRes fallback: Int = 0,
  click: (() -> (Unit))? = null
) {
  val textView = view.findViewById<TextView>(viewId)
  val value = text ?: getString(textRes, fallback)
  if (value != null) {
    textView.text = value
    textView.visibility = View.VISIBLE
  } else {
    textView.visibility = View.GONE
  }
  if (value != null) {
    textView.setOnClickListener {
      if (data[KEY_AUTO_DISMISS] as Boolean) {
        dismiss()
      }
      if (click != null) {
        click()
      }
    }
  }
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
  this.contentScrollViewFrame = this.contentScrollView!![0]
  this.mainFrame.addView(this.contentScrollView)
}
