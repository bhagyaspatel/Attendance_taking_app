package com.example.attendanceapp.ui.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.attendanceapp.ui.StudentViewModel

const val RESET_DATA = "RESET_DATA"
class AlarmReciever : BroadcastReceiver() {

    private lateinit var viewModel : StudentViewModel

    override fun onReceive(context : Context?, intent : Intent?) {
//        val intent = Intent (context, AlarmService::class.java).apply {
//            action = RESET_DATA
//        }

//        viewModel = ViewModelProvider(context).get(StudentViewModel::class.java)


    }
}