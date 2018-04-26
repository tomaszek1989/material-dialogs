package com.afollestad.materialdialogs.extensions

import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.internal.getVisibleButtons

internal fun MaterialDialog.getString(@StringRes res: Int, @StringRes fallback: Int = 0): CharSequence? {
  return context.resources.getText(if (res == 0) fallback else res)
}

internal fun MaterialDialog.getDrawable(@DrawableRes res: Int, fallback: Drawable? = null): Drawable? {
  return ContextCompat.getDrawable(context, res) ?: fallback
}

internal fun MaterialDialog.getStringArray(@ArrayRes res: Int): Array<CharSequence> {
  return context.resources.getTextArray(res)
}

internal fun MaterialDialog.hasActionButtons(): Boolean {
  return view.getVisibleButtons()
      .isNotEmpty()
}

internal fun MaterialDialog.setIcon(
  @IdRes viewId: Int,
  @DrawableRes iconRes: Int,
  icon: Drawable?
) {
  val imageView = view.findViewById<ImageView>(viewId)
  val drawable = getDrawable(iconRes, icon)
  if (drawable != null) {
    (imageView.parent as View).visibility = View.VISIBLE
    imageView.visibility = View.VISIBLE
    imageView.setImageDrawable(drawable)
  } else {
    imageView.visibility = View.GONE
  }
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
    (textView.parent as View).visibility = View.VISIBLE
    textView.visibility = View.VISIBLE
    textView.text = value
  } else {
    textView.visibility = View.GONE
  }
  if (value != null) {
    textView.setOnClickListener {
      if (autoDismiss) {
        dismiss()
      }
      if (click != null) {
        click(this@setText)
      }
    }
  }
}
