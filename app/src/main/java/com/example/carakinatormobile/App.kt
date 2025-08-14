package com.example.carakinatormobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carakinatormobile.Data.Car
import com.example.carakinatormobile.Data.Question
import com.example.carakinatormobile.Repo.CarRepositoryClass
import com.example.carakinatormobile.Repo.QuestionRepositoryClass
import com.example.carakinatormobile.Screens.AkinatorScreen
import com.example.carakinatormobile.Screens.MainScreen
import com.example.carakinatormobile.ViewModel.AkinatorViewState

@Composable
fun AkinatorApp(){
    val viewModel: AkinatorViewState = viewModel()
    //AkinatorScreen нужно вызывать тут
    AkinatorScreen( ) { }
}