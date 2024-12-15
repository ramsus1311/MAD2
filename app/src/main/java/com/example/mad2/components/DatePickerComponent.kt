package com.example.mad2.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    onDateSelected: () -> Unit,
    dateRangePickerState: DateRangePickerState
) {
    Column(
        modifier = Modifier
            .border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(500.dp)
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = { Text("Select date range", fontSize = 16.sp, modifier = Modifier.padding(8.dp)) },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        HorizontalDivider(color = Color.Gray, thickness = 2.dp)
        Button(onClick = onDateSelected, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Confirm")
        }
    }
}