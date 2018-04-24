package com.afollestad.materialdialogs.extensions

import android.support.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R.layout
import com.afollestad.materialdialogs.assertOneSet

private fun MaterialDialog.addContentMessageView(@StringRes res: Int, text: CharSequence?) {
  if (this.textViewMessage == null) {
    this.textViewMessage = inflate(
        context, layout.md_dialog_stub_message, this.contentScrollViewFrame!!
    )
    this.contentScrollViewFrame!!.addView(this.textViewMessage)
  }
  assertOneSet(res, text)
  this.textViewMessage!!.text = text ?: getString(res)
}

fun MaterialDialog.message(@StringRes textRes: Int = 0, text: CharSequence? = null): MaterialDialog {
  addContentScrollView()
  addContentMessageView(textRes, text)
  return this
}