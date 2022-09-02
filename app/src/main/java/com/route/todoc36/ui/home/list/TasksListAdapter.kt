package com.route.todoc36.ui.home.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources

import androidx.recyclerview.widget.RecyclerView
import com.route.todoc36.R
import com.route.todoc36.database.MyDataBase
import com.route.todoc36.database.Task
import com.route.todoc36.databinding.ItemTaskBinding
import com.zerobranch.layout.SwipeLayout

class TasksListAdapter (var items:List<Task>):RecyclerView.Adapter<TasksListAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.title.text = items[position].title
        holder.viewBinding.description.text = items[position].desc
        holder.viewBinding.delete.setOnClickListener{
            onDeleteClickListener?.onItemClick(position,items[position])
        }
        holder.viewBinding.swipeLayout
            .setOnActionsListener(object :SwipeLayout.SwipeActionsListener{
                override fun onClose() {
                }

                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if(direction == SwipeLayout.RIGHT){

                    }else if(direction==SwipeLayout.LEFT){
                    }
                }
            })

        holder.viewBinding.markDone.setOnClickListener {


            holder.viewBinding.markDone.setImageResource(R.drawable.double_tick)
            holder.viewBinding.markDone.setBackgroundResource(R.color.transparent)
            holder.viewBinding.title.setTextColor(Color.parseColor("#61E757"))
            holder.viewBinding.verticalLine.setBackgroundResource(R.drawable.check_done)
            holder.viewBinding.description.setTextColor(Color.parseColor("#61E757"))

        }
        holder.viewBinding.title.setOnClickListener {
            editClickListener?.onItemClick(position,items[position])
        }
    }
    var editClickListener:OnItemClickListener? = null
    var onDeleteClickListener:OnItemClickListener? =null
    interface OnItemClickListener{
        fun onItemClick(pos:Int,item:Task)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadTasks(newTasks:List<Task>){
        items = newTasks
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int = items.size

    class ViewHolder(val viewBinding:ItemTaskBinding):RecyclerView.ViewHolder(viewBinding.root)
}




