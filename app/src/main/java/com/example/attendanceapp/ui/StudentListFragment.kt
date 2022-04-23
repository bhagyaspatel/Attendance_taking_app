package com.example.attendanceapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceapp.Data.Student
import com.example.attendanceapp.R
import com.example.attendanceapp.ui.swipe.StudentListSwipeLeft
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_student_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "student"
class StudentListFragment : Fragment() {

    private lateinit var viewModel : StudentViewModel
    private lateinit var studentList : MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(StudentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val flag = StudentListFragmentArgs.fromBundle(requireArguments()).flag
        Log.d(TAG, "$flag")

        if (flag == 0){
            studentList = aList
        }else if (flag == 1){
            studentList = bList
        }else{
            studentList = cList
        }

        val backupList = studentList

        with(student_list_rv){
            layoutManager = LinearLayoutManager(activity)
            adapter = StudentListAdapter(studentList)
        }

        val leftSwipeHandler = object : StudentListSwipeLeft(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterL = student_list_rv.adapter as StudentListAdapter

                GlobalScope.launch {
                    val name = studentList[viewHolder.absoluteAdapterPosition].name
                    val usn = studentList[viewHolder.absoluteAdapterPosition].usn
                    val id = studentList[viewHolder.absoluteAdapterPosition].id

                    val student = Student(id, name, usn)
                    viewModel.insertStudent(student)
                }
                adapterL.removeAt(viewHolder.absoluteAdapterPosition)

                val snackbar = Snackbar.make(view, "Student mark absent.", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Undo!", View.OnClickListener {

                    val findId = backupList[viewHolder.absoluteAdapterPosition].id
                    var student : Student? = null
                    viewModel.students.observe(viewLifecycleOwner) {
                        for (i in it.indices) {
                            if (it[i].id == findId) {
                                student = it[i]
                                break
                            }
                        }
                    }
                    GlobalScope.launch {
                        viewModel.delteStudent(student!!)
                    }
                    adapterL.putBack(student!!)
                })
                snackbar.show()
            }
        }

        val itemTouchLeftHelper = ItemTouchHelper(leftSwipeHandler)
        itemTouchLeftHelper.attachToRecyclerView(student_list_rv)

        val rightSwipeHandler = object : StudentListSwipeLeft(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterR = student_list_rv.adapter as StudentListAdapter
                adapterR.removeAt(viewHolder.absoluteAdapterPosition)
            }
        }
        val itemTouchRighttHelper = ItemTouchHelper(rightSwipeHandler)
        itemTouchRighttHelper.attachToRecyclerView(student_list_rv)

        absentBtn.setOnClickListener{
            findNavController().navigate(
                StudentListFragmentDirections.actionStudentListFragmentToAbsentListFragment()
            )
        }
    }

//    private suspend fun addToAbsentList(position : Int) {
//        Log.d(TAG, "studentListFrag index : $position") //also (-1)
//        val name = studentList[position].name
//        val usn = studentList[position].usn
//        val id = studentList[position].id
//
//        val student = Student(id, name, usn)
//        viewModel.insertStudent(student)
//    }
}