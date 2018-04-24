package com.afollestad.materialdialogs.extensions

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author Aidan Follestad (afollestad)
 */

internal fun Int.dp(resources: Resources): Float {
  return TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics
  )
}