package com.example.carakinatormobile.Data

abstract class Question(val category: String) {
    abstract fun description(): String
    abstract fun checkCondition(answer: Boolean, car: Car): Boolean
}