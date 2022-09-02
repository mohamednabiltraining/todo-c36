package com.route.todoc36.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.route.todoc36.database.dao.TaskDao


@Database( entities = [Task::class] , version = 1 )
@TypeConverters (DateConverter::class)
abstract class MyDataBase : RoomDatabase() {

    abstract fun getTasksDao() : TaskDao

    companion object {
        private val DATABASE_NAME = "Task-Data"
        private var myDatabase:MyDataBase?=null
        fun getInstance(context:Context):MyDataBase{
            if(myDatabase==null){
                myDatabase = Room.databaseBuilder(
                    context,MyDataBase::class.java,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return myDatabase!!
        }
    }

}


