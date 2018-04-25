package com.afollestad.materialdialogs.internal

internal fun MDRootView.layoutButtonFrame() {
  val buttonFrameHeight = getTotalButtonFrameHeight()
  if (stackButtons) {
    var topY = measuredHeight - buttonFrameHeight
    for (button in getVisibleButtons()) {
      val bottomY = topY + buttonHeightStacked
      button.layout(0, topY, measuredWidth, bottomY)
      topY = bottomY
    }
  } else {
    var rightX = measuredWidth - buttonFramePadding
    val topY = measuredHeight - (buttonFrameHeight - buttonFramePadding)
    val bottomY = measuredHeight - buttonFramePadding
    for (button in getVisibleButtons()) {
      val leftX = rightX - button.measuredWidth
      button.layout(leftX, topY, rightX, bottomY)
      rightX = leftX - buttonSpacing
    }
  }
}

internal fun MDRootView.layoutMainFrame() {
  val buttonFrameHeight = getTotalButtonFrameHeight()
  val frameButtonsTop = (measuredHeight - buttonFrameHeight)
  val mainFrameTop = getMainFrameTopPadding()
  frameMain.layout(0, mainFrameTop, measuredWidth, frameButtonsTop)
}