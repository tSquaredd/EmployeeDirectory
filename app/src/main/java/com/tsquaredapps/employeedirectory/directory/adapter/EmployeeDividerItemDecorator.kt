package com.tsquaredapps.employeedirectory.directory.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.tsquaredapps.employeedirectory.databinding.EmployeeItemBinding
import com.tsquaredapps.employeedirectory.ext.dpToPx

class EmployeeDividerItemDecorator(private val divider: Drawable) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft + 16.dpToPx()
        val dividerRight = parent.width - parent.paddingRight - 16.dpToPx()
        val childCount = parent.childCount
        for (position in 0 until childCount - 1) {
            if (parent.shouldDrawDividerAt(position)) {
                val child = parent.getChildAt(position)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop = child.bottom + params.bottomMargin
                val dividerBottom: Int = dividerTop + divider.intrinsicHeight
                divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                divider.draw(canvas)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (parent.shouldDrawDividerAt(position)
        ) {
            outRect.top = divider.intrinsicHeight
        }
    }

    private fun RecyclerView.shouldDrawDividerAt(position: Int): Boolean {
        return (position + 1 < childCount &&
                (getChildViewHolder(get(position)) as AdapterDelegateViewBindingViewHolder<*, *>).binding is EmployeeItemBinding &&
                (getChildViewHolder(get(position + 1)) as AdapterDelegateViewBindingViewHolder<*, *>).binding is EmployeeItemBinding)
    }
}