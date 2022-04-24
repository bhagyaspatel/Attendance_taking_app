package com.example.attendanceapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.attendanceapp.Data.Student
import com.example.attendanceapp.Data.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel (context: Application) : AndroidViewModel(context) {
    private val repo : StudentRepository = StudentRepository(context)

    val students : LiveData<MutableList<Student>> = repo.getStudent()

    suspend fun insertStudent (student: Student){
        return repo.insertStudent(student)
    }

    suspend fun delteStudent (student : Student){
        return repo.deleteStudent(student)
    }

    fun insertAll (studentList : List<Student>){
        viewModelScope.launch {
            repo.insertAll(studentList)
        }
    }
}