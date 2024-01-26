package com.phillipdev.todo_list

import android.content.Context
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileModel {

    val FILENAME = "todolist.dat"

    fun writeData(todolist: List<String>, context: Context){
        var fos: FileOutputStream =context.openFileOutput(FILENAME,Context.MODE_PRIVATE)

        var oas = ObjectOutputStream(fos)
        oas.writeObject(todolist)
        oas.close()
    }
    fun readData(context: Context): List<String>{
        var todoList: MutableList<String>
        var fis: FileInputStream = context.openFileInput(FILENAME)
        var ois = ObjectInputStream(fis)
        todoList= ois.readObject() as MutableList<String>

        return todoList
    }
}