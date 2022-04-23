package com.example.attendanceapp.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "student")
class Student (@PrimaryKey (autoGenerate = true) val id : Long,
               val name : String,
               val usn : String)