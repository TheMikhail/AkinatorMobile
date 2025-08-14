package com.example.carakinatormobile.Data

object QuestionGearboxManual : Question("com.example.carakinatormobile.Data.Gearbox") {
    override fun description(): String {
        return "Вы хотите машину на механике?"
    }

    override fun checkCondition(answer: Boolean, car: Car): Boolean
    {
        return (car.gearbox == Gearbox.Manual)== answer
    }
}