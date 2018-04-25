package com.afollestad.materialdialogs.internal

import android.view.View.MeasureSpec

internal fun MDRootView.measureButtons(parentWidth: Int) {
  val visibleButtons = getVisibleButtons()
  for (button in visibleButtons) {
    button.update(theme, stackButtons)
    if (stackButtons) {
      button.measure(
          MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY),
          MeasureSpec.makeMeasureSpec(buttonHeightStacked, MeasureSpec.EXACTLY)
      )
    } else {
      button.measure(
          MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
          MeasureSpec.makeMeasureSpec(buttonHeightDefault, MeasureSpec.EXACTLY)
      )
    }
  }

  if (visibleButtons.isNotEmpty() && !stackButtons) {
    var totalWidth = 0
    for (button in getVisibleButtons()) {
      totalWidth += button.measuredWidth + buttonSpacing
    }
    if (totalWidth >= parentWidth) {
      stackButtons = true
      requestLayout()
    }
  }
}

internal fun MDRootView.measureMain(
  specWidth: Int,
  specHeight: Int
): Int {
  val buttonFrameHeight = getTotalButtonFrameHeight()
  val mainFrameTopPadding = getMainFrameTopPadding()
  val mainFrameMaxHeight =
    specHeight - mainFrameTopPadding - buttonFrameHeight
  frameMain.measure(
      MeasureSpec.makeMeasureSpec(specWidth, MeasureSpec.EXACTLY),
      MeasureSpec.makeMeasureSpec(mainFrameMaxHeight, MeasureSpec.AT_MOST)
  )

  val totalDialogHeight =
    frameMain.measuredHeight + buttonFrameHeight + mainFrameTopPadding
  return if (totalDialogHeight < specHeight) {
    // We can reduce the height even more since the content doesn't need all this space
    totalDialogHeight
  } else {
    specHeight
  }
}