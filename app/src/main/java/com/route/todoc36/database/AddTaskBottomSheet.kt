package com.route.todoc36.database

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.route.todoc36.R
import java.util.*

class AddTaskBottomSheet : BottomSheetDialogFragment() {

    lateinit var titleLayout : TextInputLayout
    lateinit var detailsLayout : TextInputLayout
    lateinit var chooseDateText : TextView
    lateinit var addTask:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_task,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        titleLayout = requireView().findViewById(R.id.enter_task)
        detailsLayout = requireView().findViewById(R.id.details)
        addTask = requireView().findViewById(R.id.addbutton)
        chooseDateText = requireView().findViewById(R.id.date)
        chooseDateText.text= "${calender.get(Calendar.DAY_OF_MONTH)}/" +
        "${calender.get(Calendar.MONTH)+1}/" +
        "${calender.get(Calendar.YEAR)}"
        chooseDateText.setOnClickListener{
            showDatePicker()
        }
        addTask.setOnClickListener{
            if (validateForm()){
                val title = titleLayout.editText?.text.toString()
                val details = detailsLayout.editText?.text.toString()
                insertTask(title,details)
            }
        }
    }
    private fun insertTask(title: String, details: String) {

        val task = Task(title = title,desc =details, date = calender.time , isDone =  false)
        MyDataBase.getInstance(requireContext().applicationContext)
            .getTasksDao().insertTask(task)
        Toast.makeText(requireContext(),"Task added succesfully",Toast.LENGTH_LONG).show()
        dismiss()

    }
    val calender = Calendar.getInstance()
    @SuppressLint("SetTextI18n")
    private fun showDatePicker(){

        val datePicker = DatePickerDialog(
            requireContext()
            ,
            { p0, year, month, day ->
                calender.set(Calendar.DAY_OF_MONTH,day)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.YEAR,year)
                chooseDateText.text = "$day / ${month+1} / $year"

            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH) )
            datePicker.show()
    }
    private fun validateForm():Boolean{
        var isValid = true
        if(titleLayout.editText?.text.toString().isBlank()){
            titleLayout.error = "Please enter Task title"
            isValid =false
        }else{
            titleLayout.error = null
        }
        if(detailsLayout.editText?.text.toString().isBlank()){
            titleLayout.error = "Please enter Task details"
            isValid = false
        }else{
            detailsLayout.error = null
        }
        return isValid
    }


}