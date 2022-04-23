package com.example.attendanceapp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentAbsentDao() : StudentAbsentDao

    companion object{
        @Volatile
        private var instance : StudentDatabase? = null

        fun getDataBase(context: Context) = instance ?: synchronized(this){
            Room.databaseBuilder(
                context.applicationContext,
                StudentDatabase::class.java,
                "movie_database"
            ).fallbackToDestructiveMigration().build().also { instance = it }
        }
    }
}