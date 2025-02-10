package com.alexander.tododaily.todotask.ui.home


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexander.tododaily.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import com.alexander.tododaily.todotask.navigation.Routes
import com.alexander.tododaily.todotask.ui.model.TaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController
) {
//    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AllDestinations.HOME
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val day: String = "Today"
    Scaffold(modifier = Modifier.background(Color(249, 245, 244)).windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopAppbarHome(
                dateTime = day,
                coroutineScope = coroutineScope,
                homeViewModel,
                navHostController
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                BottonAddTask(Modifier, homeViewModel)
            }
        }
    ) { PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(249, 245, 244))
                .padding(PaddingValues)

        ) {
//            HeadHome(day)
            Spacer(Modifier.padding(vertical = 10.dp))
            BodyHome(homeViewModel)


        }
    }
}


@Composable
fun BodyHome(homeViewModel: HomeViewModel) {
    val taskModels: List<TaskModel> by homeViewModel.tasks.collectAsState()
    LaunchedEffect(Unit) {
        homeViewModel.getTasks()
    }
    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        items(taskModels) { taskModel ->
            TaskField(taskModel, homeViewModel)
        }
    }
}


@Composable
fun TaskField(
    taskModel: TaskModel,
    homeViewModel: HomeViewModel
) {
    val isDialogShowDelete = homeViewModel.isDialogShowDelete.value
    val isDialogShowUpdate = homeViewModel.isDialogShowUpdate.value



    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(243, 239, 238)

        )
    ) {
        if (isDialogShowDelete) {
            AlertDialogConfir(
                "Borrar",
                "Estas Seguro?",
                { homeViewModel.onChangeShowDialogDele() },
                {
                    homeViewModel.deleteTask()
                    homeViewModel.onChangeShowDialogDele()
                })

        }
        if (isDialogShowUpdate) {
            AlertDialogConfir(
                title = "Actualizar",
                description = "Realizar cambios!",
                { homeViewModel.onChangeShowDialogUpdate() },
                {})

        }
        var expanded by remember { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = taskModel.isChecked,
                onCheckedChange = {
                    homeViewModel.onClickCheckedTask(taskModel)
                    homeViewModel.updateTask(taskModel)
                },
                colors = CheckboxDefaults.colors(

                    checkedColor = Color.LightGray,//- the color that will be used for the border and box when checked
                    uncheckedColor = Color.Black,//- color that will be used for the border when unchecked. By default, the inner box is transparent when unchecked.
                    checkmarkColor = Color.Black,//- color that will be used for the checkmark when checked
                    disabledCheckedColor = Color.LightGray
                )
            )
            TextField(
                enabled = false,
                value = taskModel.task,
                onValueChange = { },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(243, 239, 238),
                    focusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledTextColor = if (taskModel.isEnable) {


                        Color.Black
                    } else {


                        Color.Gray
                    },
                    disabledContainerColor = if (taskModel.isEnable) {
                        Color.LightGray
                    } else (Color(243, 239, 238)),
                    disabledIndicatorColor = Color.Transparent,

                    ),
                trailingIcon = {
                    if (taskModel.indicatorVisibility) {
                        IconButton(onClick = { expanded = true }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.drag_indicator_24dp),
                                contentDescription = "show password"
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            homeViewModel.menuOptions.forEach() {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = it.name, fontSize = 14.sp)

                                    },
                                    onClick = {
                                        expanded = !expanded

                                        if (it.id == 1) {
                                            homeViewModel.onChangeShowDialogUpdate()


                                        } else if (it.id == 2) {
                                            homeViewModel.onChangeShowDialogDele()
                                            homeViewModel.onChangeDeleteTask(taskModel)
                                        } else {
                                        }
                                    },

                                    )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
        }
    }
}


@Composable
fun BottonAddTask(modifier: Modifier, homeViewModel: HomeViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.padding(horizontal = 22.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by rememberSaveable { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Write a task") },
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(243, 239, 238),
                focusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.LightGray,
                disabledContainerColor = Color(243, 239, 238),
                disabledIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(),
            textStyle = TextStyle.Default.copy(fontSize = 18.sp)
        )

        Button(modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                keyboardController?.hide()
                homeViewModel.onClickAddTask(
                    task = text,
                )
                text = ""
            }) {
            Text(text = "Add")
        }
    }

}


@Composable
fun AlertDialogConfir(
    title: String = "Borrar",
    description: String = "Deseas Borrar esta tarea?",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = { Text(text = description) },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancelar")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppbarHome(

    dateTime: String,
    coroutineScope: CoroutineScope,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController
) {
    val isDialogShowDeleteAll = homeViewModel.isDialogShowDeleteAll.value
    TopAppBar(
        title = { Text(text = dateTime) },
        modifier = Modifier.fillMaxWidth(),//.nestedScroll(scrollBehavior.nestedScrollConnection),
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch { /*drawerState.open()*/ }
            }, content = {
                Icon(
                    imageVector = Icons.Default.Menu, contentDescription = null
                )
            })

        },
        actions = {
            IconButton(onClick = {
            }
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Calendar"
                )
            }
            IconButton(onClick = {
                homeViewModel.onChangeShowDialogDeleAll()


            }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "DeleteAll"
                )
            }
            IconButton(onClick = {
                homeViewModel.ChangeshowLogoutDialog()
            }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "SignOut"
                )
            }

        }

    )
    if (isDialogShowDeleteAll) {
        AlertDialogConfir(
            "Eliminar Seleccionados",
            "Estas Seguro?",
            onDismiss = { homeViewModel.onChangeShowDialogDeleAll() },
            onConfirm = {
                homeViewModel.deleteSelectedTasks()
                homeViewModel. onChangeShowDialogDeleAll()
            })

    }

    ShowLogOutDialog({
        homeViewModel.UserLogOut()
        homeViewModel.ChangeshowLogoutDialog()
        navHostController.navigate(Routes.login.route) {
            popUpTo(Routes.home.route) {
                inclusive = true
            }
        }
    }, { homeViewModel.ChangeshowLogoutDialog() }, homeViewModel.showLogOutDialog.value)
}


@Composable
fun ShowLogOutDialog(onConfirm: () -> Unit, onDismiss: () -> Unit, showDialog: Boolean) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Confirmar cierre de sesión") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                }) {
                    Text("Sí, salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancelar")
                }
            }
        )
    }
}