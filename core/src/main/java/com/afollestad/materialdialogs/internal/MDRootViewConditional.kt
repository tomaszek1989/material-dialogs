package com.afollestad.materialdialogs.internal

import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme.LIGHT
import com.afollestad.materialdialogs.extensions.isVisible

/** Gets the divider color, based on if the dialog is using the light or dark theme. */
@ColorInt
internal fun MDRootView.getDividerColor(): Int {
  val colorRes = if (theme == LIGHT) R.color.md_divider_black else R.color.md_divider_white
  return ContextCompat.getColor(context, colorRes)
}

/** Gets an array of the visible action buttons (action buttons with text). */
internal fun MDRootView.getVisibleButtons(): Array<MDActionButton> {
  return actionButtons.filter { it.isVisible() }
      .toTypedArray()
}

/** Buttons plus any spacing around that makes up the "frame" */
internal fun MDRootView.getTotalButtonFrameHeight(): Int {
  val visibleButtons = getVisibleButtons()
  return when {
    visibleButtons.isEmpty() -> 0
    stackButtons -> (visibleButtons.size * buttonHeightStacked) + buttonFramePadding
    else -> buttonHeightDefault + (buttonFramePadding * 2)
  }
}

/** If [shouldReduceMainFrameTopPadding] returns true, 12dp else 24dp. */
internal fun MDRootView.getMainFrameTopPadding(): Int {
  return if (shouldReduceMainFrameTopPadding()) dialogFrameMarginHalf else dialogFrameMargin
}

/**
 * Returns true if we want to reduce the padding of the top of the dialog from 24dp to 12dp,
 * which happens if we are showing a list dialog with no title and no action buttons.
 */
internal fun MDRootView.shouldReduceMainFrameTopPadding(): Boolean {
  return !titleView.isVisible()
      && getVisibleButtons().isEmpty()
      && frameMain.childCount > 1
      && frameMain.getChildAt(1) is RecyclerView
}

/**
 * If [shouldAddContentBottomPadding] returns false, will return 0. Otherwise,
 * returns 24dp if we have a title frame or button frame, else 12dp.
 */
internal fun MDRootView.getContentBottomPadding(): Int {
  return if (shouldAddContentBottomPadding()) {
    when {
      titleView.isVisible() -> dialogFrameMargin
      getVisibleButtons().isNotEmpty() -> dialogFrameMargin
      else -> dialogFrameMarginHalf
    }
  } else {
    0
  }
}

/**
 * We want to add padding to the content view (the view below the title) if the content view
 * is a RecyclerView, meaning we're showing a list dialog.
 */
internal fun MDRootView.shouldAddContentBottomPadding(): Boolean {
  return frameMain.childCount > 1
      && frameMain.getChildAt(1) is RecyclerView
}