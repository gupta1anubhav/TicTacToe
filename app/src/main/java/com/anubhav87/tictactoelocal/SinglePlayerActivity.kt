package com.anubhav87.tictactoelocal

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.anubhav87.tictactoelocal.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SinglePlayerActivity : AppCompatActivity() {
    var screen_width : Float = 0.0f
    var screen_height : Float = 0.0f
    var cells =  ArrayList<ImageView>()
    var Player1 = ArrayList<Int>();
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1
    var tvResult:TextView? = null
    lateinit var sharedPref:PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)
        tvResult = findViewById(R.id.tvResult)
        sharedPref = PrefManager(this)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // hideSystemUI()
        intitialsecells()

    }

    public fun ivbackclick(view: View){
       super.onBackPressed()
    }

    public fun ivrefreshclick(view: View){
        for (i in 0..8){
            val cell = cells.get(i)
            cell.setImageResource(android.R.color.transparent)
            (this.cells.get(i)).setEnabled(true)
        }

        tvResult?.setText("")
    }

    fun intitialsecells(){
        for (i in 0..8) {
            val cell = ImageView(this)
            cell.setLayoutParams(ViewGroup.LayoutParams(-2, -2))
            cell.setTag(Integer.valueOf(i))
            cell.id = 2000+i
            cell.setOnClickListener{view ->

                when (view.getId()) {
                    view.getId()->CELL_CLICK(view)
                }
            }
            cell.setClickable(true)
            (findViewById(R.id.frame) as ViewGroup).addView(cell)
            this.cells.add(cell)
            //cell.setOnTouchListener(C01212())
        }

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        screen_width = displayMetrics.widthPixels.toFloat()
        screen_height = displayMetrics.heightPixels.toFloat()
        val cell_size = (((this.screen_width) - dpToPx(20)) / 3.0f).toInt()
        (findViewById(R.id.frame) as ViewGroup).getLayoutParams().width = cell_size * 3
        (findViewById(R.id.frame) as ViewGroup).getLayoutParams().height = cell_size * 3
        var x_pos = 0
        var y_pos = 0
        for (i in 0..8) {
            //this.cells_values[i] = 0;
            (this.cells.get(i)).setEnabled(true);
            ( this.cells.get(i)).setImageResource(0);
            (this.cells.get(i)).getLayoutParams().width = cell_size;
            (this.cells.get(i)).getLayoutParams().height = cell_size;
            (this.cells.get(i)).setX( ((x_pos * cell_size).toFloat()));
            (this.cells.get(i)).setY( ((y_pos * cell_size).toFloat()));
            (this.cells.get(i)).setScaleX(1.0f);
            (this.cells.get(i)).setScaleY(1.0f);
            x_pos++;
            if (x_pos == 3) {
                x_pos = 0;
                y_pos++;
            }
        }
    }

    fun CELL_CLICK(v: View) {
        val buSelected = v as ImageView
        var cellId = 0;
        val buSelectedId = buSelected.id
        when(buSelectedId){
            cells.get(buSelectedId - 2000).id -> cellId = (buSelectedId - 1999)
        }
        //this.cells.get(buSelectedId - 2000).setImageResource(R.drawable.player2)
        PlayGame(cellId,buSelected)
        //Toast.makeText(this,"CellId "+cellId, Toast.LENGTH_SHORT).show()

    }

    fun PlayGame(cellId:Int, buSelected: ImageView){

        if(ActivePlayer == 1){
           // buSelected.text = "X"
            buSelected.setImageResource(R.drawable.player1)
           // buSelected.setBackgroundColor(Color.GREEN)
            Player1.add(cellId);
            ActivePlayer = 2;
            AutoPlay()
        }
        else if(ActivePlayer == 2){
            buSelected.setImageResource(R.drawable.player2)
           // buSelected.setBackgroundColor(Color.BLUE)
            ActivePlayer = 1
            Player2.add(cellId)
        }
        buSelected.isEnabled = false
        checkWinner()
    }

    fun AutoPlay(){

        var emptyCells = ArrayList<Int>();
        for (cellId in 1..9){
            if(!(Player1.contains(cellId)|| Player2.contains(cellId))){
                emptyCells.add(cellId)
            }

        }

        val r = Random()
        val randIndex = r.nextInt(emptyCells.size - 0) + 0
        val cellId = emptyCells.get(randIndex)
        var buSelected:ImageView?
        when(cellId){

            cellId -> buSelected = cells.get(cellId - 1)
            else ->{
                buSelected = cells.get(0)
            }

        }
       // Toast.makeText(this,"CellId "+cellId, Toast.LENGTH_SHORT).show()


        PlayGame(cellId,buSelected)
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
                //Toast.makeText(this,"Player 1 win", Toast.LENGTH_SHORT).show()
                tvResult?.setText("Cross wins !")
            }
            else{
                tvResult?.setText("Circle wins !")
                //Toast.makeText(this,"PLayer 2 win", Toast.LENGTH_SHORT).show()
            }
            Player1.clear()
            Player2.clear()
            ActivePlayer = 1
            var x:Int = sharedPref.getGamesPlayed()
            x = x+1
            sharedPref.setGamesPlayed(x)
        }
    }
    private fun hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        var mDecorView = window.decorView
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                        or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    fun dpToPx(dp: Int): Float {
        val displayMetrics = this.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
    }

}
