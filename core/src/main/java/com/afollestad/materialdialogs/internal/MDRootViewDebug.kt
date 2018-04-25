package com.afollestad.materialdialogs.internal

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import com.afollestad.materialdialogs.extensions.isVisible

private val debugColorTeal = Color.parseColor("#B4F9FA")
private val debugColorPink = Color.parseColor("#E79ACA")

internal fun MDRootView.drawDividers(canvas: Canvas) {
  val buttonFrameHeight = getTotalButtonFrameHeight()
  val mainFrameTop = getMainFrameTopPadding()
  dividerPaint.color = getDividerColor()

  if (showTopDivider && titleView.isVisible()) {
    val topDividerY = (titleView.bottom + titleFrameMarginBottom + mainFrameTop).toFloat()
    canvas.drawLine(0f, topDividerY, measuredWidth.toFloat(), topDividerY, dividerPaint)
  }
  if (showBottomDivider && buttonFrameHeight > 0) {
    val bottomDividerY = (measuredHeight - buttonFrameHeight).toFloat()
    canvas.drawLine(0f, bottomDividerY, measuredWidth.toFloat(), bottomDividerY, dividerPaint)
  }
}

private fun MDRootView.invalidateDebugPaint() {
  if (debugPaint != null) {
    return
  }
  debugPaint = Paint()
  debugPaint!!.strokeWidth = 5f
  debugPaint!!.style = Style.FILL_AND_STROKE
  debugPaint!!.isAntiAlias = true
}

internal fun MDRootView.drawMainFrameGuides(canvas: Canvas) {
  invalidateDebugPaint()
  val buttonFrameHeight = getTotalButtonFrameHeight()
  val mainFrameTop = getMainFrameTopPadding()

  // Main frame fill
  debugPaint!!.color = debugColorTeal
  canvas.drawRect(
      0f, 0f,
      measuredWidth.toFloat(),
      measuredHeight - buttonFrameHeight.toFloat(),
      debugPaint!!
  )

  // Hollow title frame fill
  debugPaint!!.color = Color.WHITE
  var contentTopY: Int = mainFrameTop
  if (titleView.isVisible()) {
    contentTopY = mainFrameTop + titleView.measuredHeight + titleFrameMarginBottom
    canvas.drawRect(
        dialogFrameMargin.toFloat(),
        mainFrameTop.toFloat(),
        measuredWidth - dialogFrameMargin.toFloat(),
        contentTopY.toFloat() - titleFrameMarginBottom,
        debugPaint!!
    )
  }

  // Hollow content frame fill
  val contentHollowStopY = measuredHeight - buttonFrameHeight - mainFrameTop
  canvas.drawRect(
      dialogFrameMargin.toFloat(),
      contentTopY.toFloat(),
      measuredWidth - dialogFrameMargin.toFloat(),
      contentHollowStopY.toFloat(),
      debugPaint!!
  )
}

private fun MDRootView.drawStackedButtonGuides(canvas: Canvas) {
  // Button fills
  debugPaint!!.color = Color.WHITE
  debugPaint!!.style = Style.FILL_AND_STROKE
  for (button in getVisibleButtons()) {
    canvas.drawRect(
        button.left.toFloat(),
        button.top.toFloat(),
        button.right.toFloat(),
        button.bottom.toFloat(),
        debugPaint!!
    )
  }
  // Button outlines
  debugPaint!!.color = debugColorPink
  debugPaint!!.style = Style.STROKE
  for (button in getVisibleButtons()) {
    canvas.drawRect(
        button.left.toFloat(),
        button.top.toFloat(),
        button.right.toFloat(),
        button.bottom.toFloat(),
        debugPaint!!
    )
  }
}

private fun MDRootView.drawDefaultButtonGuides(canvas: Canvas) {
  val buttonFrameHeight = getTotalButtonFrameHeight()
  val mainFrameTop = getMainFrameTopPadding()
  // Hollow button frame fill
  debugPaint!!.color = Color.WHITE
  val topY = (measuredHeight - (buttonFrameHeight - buttonFramePadding)).toFloat()
  val bottomY = (measuredHeight - buttonFramePadding).toFloat()
  canvas.drawRect(
      mainFrameTop.toFloat(),
      topY,
      measuredWidth - buttonFramePadding.toFloat(),
      bottomY,
      debugPaint!!
  )
  // Button highlighting
  for (button in getVisibleButtons()) {
    debugPaint!!.color = debugColorTeal
    canvas.drawRect(
        button.left.toFloat(),
        button.top.toFloat(),
        button.right.toFloat(),
        button.bottom.toFloat(),
        debugPaint!!
    )
  }
}

internal fun MDRootView.drawButtonFrameGuides(canvas: Canvas) {
  val buttonFrameHeight = getTotalButtonFrameHeight()
  if (buttonFrameHeight == 0) {
    return
  }
  // Button frame fill
  debugPaint!!.color = debugColorPink
  canvas.drawRect(
      0f,
      measuredHeight - buttonFrameHeight.toFloat(),
      measuredWidth.toFloat(),
      measuredHeight.toFloat(),
      debugPaint!!
  )
  if (stackButtons) {
    drawStackedButtonGuides(canvas)
  } else {
    drawDefaultButtonGuides(canvas)
  }
  // Button frame bottom padding fill
  debugPaint!!.style = Style.FILL_AND_STROKE
  debugPaint!!.color = debugColorPink
  canvas.drawRect(
      0f,
      measuredHeight - buttonFramePadding.toFloat(),
      measuredWidth.toFloat(),
      measuredHeight.toFloat()
      , debugPaint!!
  )
}