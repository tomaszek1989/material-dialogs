package com.afollestad.materialdialogs

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

internal class kStandardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

internal class kStandardListAdapter(
  context: Context,
  @LayoutRes itemLayout: Int,
  items: Array<CharSequence>,
  click: (() -> (Int))?
) : RecyclerView.Adapter<kStandardViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): kStandardViewHolder {
    TODO(
        "not implemented"
    ) //To change body of created functions use File | Settings | File Templates.
  }

  override fun getItemCount(): Int {
    TODO(
        "not implemented"
    ) //To change body of created functions use File | Settings | File Templates.
  }

  override fun onBindViewHolder(
    holder: kStandardViewHolder,
    position: Int
  ) {
    TODO(
        "not implemented"
    ) //To change body of created functions use File | Settings | File Templates.
  }

}