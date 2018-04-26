package com.afollestad.materialdialogs.extensions

import android.support.annotation.ArrayRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.KEY_AUTO_DISMISS
import com.afollestad.materialdialogs.MaterialDialog

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
  click: ((MaterialDialog) -> (Unit))? = null
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
        click(this@setText)
      }
    }
  }
}
