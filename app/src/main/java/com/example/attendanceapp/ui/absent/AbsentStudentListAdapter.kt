package com.example.attendanceapp.ui.absent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceapp.Data.Student
import com.example.attendanceapp.R
import kotlinx.android.extensions.LayoutContainer

class AbsentStudentListAdapter (val list : MutableList<Student>) : RecyclerView.Adapter<AbsentStudentListAdapter.viewHolder>() {

    inner class viewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        val absentName = containerView.findViewById<TextView>(R.id.absentName)
        val absentUsn = containerView.findViewById<TextView>(R.id.absentUsn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.absent_item_list, parent, false)
        return viewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = list[position]

        holder.absentName.setText(currentItem.name)
        holder.absentUsn.setText(currentItem.usn)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}