package com.afollestad.materialdialogs.internal

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.extensions.get
import com.afollestad.materialdialogs.extensions.getItemSelector
import com.afollestad.materialdialogs.extensions.inflate

/** @author Aidan Follestad (afollestad) */
internal class MDListViewHolder(
  itemView: View,
  adapter: MDListAdapter,
  dialog: MaterialDialog
) : RecyclerView.ViewHolder(itemView) {
  init {
    itemView.setOnClickListener {
      adapter.click?.invoke(dialog, adapterPosition, adapter.items[adapterPosition])
      if (dialog.autoDismiss) {
        dialog.dismiss()
      }
    }
  }

  val titleView: TextView = (itemView as ViewGroup)[0]!!
}

/**
 * The default list adapter for list dialogs, containing plain textual list items.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class MDListAdapter(
  private var dialog: MaterialDialog,
  internal var items: Array<CharSequence>,
  internal var click: ((MaterialDialog, Int, CharSequence) -> (Unit))?
) : RecyclerView.Adapter<MDListViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MDListViewHolder {
    val listItemView: View = parent.inflate(R.layout.md_listitem)
    return MDListViewHolder(listItemView, this, dialog)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onBindViewHolder(
    holder: MDListViewHolder,
    position: Int
  ) {
    val titleValue = items[position]
    holder.titleView.text = titleValue
    holder.itemView.background = dialog.getItemSelector(holder.itemView.context)
  }
}