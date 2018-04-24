package com.afollestad.materialdialogs.extensions

import android.support.annotation.ColorInt
import android.graphics.drawable.GradientDrawable
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R.dimen

fun MaterialDialog.backgroundColor(@ColorInt color: Int) {
  val drawable = GradientDrawable()
  drawable.cornerRadius = context.resources
      .getDimension(dimen.md_dialog_bg_corner_radius)
  drawable.setColor(color)
  window.setBackgroundDrawable(drawable)
}