package com.afollestad.materialdialogs.internal

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.extensions.getItemSelector
import com.afollestad.materialdialogs.extensions.hasActionButtons
import com.afollestad.materialdialogs.extensions.inflate

/** @author Aidan Follestad (afollestad) */
internal class MDSingleChoiceViewHolder(
  itemView: View,
  adapter: MDSingleChoiceAdapter,
  dialog: MaterialDialog
) : RecyclerView.ViewHolder(itemView) {
  init {
    itemView.setOnClickListener {
      adapter.currentSelection = adapterPosition
      adapter.selectionChanged?.invoke(dialog, adapterPosition, adapter.items[adapterPosition])
      if (dialog.autoDismiss && !dialog.hasActionButtons()) {
        dialog.dismiss()
      }
    }
  }

  val controlView: CompoundButton = itemView.findViewById(R.id.md_control)
  val titleView: TextView = itemView.findViewById(R.id.md_title)
}

/**
 * The default list adapter for single choice (radio button) list dialogs.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class MDSingleChoiceAdapter(
  private var dialog: MaterialDialog,
  internal var items: Array<CharSequence>,
  initialSelection: Int,
  internal var selectionChanged: ((MaterialDialog, Int, CharSequence) -> (Boolean))?
) : RecyclerView.Adapter<MDSingleChoiceViewHolder>() {

  var currentSelection: Int = -1
    set(value) {
      val previousSelection = field
      field = value
      notifyItemChanged(previousSelection)
      notifyItemChanged(value)
    }

  init {
    this.currentSelection = initialSelection
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MDSingleChoiceViewHolder {
    val listItemView: View = parent.inflate(R.layout.md_listitem_singlechoice)
    return MDSingleChoiceViewHolder(listItemView, this, dialog)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onBindViewHolder(
    holder: MDSingleChoiceViewHolder,
    position: Int
  ) {
    holder.controlView.isChecked = currentSelection == position
    holder.titleView.text = items[position]
    holder.itemView.background = dialog.getItemSelector(holder.itemView.context)
  }
}