package com.example.attendanceapp.ui.alarmManager

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import com.example.attendanceapp.ui.StudentViewModel
import com.example.attendanceapp.ui.list.StudentListFragment

class AlarmService : Service() {

    private lateinit var viewModel : StudentViewModel

    override fun onCreate() {
        super.onCreate()

//        viewModel = ViewModelProvider(this).get(StudentViewModel::class.java)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action == RESET_DATA){
            resetList()
        }

        return START_STICKY //// ***
    }

    private fun resetList() {
        ///???? how do i reset my list here ?
        //empty the viewModel

    }

}