package com.afollestad.materialdialogs.extensions

import android.support.v7.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog

// TODO(Aidan)
fun MaterialDialog.listAdapter(adapter: RecyclerView.Adapter<*>): MaterialDialog {
  return this
}

// TODO(Aidan)
//fun MaterialDialog.listItems(
//  @ArrayRes arrayRes: Int,
//  array: Array<CharSequence>? = null,
//  click: (() -> (Int))?
//): MaterialDialog {
//  assertOneSet(arrayRes, array)
//  val items = array ?: resolveStringArray(arrayRes)
//  return listAdapter(kStandardListAdapter(context, R.layout.md_dialog_list, items, click))
//}

fun MaterialDialog.singleChoice(selectionChanged: (() -> (Int))?): MaterialDialog {
  return this
}

// TODO(Aidan)
fun MaterialDialog.multipleChoice(selectionChanged: (() -> (Array<Int>))?): MaterialDialog {
  return this
}