package com.example.attendanceapp.ui.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceapp.Data.Student
import com.example.attendanceapp.R
import com.example.attendanceapp.ui.StudentViewModel
import com.example.attendanceapp.ui.swipe.StudentListSwipeLeft
import com.example.attendanceapp.ui.swipe.StudentListSwipeRight
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_student_list.*
import kotlinx.coroutines.*

const val TAG = "student"
class StudentListFragment : Fragment(){

    private lateinit var viewModel : StudentViewModel
    private lateinit var studentList : MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(StudentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.markall_menu_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val studentAdapter = student_list_rv.adapter as StudentListAdapter

        return when(item.itemId){
            R.id.allAbsent ->{
                studentAdapter.markAll()
                viewModel.insertAll(studentList)
                true
            }
            R.id.allPresent ->{
                studentAdapter.markAll()
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val flag = StudentListFragmentArgs.fromBundle(requireArguments()).flag

        if (flag == 0){
            studentList = aList
        }else if (flag == 1){
            studentList = bList
        }else{
            studentList = cList
        }

        with(student_list_rv){
            layoutManager = LinearLayoutManager(activity)
            adapter = StudentListAdapter(studentList)
        }

        val leftSwipeHandler = object : StudentListSwipeLeft(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterL = student_list_rv.adapter as StudentListAdapter

                GlobalScope.launch {
                    val student = studentList[viewHolder.absoluteAdapterPosition]
                    val position = viewHolder.absoluteAdapterPosition
                    viewModel.insertStudent(student)

                    withContext(Dispatchers.Main) {
                        adapterL.removeAt(viewHolder.absoluteAdapterPosition)

                        val snackbar = Snackbar.make(view, "Student mark absent.", Snackbar.LENGTH_INDEFINITE)
                        snackbar.setAction("Undo!", View.OnClickListener {
                            //putting back the student in the list
                            adapterL.putBack(student, position)

                            //deleting from absent list
                            viewModel.students.observe(viewLifecycleOwner) {
                                for (i in it.indices) {
                                    if (it[i].id == student.id) {
                                        GlobalScope.launch {
                                            viewModel.delteStudent(student)
                                        }
                                        break
                                    }
                                }
                            }
                        })
                        snackbar.show()
                    }
                }
            }
        }
        val itemTouchLeftHelper = ItemTouchHelper(leftSwipeHandler)
        itemTouchLeftHelper.attachToRecyclerView(student_list_rv)

        val rightSwipeHandler = object : StudentListSwipeRight(){
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

}