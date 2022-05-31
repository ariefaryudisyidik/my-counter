package com.mycounter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mycounter.databinding.ActivityDoneBinding
import java.text.SimpleDateFormat
import java.util.*

class DoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDateTime()
    }

    private fun showDateTime() {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy | HH:mm", Locale.getDefault())
        val date = dateFormat.format(Date())
        binding.tvDateTime.text = StringBuilder("$date WIB")
    }
}