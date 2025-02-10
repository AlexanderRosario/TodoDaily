package com.alexander.tododaily.todotask.ui.IntroScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexander.tododaily.todotask.ui.model.HabitModel


@Composable
fun IntroScreen() {
    val habitList = IntroViewModel().habit
//    val groupedHabits = habitList.chunked(3)

    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
//                .fillMaxSize()
                .background(Color(249, 245, 244))
        ) {
            Headintro(Modifier.weight(0.3f).padding(paddingValues))
            BodyIntro(habitList)
            FootIntro(Modifier.padding(paddingValues))
        }

    }

}

@Composable
fun FootIntro(modifier: Modifier) {
    Button(modifier = modifier.fillMaxWidth().padding(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = {}) {
        Text(text = "Continue")
    }
}

@Composable
fun BodyIntro(habitList: List<HabitModel>) {
    val groupedHabits = habitList.chunked(3)

    Column(
        Modifier.padding(start = 36.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "RECOMMENDED")
        groupedHabits.forEach { group ->
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(group) { it ->
                    HabitText(text = it)

                }

            }
        }
    }
}


@Composable
fun HabitText(text: HabitModel) {
    Text(
        text = text.habit, modifier = Modifier
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(Color(243, 239, 238))

            .border(
                BorderStroke(color = Color.Transparent, width = 0.dp),
                shape = RoundedCornerShape(10.dp)
            )

            .padding(22.dp)

    )

}


@Composable
fun Headintro(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(start = 36.dp),
//        .padding(horizontal = 36.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Pick some new", fontSize = 32.sp)
        Text(text = "habits to get started", fontSize = 32.sp)
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewIntroScreen() {
    IntroScreen()

}

@Preview(showBackground = true)
@Composable
fun Previewtext() {
//    Box(modifier = Modifier.padding(22.dp).background(Color.LightGray)){
    Text(
        text = "Exercise", modifier = Modifier
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(Color.LightGray)

            .border(
                BorderStroke(color = Color.Transparent, width = 0.dp),
                shape = RoundedCornerShape(10.dp)
            )

            .padding(22.dp)

    )
//    }

}
