package com.route.todoc36.database.dao

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.route.todoc36.R
import com.route.todoc36.database.MyDataBase
import com.route.todoc36.database.Task
import java.util.*

class EditTaskBottomSheet : BottomSheetDialogFragment() {

    lateinit var editTitle:TextInputLayout
    lateinit var editDesc:TextInputLayout
    lateinit var editDate:TextView
    lateinit var saveButton:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_task,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        editTitle = requireView().findViewById(R.id.edit_title)
        editDesc = requireView().findViewById(R.id.edit_details)
        editDate = requireView().findViewById(R.id.chooseDate)
        saveButton = requireView().findViewById(R.id.save_changes)

        editDate.setOnClickListener {
            showDatePicker()
        }

        saveButton.setOnClickListener{
            if(validateForm()) {
                val newTitle = editTitle.editText!!.text
                val newDesc = editDesc.editText!!.text

                updateTask(newTitle.toString(), newDesc.toString())
            }


        }


    }


    private fun updateTask(title: String, details: String) {

        val task = Task(title = title,desc =details, date = calender.time , isDone =  false)
        MyDataBase.getInstance(requireContext().applicationContext)
            .getTasksDao().updateTask(task)
        Toast.makeText(requireContext(),"Task updated succesfully", Toast.LENGTH_LONG).show()
        dismiss()

    }

    fun validateForm():Boolean{
        var isValid =  true
        if (editTitle.editText?.text!!.isEmpty()){
            editTitle.error = "No changes happened!"
            isValid = false
        }else{
            editTitle.error = null
        }
        if (editDesc.editText?.text!!.isEmpty()){
            editDesc.error = "No changes happened!"
            isValid =  false
        }else{
            editDesc.error = null
        }
        return isValid

    }
    val calender = Calendar.getInstance()
    fun showDatePicker(){
        val datePicker = DatePickerDialog(
            requireContext(), { p0, year, month, day ->
                calender.set(Calendar.DAY_OF_MONTH,day)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.YEAR,year)
                editDate.text = "$day / ${month+1} / $year"
            },calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH) )
        datePicker.show()

    }
}