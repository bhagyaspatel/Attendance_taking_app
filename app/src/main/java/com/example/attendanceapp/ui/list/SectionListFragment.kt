package com.example.attendanceapp.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.attendanceapp.Data.Student
import com.example.attendanceapp.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_section_list.*
import kotlinx.android.synthetic.main.fragment_section_list.toolbar
import kotlinx.android.synthetic.main.fragment_student_list.*

val aList : MutableList<Student> = mutableListOf(Student(0,"Aman Kumar", "1MS20IS032"),
    Student(1,"Ayush Jain", "1MS20IS30"),
    Student(2,"Pandey Kumar", "1MS20IS082"),
    Student(3,"Pranjal Dixit", "1MS20IS086"))

val bList : MutableList<Student> = mutableListOf(Student(11,"Patel Bhagya", "1MS20IS084"),
    Student(4,"Kumar Satyam", "1MS20IS145"),
    Student(5,"Pandey Shushmit Kumar", "1MS20IS082"),
    Student(6,"Pranjal Dixit", "1MS20IS086"))

val cList : MutableList<Student> = mutableListOf(Student(10,"Ayush Tarway", "1MS20IS096"),
    Student(7,"Rohin joshi", "1MS20IS92"),
    Student(8,"Pandey Shushmit", "1MS20IS082"),
    Student(9,"Pranjal Dixit", "1MS20IS086"))

class SectionListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigationDrawer()

        aSecBtn.setOnClickListener{
            Log.d(TAG, "A sec")
            findNavController().navigate(
                SectionListFragmentDirections.actionSectionListFragment2ToStudentListFragment(0)
            )
        }

        bSecBtn.setOnClickListener{
            Log.d(TAG, "B sec")
            findNavController().navigate(
                SectionListFragmentDirections.actionSectionListFragment2ToStudentListFragment(1)
            )
        }

        cSecBtn.setOnClickListener{
            Log.d(TAG, "C sec")
            findNavController().navigate(
                SectionListFragmentDirections.actionSectionListFragment2ToStudentListFragment(2)
            )
        }
    }

    private fun setupNavigationDrawer() {
        val navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = requireActivity().findViewById<NavigationView>(R.id.navigation_view)

        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)
        navigationView.setupWithNavController(navController)

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            when(it.itemId){
                R.id.calenderBtn ->{
                    findNavController().navigate(
                        SectionListFragmentDirections.actionSectionListFragment2ToCalenderFragment())
                    true
                }
                R.id.aboutPage ->{
                    findNavController().navigate(
                        SectionListFragmentDirections.actionSectionListFragment2ToAboutFragment())
                    true
                }
                R.id.contactPage ->{
                    findNavController().navigate(
                        SectionListFragmentDirections.actionSectionListFragment2ToContactFragment())
                    true
                }
                else -> false
            }
        }
    }
}