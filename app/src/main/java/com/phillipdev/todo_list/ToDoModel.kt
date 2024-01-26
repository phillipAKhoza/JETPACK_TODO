package com.phillipdev.todo_list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.IOException

class ToDoModel: ViewModel() {
    private var fileModel = FileModel()

    private var todoList by mutableStateOf(listOf<String>())
    private lateinit var appContext:Context
    fun getTodoList(context: Context) :List<String>{
        appContext = context
        try {
            todoList = fileModel.readData(context)
        }catch (error:IOException){
            println(error.message)
        }
        return todoList
    }
    fun addTodo(todo:String){
        todoList = todoList + listOf(todo)
        fileModel.writeData(todoList, appContext)
    }
    fun removeTodo(todo: String){
        todoList = todoList.toMutableList().also { it.remove(todo) }
        fileModel.writeData(todoList, appContext)
    }
}