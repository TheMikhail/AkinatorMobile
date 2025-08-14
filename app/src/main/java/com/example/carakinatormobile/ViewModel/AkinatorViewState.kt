package com.example.carakinatormobile.ViewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.carakinatormobile.Data.Car
import com.example.carakinatormobile.Data.Question
import com.example.carakinatormobile.Repo.CarRepositoryClass
import com.example.carakinatormobile.Repo.QuestionRepositoryClass
import com.example.carakinatormobile.Screens.QuestionScreen
import com.example.carakinatormobile.Screens.ResultView

class AkinatorViewModel : ViewModel(){

    var akinatorState: AkinatorViewState by mutableStateOf(AkinatorViewState.Loading)
        private set

    init {

    }



}
sealed class AkinatorViewState {
    object Loading : AkinatorViewState()
    data class QuestionState(val currentQuestion: Question): AkinatorViewState()
    data class Result(val cars: List<Car>): AkinatorViewState()
    data class Error(val message:String): AkinatorViewState()
}