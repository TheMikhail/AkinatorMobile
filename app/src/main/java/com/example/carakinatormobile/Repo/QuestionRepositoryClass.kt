package com.example.carakinatormobile.Repo

import com.example.carakinatormobile.Data.Question
import com.example.carakinatormobile.Data.QuestionEDM
import com.example.carakinatormobile.Data.QuestionGearboxAutomatic
import com.example.carakinatormobile.Data.QuestionGearboxManual
import com.example.carakinatormobile.Data.QuestionJDM
import com.example.carakinatormobile.Data.QuestionRDM
import com.example.carakinatormobile.Data.QuestionUSDM

class QuestionRepositoryClass : QuestionRepository {

    private val allQuestion: List<Question> = listOf(
        QuestionJDM, QuestionEDM, QuestionUSDM,
        QuestionRDM, QuestionGearboxAutomatic, QuestionGearboxManual
    )

    override fun getQuestion(): List<Question> {
        return allQuestion
    }
}