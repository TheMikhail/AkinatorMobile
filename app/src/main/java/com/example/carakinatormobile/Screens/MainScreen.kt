package com.example.carakinatormobile.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carakinatormobile.Data.Car
import com.example.carakinatormobile.Data.Question
import com.example.carakinatormobile.Repo.CarRepositoryClass
import com.example.carakinatormobile.Repo.QuestionRepositoryClass
import com.example.carakinatormobile.ViewModel.AkinatorViewState

@Composable
fun QuestionScreen(question: Question, onAnswer: (Boolean) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(bottom = 35.dp), verticalArrangement = Arrangement.Bottom) {
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
@Composable
fun ResultView(cars:List<Car>){
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (cars.size) {
            0 -> Text(text = "Вам не подходит ни одна существующая машина")
            1 -> Text(text = "Ваша машина ${cars.single().name}")
            else -> Text(
                text = "Вам подходят следующие авто: ${
                    cars.joinToString(
                        prefix = "Вам подходят авто: ",
                        transform = { car -> car.name })
                }"
            )
        }
    }
}
@Composable
fun AkinatorScreen(
    state: AkinatorViewState,
    onAnswer: (Boolean) -> Unit
){
    when (state) {
        is AkinatorViewState.QuestionState -> QuestionScreen(
            question = state.currentQuestion,
            onAnswer = onAnswer
        )
        is AkinatorViewState.Result -> ResultView(cars = state.cars)
        is AkinatorViewState.Error ->{
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.message)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        else -> {}
    }
}
object SelectNewQuestion {
    val question = QuestionRepositoryClass().getQuestion().toMutableList()

    fun getNextQuestion(): Question? {
        if (question.isEmpty())
            return null
        else {
            val randomQuestion = question.random()
            question.removeAll{it.category == randomQuestion.category}
            return randomQuestion
        }
    }
}
@Composable
fun Application() {
    val car = CarRepositoryClass()
    val filteredCars = remember { mutableStateOf(car.getCar()) }
    val currentQuestion = remember { mutableStateOf(SelectNewQuestion.getNextQuestion()) }
    val question = currentQuestion.value

    if (question != null) {
        QuestionScreen(question, onAnswer = { answer ->
            filteredCars.value = filteredCars.value.filterByAnswer(question, answer)
            currentQuestion.value = SelectNewQuestion.getNextQuestion()
        })
    } else {
        ResultView(cars = filteredCars.value)

    }
}

fun List<Car>.filterByAnswer(currentQuestion: Question, answer: Boolean) =
    filter { car -> currentQuestion.checkCondition(answer, car) }