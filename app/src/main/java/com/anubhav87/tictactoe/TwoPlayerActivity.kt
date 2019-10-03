package com.anubhav87.tictactoe

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import mehdi.sakout.fancybuttons.FancyButton
import java.util.*

class TwoPlayerActivity : AppCompatActivity() {
    var screen_width : Float = 0.0f
    var screen_height : Float = 0.0f
    var cells =  ArrayList<ImageView>()
    var Player1 = ArrayList<Int>();
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1
    lateinit var tvResult: TextView
    lateinit var sharedPref:PrefManager
    lateinit var rlRefresh: RelativeLayout
    lateinit var btnRefresh: FancyButton
    lateinit var ivback:ImageView
    lateinit var ivshare:ImageView
    lateinit var parentll: LinearLayout
    lateinit var ivPlayerTurn:ImageView
    lateinit var tvPlayerTurn:TextView
    lateinit var storedView:View
    lateinit var namebar:View
    lateinit var mediaPlayer1: MediaPlayer
    lateinit var mediaPlayer2: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player)
        ActivePlayer = 1
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mediaPlayer1 = MediaPlayer.create(this,R.raw.soundc1)
        mediaPlayer2 = MediaPlayer.create(this,R.raw.soundc2)
        redundant1()
        setListeners()
        intitialsecells()
    }
    fun setListeners(){
        parentll = findViewById(R.id.parentll)
        ivPlayerTurn = findViewById(R.id.ivPlayerTurn)
        tvPlayerTurn = findViewById(R.id.tvPlayerTurn)
        sharedPref = PrefManager(this)
        tvResult = findViewById(R.id.tvResult)
        rlRefresh = findViewById(R.id.rlRefresh)
        btnRefresh = findViewById(R.id.btnRefresh)
        ivback = findViewById(R.id.ivback)
        ivshare = findViewById(R.id.ivshare)
        ivback.setOnClickListener{
            super.onBackPressed()
        }
        ivshare.setOnClickListener{
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Tic Tac Toe")
            var sAux = "\nPlay Tic Tac Toe on your Android phone. Our new modern version appears in a cool design.\n"
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.anubhav87.tictactoe \n\n"
            i.putExtra(Intent.EXTRA_TEXT, sAux)
            startActivity(Intent.createChooser(i, "choose one"))
        }
        btnRefresh.setOnClickListener{
            for (i in 0..8){
                val cell = cells.get(i)
                cell.setImageResource(android.R.color.transparent)
                (this.cells.get(i)).setEnabled(true)
            }
            // val namebar:View = findViewById(R.id.llDiff)
            // (namebar.getParent() as ViewGroup).addView(namebar)
            if (storedView != null){
                tvResult.setTextSize(TypedValue.COMPLEX_UNIT_SP,0f)
                setMargins(tvResult as View,0,24,0,0)
                (parentll as ViewGroup).addView(storedView)
            }
            sharedPref.setFirstMove(true)
            rlRefresh.visibility = View.GONE
            tvResult.setText("")
            ivPlayerTurn.setImageResource(R.drawable.player1)
            //   setMargins(tvResult as View,0,20,0,0)
        }


    }


    fun intitialsecells(){
        for (i in 0..8) {
            val cell = ImageView(this)
            cell.setLayoutParams(ViewGroup.LayoutParams(-2, -2))
            cell.setTag(Integer.valueOf(i))
            cell.id = 2000+i
            cell.setOnClickListener{view ->
                if(sharedPref.getMusic() && ActivePlayer.equals(1))
                    mediaPlayer1.start()
                else if (sharedPref.getMusic() && ActivePlayer.equals(2))
                    mediaPlayer2.start()
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
        val cell_size = (((this.screen_width) - dpToPx(56)) / 3.0f).toInt()
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
        try {

            if (ActivePlayer == 1) {
                buSelected.setImageResource(R.drawable.player1)
                Player1.add(cellId);
                ActivePlayer = 2
                ivPlayerTurn.setImageResource(R.drawable.player2)
            } else if (ActivePlayer == 2) {
                buSelected.setImageResource(R.drawable.player2)
                ActivePlayer = 1
                ivPlayerTurn.setImageResource(R.drawable.player1)
                Player2.add(cellId)
            }
            buSelected.isEnabled = false
            checkWinner()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun checkWinner(){
        var winner = -1
        Log.d("TAG","Inside winnner")
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

        //Todo no. of GamesPlayed not working(MainActivity not updatig after back button)
        //Todo addViews Programmatically
        //Todo add DialogBox for singleplayer for selecting who should use first move
        //Last Todo add animation to TicTacToe text

        if (winner != -1){

            if(winner == 1){
                //Toast.makeText(this,"Player 1 win", Toast.LENGTH_SHORT).show()
                (namebar.getParent() as ViewGroup).removeView(namebar)
                tvResult.setText("Cross wins !")
                redundant()

            }
            else{
                //Toast.makeText(this,"PLayer 2 win", Toast.LENGTH_SHORT).show()
                (namebar.getParent() as ViewGroup).removeView(namebar)
                tvResult.setText("Circle wins !")
                redundant()
            }
            for (i in 0..8){
                (this.cells.get(i)).setEnabled(false)
            }
            val mediaPlayer:MediaPlayer = MediaPlayer.create(this,R.raw.soundend)
            if(sharedPref.getMusic())
                mediaPlayer.start()
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
                (namebar.getParent() as ViewGroup).removeView(namebar)
                tvResult?.setText("Draw !")
                val mediaPlayer:MediaPlayer = MediaPlayer.create(this,R.raw.soundend)
                if(sharedPref.getMusic())
                    mediaPlayer.start()
                redundant()
                Player1.clear()
                Player2.clear()
                ActivePlayer = 1
                var x:Int = sharedPref.getGamesPlayed()
                x = x+1
                sharedPref.setGamesPlayed(x)
            }
        }

    }
    fun dpToPx(dp: Int): Float {
        val displayMetrics = this.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
    }
    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
    fun redundant(){
        tvResult.setTextSize(TypedValue.COMPLEX_UNIT_SP,30f)
        setMargins(tvResult as View,0,60,0,0)
        rlRefresh.visibility = View.VISIBLE
    }
    fun redundant1(){

        namebar = findViewById(R.id.llPlayerTurn)
        storedView = namebar
    }
}
