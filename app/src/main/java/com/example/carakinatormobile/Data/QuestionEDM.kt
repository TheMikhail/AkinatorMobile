package com.example.carakinatormobile.Data

object QuestionEDM : Question("Country"){
   override fun description(): String {
        return "Вы хотите Европейскую машину?"
    }

    override fun checkCondition(answer: Boolean, car: Car): Boolean
    {
        return (car.market == Market.EDM) == answer
    }
}