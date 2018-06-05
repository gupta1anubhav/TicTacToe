package com.anubhav87.tictactoelocal

import android.content.Context
import android.content.SharedPreferences

class PrefManager {
    private val IS_FIRST_TIME_LAUNCH = "IS"
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var context: Context

    // shared pref mode
    internal var PRIVATE_MODE = 0

    // Shared preferences file name
    private val PREF_NAME = "Pref"
    private val NO_OF_GAMES_PLAYED = "Games played"

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }

    fun setGamesPlayed(x:Int){
        editor.putInt(NO_OF_GAMES_PLAYED,x)
        editor = pref.edit()
    }
    fun getGamesPlayed():Int{
        return pref.getInt(NO_OF_GAMES_PLAYED,0)
    }




}