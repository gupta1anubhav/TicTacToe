package com.anubhav87.tictactoe

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
    private val First_Move = "First Move"
    private val PREF_NAME = "Pref"
    private val NO_OF_GAMES_PLAYED = "Games played"
    private val SET_DIFFICULTY = "Difficulty"
    private val MUSIC = "MUSIC"

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
       editor.commit()
    }
    fun getGamesPlayed():Int{
        return pref.getInt(NO_OF_GAMES_PLAYED,0)
    }
    fun setDifficulty(x:Int){
        editor.putInt(SET_DIFFICULTY,x)
        editor.commit()
    }
    fun getDifficulty():Int{
        return pref.getInt(SET_DIFFICULTY,1)
    }
    fun setFirstMove(y:Boolean){
        editor.putBoolean(First_Move,y)
        editor.commit()
    }
    fun isFirstMove():Boolean{
        return pref.getBoolean(First_Move,true)
    }
    fun setMusic(y:Boolean){
        editor.putBoolean(MUSIC,y)
        editor.commit()
    }
    fun getMusic():Boolean{
        return pref.getBoolean(MUSIC,true)
    }




}