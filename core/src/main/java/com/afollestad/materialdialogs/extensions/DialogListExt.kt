@file:Suppress("unused")

package com.afollestad.materialdialogs.extensions

import android.support.annotation.ArrayRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.assertOneSet
import com.afollestad.materialdialogs.internal.MDListAdapter

private fun MaterialDialog.addContentRecyclerView() {
  if (this.contentRecyclerView != null) {
    return
  }
  this.contentRecyclerView = inflate(context, R.layout.md_dialog_stub_recyclerview, this.mainFrame)
  this.contentRecyclerView!!.rootView = this.view
  this.contentRecyclerView!!.layoutManager = LinearLayoutManager(context)
  this.mainFrame.addView(this.contentRecyclerView)
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
  return listAdapter(MDListAdapter(this, R.layout.md_listitem, items, click))
}

fun MaterialDialog.singleChoice(selectionChanged: ((MaterialDialog, Int) -> (Unit))?): MaterialDialog {
  return this
}

// TODO(Aidan)
fun MaterialDialog.multipleChoice(selectionChanged: ((MaterialDialog) -> (Array<Int>))?): MaterialDialog {
  return this
}