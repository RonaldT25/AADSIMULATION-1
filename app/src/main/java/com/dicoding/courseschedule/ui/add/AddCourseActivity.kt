package com.dicoding.courseschedule.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener  {
    private lateinit var addCourseViewModel: AddCourseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        val factory = ListViewModelFactory.createFactory(this)
        addCourseViewModel =  ViewModelProvider(this,factory).get(AddCourseViewModel::class.java)
        val spinner: Spinner = findViewById(R.id.spinner_day)
        ArrayAdapter.createFromResource(
                this,
                R.array.day,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database
                val day : Spinner = findViewById(R.id.spinner_day)
                val courseName : TextInputEditText = findViewById(R.id.add_course_name)
                val lecturer : TextInputEditText = findViewById(R.id.add_lecturer)
                val note : TextInputEditText = findViewById(R.id.add_note)
                val startTime : TextView = findViewById(R.id.add_tv_start_time)
                val endTime : TextView = findViewById(R.id.add_tv_end_time)
                addCourseViewModel.insertCourse(courseName.text.toString(),dateConverter(day.selectedItem.toString()),startTime.text.toString(),endTime.text.toString(),lecturer.text.toString(),note.text.toString())
                val addIntent = Intent(this, HomeActivity::class.java)
                startActivity(addIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun showDatePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "timePicker")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        if (tag=="timePicker") {
            findViewById<TextView>(R.id.add_tv_start_time).text = dateFormat.format(calendar.time)
        }
        else if (tag=="timePicker2"){
            findViewById<TextView>(R.id.add_tv_end_time).text = dateFormat.format(calendar.time)
        }
    }

    fun showDatePicker2(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "timePicker2")
    }

    private fun dateConverter(day : String) : Int{
        if (day == "Monday"){
            return 1
        }
        if (day == "Tuesday"){
            return 2
        }
        if (day == "Wednesday"){
            return 3
        }
        if (day == "Thursday"){
            return 4
        }
        if (day == "Friday"){
            return 5
        }
        if (day == "Saturday"){
            return 6
        }
        if (day == "Sunday"){
            return 0
        }
        else{
            return 0
        }
    }




}