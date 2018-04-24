package com.afollestad.materialdialogs.internal

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ScrollView
import com.afollestad.materialdialogs.extensions.get
import com.afollestad.materialdialogs.extensions.waitForLayout

/**
 * A [ScrollView] which reports whether or not it's scrollable based on whether the content
 * is shorter than the ScrollView itself. Also reports back to an [MDRootView] to invalidate
 * dividers.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class MDScrollView(
  context: Context?,
  attrs: AttributeSet? = null
) : ScrollView(context, attrs) {

  var isScrollable = false
  var rootView: MDRootView? = null

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    waitForLayout { invalidateDividers() }
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    val width = MeasureSpec.getSize(widthMeasureSpec)
    if (childCount == 0) {
      super.onMeasure(width, 0)
      return
    }
    val height = MeasureSpec.getSize(heightMeasureSpec)
    val child: ViewGroup = this[0]!!

    child.measure(
        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
    )
    isScrollable = child.measuredHeight > height

    if (child.measuredHeight < height) {
      setMeasuredDimension(width, child.measuredHeight)
    } else {
      setMeasuredDimension(width, height)
    }
  }

  override fun onScrollChanged(
    left: Int,
    top: Int,
    oldl: Int,
    oldt: Int
  ) {
    super.onScrollChanged(left, top, oldl, oldt)
    invalidateDividers()
  }

  private fun invalidateDividers() {
    if (childCount == 0 || measuredHeight == 0 || !isScrollable) {
      return
    }
    val view = getChildAt(childCount - 1)
    val diff = view.bottom - (measuredHeight + scrollY)
    rootView?.invalidateDividers(scrollY > 0, diff > 0)
  }
}