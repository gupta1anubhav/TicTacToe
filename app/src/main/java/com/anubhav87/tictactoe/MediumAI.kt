package com.anubhav87.tictactoe

import android.content.Context
import android.util.Log

class MediumAI {
   lateinit var gameBoard:CharArray
    lateinit var context: Context
    var cellRank:IntArray = intArrayOf(3,2,3,2,4,2,3,2,3)
       constructor(board:CharArray,context:Context){
            this.gameBoard = board
            this.context = context
       }


    var winningCombos:Array<IntArray> =  arrayOf(intArrayOf(0,1,2),intArrayOf(3,4,5),intArrayOf(6,7,8),intArrayOf(0,3,6)
    ,intArrayOf(6,7,8),intArrayOf(0,3,6),intArrayOf(1,4,7),intArrayOf(2,5,8),intArrayOf(0,4,8),intArrayOf(2,4,6))

     fun  getBestMove():Int{

        // use a heuristic algorithm to determine the best play

        //initial rank based on number of winning combos
        //that go through the cell


        //demote any cells already taken
        for(i in 0..gameBoard.size-1){
        if(!(gameBoard[i].equals('0'))){
            cellRank[i] -= 99;
        } // end if
    } // end for

        //look for partially completed combos
        for(combo in 0..winningCombos.size-1){
        var a = winningCombos[combo][0];
        var b = winningCombos[combo][1];
        var c = winningCombos[combo][2];

        //if any two cells in a combo are
        //non-blank and the same value,
        //promote the remaining cell
        if(gameBoard[a] == gameBoard[b]){
            if(!(gameBoard[a].equals('0'))){
                if(gameBoard[c].equals('0')){
                    cellRank[c] += 10;
                } // end if
            } // end if
        } // end if

        if(gameBoard[a] == gameBoard[c]){
            if(!(gameBoard[a].equals('0'))){
                if(gameBoard[b].equals('0')){
                    cellRank[b] += 10;
                } // end if
            } // end if
        } // end if

        if(gameBoard[b] == gameBoard[c]){
            if(!(gameBoard[b].equals('0'))){
                if(gameBoard[a].equals('0')){
                    cellRank[a] += 10;
                } // end if
            } // end if
        } // end if
    } // end for

        //determine the best move to make
        var bestCell = -1;
        var highest = -999;

        //step through cellRank to find the best available score
        for(j in 0..gameBoard.size-1){
        if(cellRank[j] > highest){
            highest = cellRank[j];
            bestCell = j;
        } // end if
    } // end for
        Log.d("MediumAI", cellRank.contentToString())
        return bestCell

    }
}