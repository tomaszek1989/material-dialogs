package com.afollestad.materialdialogs.extensions

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.assertOneSet

fun MaterialDialog.icon(@DrawableRes iconRes: Int = 0, icon: Drawable? = null): MaterialDialog {
  assertOneSet(iconRes, icon)
  setIcon(R.id.md_icon_title, iconRes, icon)
  return this
}