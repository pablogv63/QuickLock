package com.pablogv63.quicklock.utilities

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.pablogv63.quicklock.EditActivity
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.RecyclerAdapter

class SwipeCallback(val myAdapter: RecyclerAdapter, val context: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    var icon: Drawable = ContextCompat.getDrawable(myAdapter.context, R.drawable.ic_baseline_edit_24)!!
    private var background: ColorDrawable = ColorDrawable(ContextCompat.getColor(myAdapter.context,R.color.cardEditIconBG))

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        //Do nothing
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        myAdapter.openEditActivity(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        when {
            (dX > 0) -> { //Swipe right
                val iconLeft = itemView.left + iconMargin;
                val iconRight = itemView.left + iconMargin  + icon.intrinsicWidth;
                icon.setBounds(iconLeft,iconTop,iconRight,iconBottom)
                background.setBounds(itemView.left,itemView.top,itemView.left + (dX.toInt()) + backgroundCornerOffset,itemView.bottom)
            }
            (dX < 0) -> { //Swipe left
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth;
                val iconRight = itemView.right - iconMargin;
                icon.setBounds(iconLeft,iconTop,iconRight,iconBottom)
                background.setBounds(itemView.right + (dX.toInt()) - backgroundCornerOffset,itemView.top,itemView.right,itemView.bottom)
            }
            else -> {
                background.setBounds(0,0,0,0)
            }
        }
        background.draw(c)
        icon.draw(c)
    }


}