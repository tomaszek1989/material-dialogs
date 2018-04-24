package com.afollestad.materialdialogs.internal

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.View
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme
import com.afollestad.materialdialogs.Theme.LIGHT
import com.afollestad.materialdialogs.extensions.dimenPx
import com.afollestad.materialdialogs.extensions.updatePadding

/**
 * Represents an action button in a dialog, positive, negative, or neutral. Handles switching
 * out its selector, padding, and text alignment based on whether buttons are in stacked mode or not.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class MDActionButton(
  context: Context,
  attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

  private val paddingDefault = dimenPx(R.dimen.md_action_button_padding_horizontal)
  private val paddingStacked = dimenPx(R.dimen.md_stacked_action_button_padding_horizontal)

  init {
    isClickable = true
    isFocusable = true
  }

  fun update(
    theme: Theme,
    stacked: Boolean
  ) {
    // Selector
    val backgroundRes: Int = if (stacked) {
      if (theme == LIGHT) R.drawable.md_item_selector else R.drawable.md_item_selector_dark
    } else {
      if (theme == LIGHT) R.drawable.md_btn_selector else R.drawable.md_btn_selector_dark
    }
    background = ContextCompat.getDrawable(context, backgroundRes)

    // Padding
    val sidePadding = if (stacked) paddingStacked else paddingDefault
    updatePadding(left = sidePadding, right = sidePadding)

    // Text alignment
    textAlignment = if (stacked) View.TEXT_ALIGNMENT_VIEW_END else View.TEXT_ALIGNMENT_CENTER
  }
}
