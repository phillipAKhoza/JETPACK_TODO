package com.phillipdev.todo_list

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phillipdev.todo_list.ui.theme.TODO_LISTTheme
import java.io.IOException

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODO_LISTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val context: Context = applicationContext
                    //    instance of the file controller
                    var fileController = FileController()
//    to do input variable
                    var text by remember {
                        mutableStateOf("")
                    }
//    to do array
                    var todoItems by remember {
                        mutableStateOf(listOf<String>())
                    }
//                    read data from file
                        try {
                            todoItems = fileController.readData(applicationContext)
                        }catch (e : IOException){
                            println(e.message)
                        }




                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(modifier= Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth()){
                            OutlinedTextField(
                                value = text,
                                onValueChange = {text = it},
                                label = { Text("Item To Do")},
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                onClick = {
//                                    null check, write input to array and to file then rest textField
                                    if (text.isNotBlank()) {
                                        todoItems += text
                                        fileController.writeData(todoItems,context)
                                        text=""
                                    }
                                },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Text("Add")
                            }

                        }
                        TodoList(todoList = todoItems)
                    }
                }
            }
        }
    }
}

@Composable
fun TodoList(
    todoList: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn{
        items(todoList) { todo ->
            Text(
                text = todo,
                modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            )
            Divider()
        }
    }

}