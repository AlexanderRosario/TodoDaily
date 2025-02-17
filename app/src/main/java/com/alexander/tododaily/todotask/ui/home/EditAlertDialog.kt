package com.alexander.tododaily.todotask.ui.home

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import com.alexander.tododaily.todotask.ui.component.DateFormater
import com.alexander.tododaily.todotask.ui.component.DatePickerModal
import com.alexander.tododaily.todotask.ui.model.TaskModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun EditAlertDialog(
    modifier: Modifier = Modifier,
    taskModel: TaskModel,
    homeViewModel: HomeViewModel,
    onDismiss: () -> Unit = {},
    onConfirm: (title: String, description: String, alarmDate: Long) -> Unit,
) {
    val showModal = homeViewModel.isDialogDate.value
    val title : String by homeViewModel.title.observeAsState(initial = taskModel.task)
    val description: String by homeViewModel.description.observeAsState(initial = taskModel.description)

    val selectedDate: Long by homeViewModel.alarmDate.observeAsState(initial = taskModel.alarmDate ?:0L)

    AlertDialog(

        onDismissRequest = { onDismiss() }, title = { Text(text = "Actualizar") }, text = {
            Column {


                Text(
                    text = taskModel.todayDate.split(" ")[0],
                    Modifier.align(alignment = Alignment.End)
                )
                OutlinedTextField(
                    value = title,
//                    placegolder
                    onValueChange = { homeViewModel.onChangeTask(title = it,description=description, alarmDate = selectedDate) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { homeViewModel.onChangeTask(title = title,description = it , alarmDate = selectedDate) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = selectedDate?.let { convertMillisToDate(it) } ?: "",
//                    value = taskModel.alarmDate?.toString() ?: "",
                    onValueChange = { },
                    label = { Text("Alarm to remind") },
                    readOnly = true,
                    placeholder = { Text("MM/DD/YYYY") },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Select date")
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .pointerInput(selectedDate) {
                            awaitEachGesture {
                                // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                // in the Initial pass to observe events before the text field consumes them
                                // in the Main pass.
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    homeViewModel.onChangeShowDDate()
                                }
                            }
                        })
            }


        }, confirmButton = {
            TextButton(onClick = { onConfirm(title, description, selectedDate) }) {
                Text(text = "Confirmar")
            }
        }, dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancelar")
            }
        })
    if (showModal) {
        DatePickerModal(onDateSelected = {
            homeViewModel.onChangeTask(title = title,description = description , alarmDate = it)
//            selectedDate =
//                it


        }, onDismiss = {  homeViewModel.onChangeShowDDate() })
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    // Establecer la zona horaria a UTC para evitar el desfase
    formatter.timeZone = TimeZone.getTimeZone("UTC")

    // Crear un objeto Date con los milisegundos
    val date = Date(millis)

    // Convertir la fecha a formato de texto
    return formatter.format(date)
}


