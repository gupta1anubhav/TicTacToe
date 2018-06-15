package com.anubhav87.tictactoe

import java.util.*

class EasyAI {

    fun EasyAI(){

    }

    fun Easy(x:Int):Int {
        val r = Random()
        val randIndex = r.nextInt(x - 0) + 0
        return randIndex
    }
}