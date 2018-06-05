package com.anubhav87.tictactoelocal

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*

class TwoPlayerActivity : AppCompatActivity() {
    var Player1 = ArrayList<Int>();
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player)
    }
    public fun buClick(view: View){
        val buSelected = view as Button;
        var cellId = 0;
        when(buSelected.id){
            R.id.bu1->cellId = 1;
            R.id.bu2->cellId = 2;
            R.id.bu3->cellId = 3;
            R.id.bu4->cellId = 4;
            R.id.bu5->cellId = 5;
            R.id.bu6->cellId = 6;
            R.id.bu7->cellId = 7;
            R.id.bu8->cellId = 8;
            R.id.bu9->cellId = 9;
        }
        Toast.makeText(this,"CellId "+cellId, Toast.LENGTH_SHORT).show()
        PlayGame(cellId,buSelected)
    }
    fun PlayGame(cellId:Int, buSelected: Button){

        if(ActivePlayer == 1){
            buSelected.text = "X"
            buSelected.setBackgroundColor(Color.GREEN)
            Player1.add(cellId);
            ActivePlayer = 2;
          //  AutoPlay()
        }
        else if(ActivePlayer == 2){
            buSelected.text = "O"
            buSelected.setBackgroundColor(Color.BLUE)
            ActivePlayer = 1
            Player2.add(cellId)
        }
        buSelected.isEnabled = false
        checkWinner()
    }


    fun checkWinner(){
        var winner = -1

        // row 1
        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3)){
            winner = 1
        }
        if(Player2.contains(1) && Player2.contains(2) && Player2.contains(3)){
            winner = 2
        }

        // row 2
        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6)){
            winner = 1
        }
        if(Player2.contains(4) && Player2.contains(5) && Player2.contains(6)){
            winner = 2
        }
        // row 3
        if(Player1.contains(7) && Player1.contains(8) && Player1.contains(9)){
            winner = 1
        }
        if(Player2.contains(7) && Player2.contains(8) && Player2.contains(9)){
            winner = 2
        }
        // col 1
        if(Player1.contains(1) && Player1.contains(4) && Player1.contains(7)){
            winner = 1
        }
        if(Player2.contains(1) && Player2.contains(4) && Player2.contains(7)){
            winner = 2
        }
        // col 2
        if(Player1.contains(2) && Player1.contains(5) && Player1.contains(8)){
            winner = 1
        }
        if(Player2.contains(2) && Player2.contains(5) && Player2.contains(8)){
            winner = 2
        }
        // col 3
        if(Player1.contains(3) && Player1.contains(6) && Player1.contains(9)){
            winner = 1
        }
        if(Player2.contains(3) && Player2.contains(6) && Player2.contains(9)){
            winner = 2
        }

        if (winner != -1){

            if(winner == 1){
                Toast.makeText(this,"Player 1 win", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"PLayer 2 win", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
