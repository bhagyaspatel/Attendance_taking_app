package com.example.attendanceapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendanceapp.R
import kotlinx.android.synthetic.main.fragment_absent_list.*

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

        viewModel.students.observe(viewLifecycleOwner){
            with(absent_student_list_rv){
                layoutManager = LinearLayoutManager(activity)
                adapter = AbsentStudentListAdapter(it)
            }
        }
    }
}