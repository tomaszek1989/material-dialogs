package com.afollestad.materialdialogs.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Paint.Style.STROKE
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme
import com.afollestad.materialdialogs.Theme.LIGHT
import com.afollestad.materialdialogs.extensions.dimenPx
import com.afollestad.materialdialogs.extensions.isVisible
import com.afollestad.materialdialogs.extensions.updatePadding

/**
 * Handles all measurement and positioning of the dialog and its views. Helps us inflate quickly,
 * adapt to scenarios quickly, and get around weight dialog height stuff which would occur
 * if we used straight-up stock views only.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class MDRootView(
  context: Context,
  attrs: AttributeSet?
) : FrameLayout(context, attrs) {

  var maxHeight: Int = 0

  private val dialogFrameMargin = dimenPx(R.dimen.md_dialog_frame_margin)
  private val dialogFrameMarginHalf = dimenPx(R.dimen.md_dialog_frame_margin_half)

  private val titleFrameMarginBottom = dimenPx(R.dimen.md_title_frame_margin_bottom)
  private val buttonHeightDefault = dimenPx(R.dimen.md_action_button_height)
  private val buttonHeightStacked = dimenPx(R.dimen.md_stacked_action_button_height)
  private val buttonFramePadding = dimenPx(R.dimen.md_action_button_frame_padding)
  private val buttonSpacing = dimenPx(R.dimen.md_action_button_spacing)

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
  private var stackButtons: Boolean = false
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
    val contentBottomPadding = getContentBottomPadding()
    if (contentBottomPadding > 0) {
      val recyclerView = frameMain.getChildAt(1)
      recyclerView.updatePadding(bottom = contentBottomPadding)
    }

    val width = MeasureSpec.getSize(widthMeasureSpec)
    var height = MeasureSpec.getSize(heightMeasureSpec)
    if (height > maxHeight) {
      height = maxHeight
    }

    measureButtons(width)
    val buttonFrameHeight = getTotalButtonFrameHeight()
    val mainFrameTopPadding = getMainFrameTopPadding()
    val mainFrameMaxHeight =
      height - mainFrameTopPadding - buttonFrameHeight
    frameMain.measure(
        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(mainFrameMaxHeight, MeasureSpec.AT_MOST)
    )

    val totalDialogHeight =
      frameMain.measuredHeight + buttonFrameHeight + mainFrameTopPadding
    if (totalDialogHeight < height) {
      // We can reduce the height even more since the content doesn't need all this space
      height = totalDialogHeight
    }
    setMeasuredDimension(width, height)
  }

  private fun measureButtons(parentWidth: Int) {
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
    val frameButtonsTop = (measuredHeight - buttonFrameHeight)
    val mainFrameTop = getMainFrameTopPadding()
    frameMain.layout(0, mainFrameTop, measuredWidth, frameButtonsTop)
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
    val mainFrameTop = getMainFrameTopPadding()

    if (debugPaint == null) {
      debugPaint = Paint()
      debugPaint!!.strokeWidth = 5f
      debugPaint!!.style = Style.FILL_AND_STROKE
      debugPaint!!.isAntiAlias = true
    }

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

    // Button frame fill
    if (buttonFrameHeight > 0) {
      debugPaint!!.color = debugColorPink
      canvas.drawRect(
          0f,
          measuredHeight - buttonFrameHeight.toFloat(),
          measuredWidth.toFloat(),
          measuredHeight.toFloat(),
          debugPaint!!
      )

      if (stackButtons) {
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
        debugPaint!!.style = Style.FILL_AND_STROKE
        canvas.drawRect(
            0f,
            measuredHeight - buttonFramePadding.toFloat(),
            measuredWidth.toFloat(),
            measuredHeight.toFloat()
            , debugPaint!!
        )
      } else {
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
        // Button highlight and spacing fill
        debugPaint!!.color = debugColorTeal
        for (button in getVisibleButtons()) {
          canvas.drawRect(
              button.left.toFloat(),
              button.top.toFloat(),
              button.right.toFloat(),
              button.bottom.toFloat(), debugPaint!!
          )
        }
      }
    }
  }

  /** Gets the divider color, based on if the dialog is using the light or dark theme. */
  @ColorInt
  private fun getDividerColor(): Int {
    val colorRes = if (theme == LIGHT) R.color.md_divider_black else R.color.md_divider_white
    return ContextCompat.getColor(context, colorRes)
  }

  /** Gets an array of the visible action buttons (action buttons with text). */
  private fun getVisibleButtons(): Array<MDActionButton> {
    return actionButtons.filter { it.isVisible() }
        .toTypedArray()
  }

  /** Buttons plus any spacing around that makes up the "frame" */
  private fun getTotalButtonFrameHeight(): Int {
    val visibleButtons = getVisibleButtons()
    return when {
      visibleButtons.isEmpty() -> 0
      stackButtons -> (visibleButtons.size * buttonHeightStacked) + buttonFramePadding
      else -> buttonHeightDefault + (buttonFramePadding * 2)
    }
  }

  /** If [shouldReduceMainFrameTopPadding] returns true, 12dp else 24dp. */
  private fun getMainFrameTopPadding(): Int {
    return if (shouldReduceMainFrameTopPadding()) dialogFrameMarginHalf else dialogFrameMargin
  }

  /**
   * Returns true if we want to reduce the padding of the top of the dialog from 24dp to 12dp,
   * which happens if we are showing a list dialog with no title and no action buttons.
   */
  private fun shouldReduceMainFrameTopPadding(): Boolean {
    return !titleView.isVisible()
        && getVisibleButtons().isEmpty()
        && frameMain.childCount > 1
        && frameMain.getChildAt(1) is RecyclerView
  }

  /**
   * If [shouldAddContentBottomPadding] returns false, will return 0. Otherwise,
   * returns 24dp if we have a title frame or button frame, else 12dp.
   */
  private fun getContentBottomPadding(): Int {
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
  private fun shouldAddContentBottomPadding(): Boolean {
    return frameMain.childCount > 1
        && frameMain.getChildAt(1) is RecyclerView
  }
}
