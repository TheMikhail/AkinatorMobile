package com.example.carakinatormobile.Repo

import com.example.carakinatormobile.Data.Question

interface QuestionRepository {
    fun getQuestion(): List<Question>
}