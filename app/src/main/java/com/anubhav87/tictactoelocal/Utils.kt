package com.anubhav87.tictactoelocal

import android.graphics.Color
import android.widget.Button
import android.widget.Toast
import java.util.*

class Utils {

//Easy Difficulty AI

    companion object {
        fun Easy(x:Int):Int {
            val r = Random()
            val randIndex = r.nextInt(x - 0) + 0
            return randIndex
        }
        fun Medium(){

        }
    }



}