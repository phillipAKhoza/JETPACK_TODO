package com.phillipdev.todo_list

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.phillipdev.todo_list.ui.theme.TODO_LISTTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val todoViewModel by viewModels<ToDoModel>()
            TODO_LISTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val context: Context = applicationContext
                    todoApp(context = context, todoViewModel.getTodoList(context), addTodo = {todoViewModel.addTodo(it)}, removeTodo = {todoViewModel.removeTodo(it)})
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun todoApp(context: Context, todoItems: List<String>,addTodo:(String)-> Unit,removeTodo:(String)-> Unit ){

    var text by remember {
        mutableStateOf("")
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
                    addTodo(text)
                    text=""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Add")
            }

        }
        TodoList(todoList = todoItems,removeTodo)
    }
}
@Composable
fun TodoList(
    todoList: List<String>,
    removeTodo: (String) -> Unit,

    ) {

    LazyColumn{
        items(todoList) { todo ->
            todoItem(todo, removeTodo =  removeTodo )

            Divider()
        }
    }

}
@Composable
fun todoItem(todo: String, modifier: Modifier= Modifier, removeTodo:(String)-> Unit){
    var isClicked by remember {
        mutableStateOf(false)
    }
    Text(
        text = todo,
        modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clickable(
                onClick = {
                    isClicked = !isClicked
                }
            )
    )
    if (isClicked){
        AlertDialog(
            icon = {
                Image(painter = painterResource(id = R.drawable.warning) , contentDescription = "Example Icon")
            },
            title = {
                Text("Delete")
            },
            text = {
                Text("Confirm to Delete the TODO item?")
            },
            onDismissRequest = {
                isClicked = !isClicked
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        removeTodo(todo, )
                        isClicked=  !isClicked
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isClicked = !isClicked
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

}

