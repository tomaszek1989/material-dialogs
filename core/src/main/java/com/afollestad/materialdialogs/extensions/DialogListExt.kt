@file:Suppress("unused")

package com.afollestad.materialdialogs.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme.LIGHT
import com.afollestad.materialdialogs.assertOneSet
import com.afollestad.materialdialogs.internal.MDListAdapter
import com.afollestad.materialdialogs.internal.MDSingleChoiceAdapter

private fun MaterialDialog.addContentRecyclerView() {
  if (this.contentRecyclerView != null) {
    return
  }
  this.contentRecyclerView = inflate(context, R.layout.md_dialog_stub_recyclerview, this.mainFrame)
  this.contentRecyclerView!!.rootView = this.view
  this.contentRecyclerView!!.layoutManager = LinearLayoutManager(context)
  this.mainFrame.addView(this.contentRecyclerView)
}

internal fun MaterialDialog.getItemSelector(context: Context): Drawable? {
  val resId = when (theme) {
    LIGHT -> R.drawable.md_item_selector
    else -> R.drawable.md_item_selected_dark
  }
  return ContextCompat.getDrawable(context, resId)
}

fun MaterialDialog.listAdapter(adapter: RecyclerView.Adapter<*>): MaterialDialog {
  addContentRecyclerView()
  this.contentRecyclerView!!.adapter = adapter
  return this
}

fun MaterialDialog.listItems(
  @ArrayRes arrayRes: Int = 0,
  array: Array<CharSequence>? = null,
  click: ((dialog: MaterialDialog, index: Int, text: CharSequence) -> (Unit))? = null
): MaterialDialog {
  assertOneSet(arrayRes, array)
  val items = array ?: getStringArray(arrayRes)
  return listAdapter(MDListAdapter(this, items, click))
}

fun MaterialDialog.singleChoiceListItems(
  @ArrayRes arrayRes: Int = 0,
  array: Array<CharSequence>? = null,
  initialSelection: Int = -1,
  selectionChanged: ((MaterialDialog, Int, CharSequence) -> (Boolean))? = null
): MaterialDialog {
  assertOneSet(arrayRes, array)
  val items = array ?: getStringArray(arrayRes)
  return listAdapter(MDSingleChoiceAdapter(this, items, initialSelection, selectionChanged))
}

fun MaterialDialog.multipleChoiceListItems(
  @ArrayRes arrayRes: Int = 0,
  array: Array<CharSequence>? = null,
  initialSelections: Array<Int>? = null,
  selectionChanged: ((MaterialDialog, Array<Int>, Array<CharSequence>) -> (Boolean))? = null
): MaterialDialog {
  // TODO(Aidan)
  return this
}