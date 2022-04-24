package com.example.attendanceapp.ui.absent

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendanceapp.R
import com.example.attendanceapp.ui.StudentViewModel
import com.example.attendanceapp.ui.list.TAG
import kotlinx.android.synthetic.main.fragment_absent_list.*
import java.text.DateFormat
import java.util.*

class AbsentListFragment : Fragment() {

    private lateinit var viewModel : StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(StudentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_absent_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val calendar = Calendar.getInstance()
        val currentDate : String = DateFormat.getDateInstance().format(calendar.time)
        dateTxt.setText(currentDate)

        viewModel.students.observe(viewLifecycleOwner){
            with(absent_student_list_rv){
                layoutManager = LinearLayoutManager(activity)
                adapter = AbsentStudentListAdapter(it)
            }
        }
    }
}