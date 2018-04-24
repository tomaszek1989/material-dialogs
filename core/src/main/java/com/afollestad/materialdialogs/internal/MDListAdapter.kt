package com.afollestad.materialdialogs.internal

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.afollestad.materialdialogs.KEY_AUTO_DISMISS
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme
import com.afollestad.materialdialogs.Theme.LIGHT
import com.afollestad.materialdialogs.extensions.get
import com.afollestad.materialdialogs.extensions.inflate

internal class MDListViewHolder(
  itemView: View,
  adapter: MDListAdapter,
  dialog: MaterialDialog
) : RecyclerView.ViewHolder(itemView) {
  init {
    itemView.setOnClickListener {
      adapter.click?.invoke(dialog, adapterPosition, adapter.items[adapterPosition])
      if (dialog.data[KEY_AUTO_DISMISS] as Boolean) {
        dialog.dismiss()
      }
    }
  }

  val titleView: TextView = (itemView as ViewGroup)[0]!!
}

internal class MDListAdapter(
  private var dialog: MaterialDialog,
  @LayoutRes private var itemLayout: Int,
  internal var items: Array<CharSequence>,
  internal var click: ((MaterialDialog, Int, CharSequence) -> (Unit))?
) : RecyclerView.Adapter<MDListViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MDListViewHolder {
    val listItemView: View = parent.inflate(itemLayout)
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
    holder.itemView.background = getItemSelector(holder.itemView.context)
  }

  private fun getItemSelector(context: Context): Drawable? {
    val resId = when (dialog.theme) {
      LIGHT -> R.drawable.md_item_selector
      else -> R.drawable.md_item_selected_dark
    }
    return ContextCompat.getDrawable(context, resId)
  }
}