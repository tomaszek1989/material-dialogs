package com.afollestad.materialdialogs.internal

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ScrollView
import com.afollestad.materialdialogs.extensions.get

/**
 * @author Aidan Follestad (afollestad)
 */
internal class MDScrollView(
  context: Context?,
  attrs: AttributeSet? = null
) : ScrollView(context, attrs) {

  var isScrollable = false

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
}