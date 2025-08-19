package com.example.carakinatormobile

import AkinatorViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carakinatormobile.Data.Car
import com.example.carakinatormobile.Data.Question
import com.example.carakinatormobile.ui.theme.CarAkinatorMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarAkinatorMobileTheme {
                val viewModel: AkinatorViewModel = viewModel()
                Application(viewModel)

            }
        }
    }
}

@Composable
fun Application(viewModel: AkinatorViewModel) {
    val filteredCars by viewModel.filteredCars.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()

    if (currentQuestion != null) {
        QuestionItem(question = currentQuestion!!, onAnswer = { answer ->
            viewModel.answerQuestion(answer)
        })
    } else {
        ResultScreen(filteredCars = filteredCars, onRestart = { viewModel.resetGame() })
    }

}

@Composable
fun ResultScreen(filteredCars: List<Car>, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (filteredCars.size) {
            0 -> Text(text = "Вам не подходит ни одна существующая машина")
            1 -> Text(text = "Ваша машина ${filteredCars.single().name}")
            else -> Text(
                text = filteredCars.joinToString(
                    prefix = "Вам подходят авто: ",
                    transform = { car -> car.name }
                )
            )
        }

        Button(
            onClick = onRestart,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Начать заново")
        }
    }
}

fun List<Car>.filterByAnswer(currentQuestion: Question, answer: Boolean) =
    filter { car -> currentQuestion.checkCondition(answer, car) }

@Composable
fun QuestionItem(question: Question, onAnswer: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 35.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = {
                onAnswer(true)
            }
            ) {
                Text(text = "Да", fontSize = 32.sp)
            }
            TextButton(onClick = {
                onAnswer(false)
            }) {
                Text(text = "Нет", fontSize = 32.sp)
            }

        }
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = question.description())
        }
    }

}