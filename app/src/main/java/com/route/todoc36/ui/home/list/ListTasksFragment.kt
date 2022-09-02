package com.route.todoc36.ui.home.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.route.todoc36.database.MyDataBase
import com.route.todoc36.database.Task
import com.route.todoc36.database.dao.EditTaskBottomSheet
import com.route.todoc36.databinding.FragmentTasksListBinding

class ListTasksFragment :Fragment() {

    lateinit var viewBinding: FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentTasksListBinding.inflate(layoutInflater,container,false);
        return viewBinding.root;
    }

    lateinit var adapter :TasksListAdapter
    lateinit var tasksList :List<Task>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksList = MyDataBase.getInstance(requireContext())
            .getTasksDao().selectAllTasks()
        adapter = TasksListAdapter(tasksList)
        adapter.onDeleteClickListener = object :TasksListAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, item: Task) {
                deleteTask(item)
                reloadTasks()
            }
        }
        adapter.editClickListener = object :TasksListAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, item: Task) {
                showBottomSheet()
            }

        }

        viewBinding.recyclerView.adapter = adapter

    }
    fun reloadTasks(){
        tasksList = MyDataBase.getInstance(requireContext())
            .getTasksDao()
            .selectAllTasks();
        adapter.reloadTasks(tasksList)
    }
    fun deleteTask(task:Task){
        MyDataBase.getInstance(requireContext())
            .getTasksDao()
            .deleteTask(task)
    }
    @SuppressLint("UseRequireInsteadOfGet")
    fun showBottomSheet(){
        val showBottomSheet = EditTaskBottomSheet()
        showBottomSheet.show(this@ListTasksFragment.activity!!.supportFragmentManager,"")
    }
}