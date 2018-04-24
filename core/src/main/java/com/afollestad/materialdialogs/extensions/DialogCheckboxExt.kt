@file:Suppress("unused")

package com.afollestad.materialdialogs.extensions

import android.support.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.assertOneSet

// TODO(Aidan)
fun MaterialDialog.checkBoxPrompt(
  @StringRes textRes: Int = 0,
  text: CharSequence? = null,
  onToggle: (() -> (Boolean))?
): MaterialDialog {
  assertOneSet(textRes, text)
//  data[KEY_CHECKBOX_PROMPT] = text ?: getString(textRes)
//  data[KEY_CHECKBOX_PROMPT_LISTENER] = onToggle
  return this
}