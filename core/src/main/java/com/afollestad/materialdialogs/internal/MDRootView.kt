package com.afollestad.materialdialogs.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Paint.Style.STROKE
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme
import com.afollestad.materialdialogs.Theme.LIGHT
import com.afollestad.materialdialogs.extensions.dp
import com.afollestad.materialdialogs.extensions.isVisible

internal class MDRootView(
  context: Context,
  attrs: AttributeSet?
) : FrameLayout(context, attrs) {

  var maxHeight: Int = 0

  private val dialogFrameMargin =
    context.resources.getDimensionPixelSize(R.dimen.md_dialog_frame_margin)
        .toFloat()
  private val titleFrameMarginBottom =
    context.resources.getDimensionPixelSize(R.dimen.md_title_frame_margin_bottom)
        .toFloat()
  private val buttonHeightDefault =
    context.resources.getDimensionPixelSize(R.dimen.md_action_button_height)
  private val buttonHeightStacked =
    context.resources.getDimensionPixelSize(R.dimen.md_stacked_action_button_height)
  private val buttonFramePadding =
    context.resources.getDimensionPixelSize(R.dimen.md_action_button_frame_padding)
  private val buttonSpacing =
    context.resources.getDimensionPixelSize(R.dimen.md_action_button_spacing)

  var debugMode: Boolean = false
    set(value) {
      field = value
      invalidate()
    }
  var theme: Theme = Theme.LIGHT
    set(value) {
      field = value
      requestLayout()
    }
  var stackButtons: Boolean = false
    set(value) {
      field = value
      requestLayout()
    }

  private var debugPaint: Paint? = null
  private val dividerPaint: Paint

  private lateinit var frameMain: ViewGroup
  private lateinit var titleView: TextView
  private lateinit var actionButtons: Array<MDActionButton>

  init {
    setWillNotDraw(false)
    dividerPaint = Paint()
    dividerPaint.style = STROKE
    dividerPaint.strokeWidth = context.resources.getDimension(R.dimen.md_divider_height)
    dividerPaint.isAntiAlias = true
  }

  override fun onFinishInflate() {
    super.onFinishInflate()
    frameMain = findViewById(R.id.md_frame_main)
    titleView = findViewById(R.id.md_text_title)
    actionButtons = arrayOf(
        findViewById(R.id.md_button_positive),
        findViewById(R.id.md_button_negative),
        findViewById(R.id.md_button_neutral)
    )
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    val width = MeasureSpec.getSize(widthMeasureSpec)
    var height = MeasureSpec.getSize(heightMeasureSpec)
    if (height > maxHeight) {
      height = maxHeight
    }

    measureButtons(width)
    val buttonFrameHeight = getTotalButtonFrameHeight()

    val mainFrameMaxHeight = height - buttonFrameHeight
    frameMain.measure(
        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(mainFrameMaxHeight, MeasureSpec.AT_MOST)
    )

    if (frameMain.measuredHeight + buttonFrameHeight < height) {
      height = frameMain.measuredHeight + buttonFrameHeight
    }
    setMeasuredDimension(width, height)
  }

  private fun measureButtons(parentWidth: Int) {
    for (button in getVisibleButtons()) {
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

    if (!stackButtons) {
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

  override fun onLayout(
    changed: Boolean,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
  ) {
    // Buttons
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

    // Main frame
    val frameButtonsTop = bottom - buttonFrameHeight
    frameMain.layout(0, 0, measuredWidth, frameButtonsTop)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    dividerPaint.color = getDividerColor()
    if (!debugMode) {
      return
    }

    val buttonFrameHeight = getTotalButtonFrameHeight()
    val debugColorTeal = Color.parseColor("#B4F9FA")
    val debugColorPink = Color.parseColor("#E79ACA")

    if (debugPaint == null) {
      debugPaint = Paint()
      debugPaint!!.strokeWidth = 5f
      debugPaint!!.style = Style.FILL_AND_STROKE
      debugPaint!!.isAntiAlias = true
    }

    // Main frame fill
    debugPaint!!.color = debugColorTeal
    canvas.drawRect(
        0f, 0f, measuredWidth.toFloat(), measuredHeight - buttonFrameHeight.toFloat(),
        debugPaint!!
    )

    // Hollow title frame fill
    var titleFrameBottomY = dialogFrameMargin + titleView.measuredHeight
    debugPaint!!.color = Color.WHITE
    if (titleView.isVisible()) {
      canvas.drawRect(
          dialogFrameMargin, dialogFrameMargin, measuredWidth - dialogFrameMargin,
          titleFrameBottomY,
          debugPaint!!
      )
    } else {
      // 4dp, added to 20dp, makes 24dp
      titleFrameBottomY = 4.dp(resources)
    }

    // Hollow content frame fill
    val contentHollowStopY = measuredHeight - buttonFrameHeight - dialogFrameMargin
    canvas.drawRect(
        dialogFrameMargin, titleFrameBottomY + titleFrameMarginBottom,
        measuredWidth - dialogFrameMargin,
        contentHollowStopY,
        debugPaint!!
    )

    // Button frame fill
    debugPaint!!.color = debugColorPink
    canvas.drawRect(
        0f, measuredHeight - buttonFrameHeight.toFloat(), measuredWidth.toFloat(),
        measuredHeight.toFloat(), debugPaint!!
    )

    if (stackButtons) {
      // Button fills
      debugPaint!!.color = Color.WHITE
      debugPaint!!.style = Style.FILL_AND_STROKE
      for (button in getVisibleButtons()) {
        canvas.drawRect(
            button.left.toFloat(), button.top.toFloat(), button.right.toFloat(),
            button.bottom.toFloat(), debugPaint!!
        )
      }
      // Button outlines
      debugPaint!!.color = debugColorPink
      debugPaint!!.style = Style.STROKE
      for (button in getVisibleButtons()) {
        canvas.drawRect(
            button.left.toFloat(), button.top.toFloat(), button.right.toFloat(),
            button.bottom.toFloat(), debugPaint!!
        )
      }
      debugPaint!!.style = Style.FILL_AND_STROKE
      canvas.drawRect(
          0f, measuredHeight - buttonFramePadding.toFloat(), measuredWidth.toFloat(),
          measuredHeight.toFloat(), debugPaint!!
      )
    } else {
      // Hollow button frame fill
      debugPaint!!.color = Color.WHITE
      val topY = (measuredHeight - (buttonFrameHeight - buttonFramePadding)).toFloat()
      val bottomY = (measuredHeight - buttonFramePadding).toFloat()
      canvas.drawRect(
          dialogFrameMargin, topY, measuredWidth - buttonFramePadding.toFloat(),
          bottomY, debugPaint!!
      )
      // Button highlight and spacing fill
      debugPaint!!.color = debugColorTeal
      for (button in getVisibleButtons()) {
        canvas.drawRect(
            button.left.toFloat(), button.top.toFloat(), button.right.toFloat(),
            button.bottom.toFloat(), debugPaint!!
        )
      }
    }
  }

  @ColorInt
  private fun getDividerColor(): Int {
    val colorRes = if (theme == LIGHT) R.color.md_divider_black else R.color.md_divider_white
    return ContextCompat.getColor(context, colorRes)
  }

  private fun getVisibleButtons(): Array<MDActionButton> {
    return actionButtons.filter { it.isVisible() }
        .toTypedArray()
  }

  /** Buttons plus any spacing around that makes up the "frame" */
  private fun getTotalButtonFrameHeight(): Int {
    return if (stackButtons) {
      (getVisibleButtons().size * buttonHeightStacked) + buttonFramePadding
    } else {
      buttonHeightDefault + (buttonFramePadding * 2)
    }
  }
}
