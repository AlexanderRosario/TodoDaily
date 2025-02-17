package com.alexander.tododaily.todotask.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DatePickerModal(
//    onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit
//) {
//    val datePickerState = rememberDatePickerState()
//
//    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
//        TextButton(onClick = {
//
//            val selectedMillis = datePickerState.selectedDateMillis
//            if (selectedMillis != null) {
//                // Ajustar la fecha seleccionada a la zona horaria local
//                val correctedMillis = convertToLocalDateMillis(selectedMillis)
//                onDateSelected(correctedMillis)
//            }
//            onDismiss()
//        }) {
//            Text("OK")
//        }
//    }, dismissButton = {
//        TextButton(onClick = onDismiss) {
//            Text("Cancel")
//        }
//    }) {
//        DatePicker(state = datePickerState)
//    }
//}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}



@Preview
@Composable
fun PreviewDatePickerModal(){
    DatePickerModal({},{})
}