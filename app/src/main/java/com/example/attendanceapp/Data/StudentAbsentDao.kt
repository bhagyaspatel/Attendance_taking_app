package com.example.attendanceapp.Data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentAbsentDao {

    @Query("SELECT * FROM student ORDER BY usn ASC")
    fun getSetudent () : LiveData<MutableList<Student>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent (student: Student)

    @Delete
    suspend fun deleteStudent (student: Student)
}