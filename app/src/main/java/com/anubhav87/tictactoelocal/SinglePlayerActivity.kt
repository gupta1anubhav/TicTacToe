package com.anubhav87.tictactoelocal

import android.graphics.Color
import android.graphics.ColorSpace
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import mehdi.sakout.fancybuttons.FancyButton
import java.util.*

class SinglePlayerActivity : AppCompatActivity() {
    var screen_width : Float = 0.0f
    var screen_height : Float = 0.0f
    var cells =  ArrayList<ImageView>()
    var Player1 = ArrayList<Int>();
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1
    var tvResult:TextView? = null
    lateinit var btneasy:FancyButton
    lateinit var btnmedium:FancyButton
    lateinit var btnhard:FancyButton
    lateinit var sharedPref:PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)
        tvResult = findViewById(R.id.tvResult)
        sharedPref = PrefManager(this)
        ActivePlayer = 1
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setListeners()
        if (getCurrentDiff().equals(0)){
            btneasy.setTextColor(Color.parseColor("#47ADFF"))
            btnmedium.setTextColor(Color.parseColor("#5A5A5A"))
            btnhard.setTextColor(Color.parseColor("#5A5A5A"))
        }
        else if(getCurrentDiff().equals(1)){
            btnmedium.setTextColor(Color.parseColor("#47ADFF"))
            btnhard.setTextColor(Color.parseColor("#5A5A5A"))
            btneasy.setTextColor(Color.parseColor("#5A5A5A"))
        }
        else{
            btnhard.setTextColor(Color.parseColor("#47ADFF"))
            btnmedium.setTextColor(Color.parseColor("#5A5A5A"))
            btneasy.setTextColor(Color.parseColor("#5A5A5A"))
        }
       // hideSystemUI()
        intitialsecells()

    }
    fun setListeners(){
        btneasy = findViewById(R.id.btneasy)
        btnmedium = findViewById(R.id.btnmedium)
        btnhard = findViewById(R.id.btnhard)
        btneasy.setOnClickListener{
            sharedPref.setDifficulty(0)
            btneasy.setTextColor(Color.parseColor("#47ADFF"))
            btnmedium.setTextColor(Color.parseColor("#5A5A5A"))
            btnhard.setTextColor(Color.parseColor("#5A5A5A"))
        }
        btnmedium.setOnClickListener{
            sharedPref.setDifficulty(1)
            btnmedium.setTextColor(Color.parseColor("#47ADFF"))
            btnhard.setTextColor(Color.parseColor("#5A5A5A"))
            btneasy.setTextColor(Color.parseColor("#5A5A5A"))
        }
        btnhard.setOnClickListener{
            sharedPref.setDifficulty(2)
            btnhard.setTextColor(Color.parseColor("#47ADFF"))
            btnmedium.setTextColor(Color.parseColor("#5A5A5A"))
            btneasy.setTextColor(Color.parseColor("#5A5A5A"))
        }

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
        var cellId: Int = 1
        // Get current selected difficulty
        var currDiff:Int = getCurrentDiff()
        // Use if conditon to get best move
        if (currDiff == 0){
            // Then its easy
            var easy:EasyAI = EasyAI()
            cellId = emptyCells.get(easy.Easy(emptyCells.size))
        }
        else if (currDiff == 1) {
            // Then its medium diff
            // create a linear board
            var board = CharArray(9)
            for (i in 0..Player1.size-1){
                board[Player1.get(i) - 1] = 'X'
            }
            for (i in 0..Player2.size-1){
                board[Player2.get(i) - 1] = 'O'
            }
            for(i in 0..8){
                if(board[i] != 'X' && board[i] != 'O'){
                    board[i] = '0'
                }
            }
            var mediumAi = MediumAi(board)
            cellId = mediumAi.getBestMove() + 1
            Log.d("TAG",cellId.toString())

        }
        else if (currDiff == 2){
            // Then its hard diff
                var xy:hardAi = hardAi()

                var ans:Int = xy.constructBoard(Player1,Player2)
                cellId = ans
                Log.d("Hard",cellId.toString())
        }


       // Toast.makeText(this,ans.toString(),Toast.LENGTH_SHORT).show()

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
    fun getCurrentDiff(): Int{
        var curDiff:Int = sharedPref.getDifficulty()
        return curDiff
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
        // Diagnol left
        if(Player1.contains(1) && Player1.contains(5) && Player1.contains(9)){
            winner = 1
        }
        if(Player2.contains(1) && Player2.contains(5) && Player2.contains(9)){
            winner = 2
        }
        // Diagnol Right
        if(Player1.contains(3) && Player1.contains(5) && Player1.contains(7)){
            winner = 1
        }
        if(Player2.contains(3) && Player2.contains(5) && Player2.contains(7)){
            winner = 2
        }

        //Todo use shared prefs for difficulty level
        //Todo add DialogBox for singleplayer for selecting who should use first move
        //Todo add three buttons and hardAi
        //Last Todo add animation to TicTacToe text

        if (winner != -1){

            if(winner == 1){
                //Toast.makeText(this,"Player 1 win", Toast.LENGTH_SHORT).show()
                tvResult?.setText("Cross wins !")
            }
            else{
                tvResult?.setText("Circle wins !")
                //Toast.makeText(this,"PLayer 2 win", Toast.LENGTH_SHORT).show()
            }
            for (i in 0..8){
                (this.cells.get(i)).setEnabled(false)
            }
            Player1.clear()
            Player2.clear()
            ActivePlayer = 1
            var x:Int = sharedPref.getGamesPlayed()
            x = x+1
            sharedPref.setGamesPlayed(x)
        }
        else{
            //Its a draw
            var emptyCells = ArrayList<Int>();
            for (cellId in 1..9){
                if(!(Player1.contains(cellId)|| Player2.contains(cellId))){
                    emptyCells.add(cellId)
                }
            }
            if (emptyCells.size.equals(0)){
                tvResult?.setText("It's A Draw !")
            }
        }

    }

    fun dpToPx(dp: Int): Float {
        val displayMetrics = this.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
    }

}
