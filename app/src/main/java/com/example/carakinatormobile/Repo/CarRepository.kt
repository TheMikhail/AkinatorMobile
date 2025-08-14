package com.example.carakinatormobile.Repo

import com.example.carakinatormobile.Data.Car

interface CarRepository{
    fun getCar(): List<Car>
}