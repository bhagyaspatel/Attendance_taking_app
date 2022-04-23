package com.example.attendanceapp.ui.swipe

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class StudentListSwipeRight : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#07b318")

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
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

        val itemView = viewHolder.itemView

        background.color = backgroundColor
        background.setBounds(itemView.left, itemView.top, itemView.right + dX.toInt(), itemView.bottom )
        background.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}