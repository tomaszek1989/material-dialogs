package com.afollestad.materialdialogs.internal

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ScrollView
import com.afollestad.materialdialogs.extensions.get
import android.opengl.ETC1.getHeight
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.afollestad.materialdialogs.extensions.waitForLayout

/**
 * A [RecyclerView] which reports whether or not it's scrollable, along with reporting back to a
 * [MDRootView] to invalidate dividers.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class MDRecyclerView(
  context: Context?,
  attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

  var rootView: MDRootView? = null

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    waitForLayout { invalidateDividers() }
    addOnScrollListener(scrollListeners)
  }

  override fun onDetachedFromWindow() {
    removeOnScrollListener(scrollListeners)
    super.onDetachedFromWindow()
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

  private fun isScrollable(): Boolean {
    val lm = layoutManager
    val itemCount = adapter.itemCount
    return when (lm) {
      is LinearLayoutManager -> {
        val diff = lm.findLastVisibleItemPosition() - lm.findFirstVisibleItemPosition()
        return itemCount > diff
      }
      is GridLayoutManager -> {
        val diff = lm.findLastVisibleItemPosition() - lm.findFirstVisibleItemPosition()
        return itemCount > diff
      }
      else -> {
        Log.w(
            "MaterialDialogs", "LayourManager of type ${lm.javaClass.name} is currently supported."
        )
        return false
      }
    }
  }

  private fun isAtTop(): Boolean {
    if (!isScrollable()) {
      return false
    }
    val lm = layoutManager
    return when (lm) {
      is LinearLayoutManager -> lm.findFirstCompletelyVisibleItemPosition() == 0
      is GridLayoutManager -> lm.findFirstCompletelyVisibleItemPosition() == 0
      else -> false
    }
  }

  private fun isAtBottom(): Boolean {
    if (!isScrollable()) {
      return false
    }
    val lastIndex = adapter.itemCount - 1
    val lm = layoutManager
    return when (lm) {
      is LinearLayoutManager -> lm.findLastVisibleItemPosition() == lastIndex
      is GridLayoutManager -> lm.findLastVisibleItemPosition() == lastIndex
      else -> false
    }
  }

  private val scrollListeners = object : RecyclerView.OnScrollListener() {
    override fun onScrolled(
      recyclerView: RecyclerView?,
      dx: Int,
      dy: Int
    ) {
      super.onScrolled(recyclerView, dx, dy)
      invalidateDividers()
    }
  }

  private fun invalidateDividers() {
    if (childCount == 0 || measuredHeight == 0) {
      return
    }
    rootView?.invalidateDividers(!isAtTop(), !isAtBottom())
  }
}