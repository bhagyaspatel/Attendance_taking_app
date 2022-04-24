package com.example.attendanceapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceapp.Data.Student
import com.example.attendanceapp.R
import kotlinx.android.extensions.LayoutContainer

class StudentListAdapter (private var list : MutableList<Student>)
    : RecyclerView.Adapter<StudentListAdapter.studentViewHolder>() {

    inner class studentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val usn = containerView.findViewById<TextView>(R.id.usn)
        val studentName = containerView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): studentViewHolder {
        val itemLayout = LayoutInflater.from (parent.context).inflate(R.layout.item_list, parent, false)
        return studentViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: studentViewHolder, position: Int) {
        val currentItem = list[position]
        holder.studentName.text = currentItem.name
        holder.usn.text = currentItem.usn

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeAt (position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun putBack(student: Student, position : Int) {
        list.add(position, student)
        notifyItemInserted(position)
    }

    fun markAll() {
        list.removeAll(list)
        notifyDataSetChanged()
    }

}