package com.example.attendanceapp.Data

import android.app.Application
import androidx.lifecycle.LiveData

class StudentRepository (context : Application) {
    private val studentAbsentDao : StudentAbsentDao = StudentDatabase.getDataBase(context).studentAbsentDao()

    suspend fun insertStudent (student: Student){
        return studentAbsentDao.insertStudent(student)
    }

    fun getStudent() : LiveData<MutableList<Student>>{
        return studentAbsentDao.getSetudent()
    }

    suspend fun deleteStudent (student: Student){
        return studentAbsentDao.deleteStudent(student)
    }

    suspend fun insertAll (studentList : List<Student>){
        return studentAbsentDao.insertAll(studentList)
    }
}