package com.anubhav87.tictactoe

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import mehdi.sakout.fancybuttons.FancyButton
import java.util.*



class SinglePlayerActivity : AppCompatActivity() {
    var screen_width : Float = 0.0f
    var screen_height : Float = 0.0f
    var cells =  ArrayList<ImageView>()
    var Player1 = ArrayList<Int>();
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1
    lateinit var tvResult:TextView
    lateinit var btneasy:FancyButton
    lateinit var btnmedium:FancyButton
    lateinit var btnhard:FancyButton
    lateinit var sharedPref:PrefManager
    lateinit var rlRefresh:RelativeLayout
    lateinit var btnRefresh:FancyButton
    lateinit var ivback:ImageView
    lateinit var ivshare:ImageView
    lateinit var storedView:View
    lateinit var parentll:LinearLayout
    lateinit var namebar:View
    lateinit var mediaPlayer1:MediaPlayer
    lateinit var mediaPlayer2:MediaPlayer
     var curDiff:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)
        tvResult = findViewById(R.id.tvResult)
        sharedPref = PrefManager(this)
        mediaPlayer1 = MediaPlayer.create(this,R.raw.soundc1)
        mediaPlayer2 = MediaPlayer.create(this,R.raw.soundc2)
        ActivePlayer = 1
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        curDiff = getCurrentDiff()
        redundant1()
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
        val handler = Handler()
        handler.postDelayed(Runnable {
            //Do something after 400ms
            AutoPlay()
        }, 300)

    }
    fun setListeners(){

        btneasy = findViewById(R.id.btneasy)
        btnmedium = findViewById(R.id.btnmedium)
        btnhard = findViewById(R.id.btnhard)
        rlRefresh = findViewById(R.id.rlRefresh)
        btnRefresh = findViewById(R.id.btnRefresh)
        ivback = findViewById(R.id.ivback)
        ivshare = findViewById(R.id.ivshare)
        parentll = findViewById(R.id.parentll)
        ivback.setOnClickListener{
            super.onBackPressed()
        }
        ivshare.setOnClickListener{
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Tic Tac Toe")
            var sAux = "\nPlay Tic Tac Toe on your Android phone. Our new modern version appears in a cool design.\n\n"
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.anubhav87.tictactoe \n"
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
            Player1.clear()
            Player2.clear()
            val handler = Handler()
            handler.postDelayed(Runnable {
                //Do something after 400ms
                AutoPlay()
            }, 300)
         //   setMargins(tvResult as View,0,20,0,0)
        }
        btneasy.setOnClickListener{
            curDiff = 0
            btneasy.setTextColor(Color.parseColor("#47ADFF"))
            btnmedium.setTextColor(Color.parseColor("#5A5A5A"))
            btnhard.setTextColor(Color.parseColor("#5A5A5A"))
        }
        btnmedium.setOnClickListener{
            curDiff = 1
            btnmedium.setTextColor(Color.parseColor("#47ADFF"))
            btnhard.setTextColor(Color.parseColor("#5A5A5A"))
            btneasy.setTextColor(Color.parseColor("#5A5A5A"))
        }
        btnhard.setOnClickListener{
            curDiff = 2
            btnhard.setTextColor(Color.parseColor("#47ADFF"))
            btnmedium.setTextColor(Color.parseColor("#5A5A5A"))
            btneasy.setTextColor(Color.parseColor("#5A5A5A"))
        }

    }


    fun intitialsecells(){
        for (i in 0..8) {
            val cell = ImageView(this)
            cell.setLayoutParams(ViewGroup.LayoutParams(-2, -2))
            cell.setTag(Integer.valueOf(i))
            cell.id = 2000+i

            cell.setOnClickListener{view ->
                if(sharedPref.getMusic())
                mediaPlayer1.start()
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
            ( this.cells.get(i)).setImageResource(0)
            (this.cells.get(i)).isSoundEffectsEnabled = false
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
                // buSelected.text = "X"
                buSelected.setImageResource(R.drawable.player1)
                // buSelected.setBackgroundColor(Color.GREEN)
                Player1.add(cellId);
                ActivePlayer = 2;
                val handler = Handler()
                handler.postDelayed(Runnable {
                    //Do something after 100ms
                    AutoPlay()
                }, 400)
               // AutoPlay()
            } else if (ActivePlayer == 2) {
                buSelected.setImageResource(R.drawable.player2)
                // buSelected.setBackgroundColor(Color.BLUE)
                ActivePlayer = 1
                Player2.add(cellId)
            }
            buSelected.isEnabled = false
            checkWinner()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun AutoPlay(){
        try {

            var cellId: Int = 1
            var emptyCells = ArrayList<Int>();
            for (cellId in 1..9) {
                if (!(Player1.contains(cellId) || Player2.contains(cellId))) {
                    emptyCells.add(cellId)
                }

            }
            if (sharedPref.isFirstMove() && curDiff.equals(1)) {
                var r: Random = Random()
                var x: Int = r.nextInt(15) + 1
                if (x > 9) {
                    x = 5
                }
                cellId = x
                sharedPref.setFirstMove(false)
                Log.d("Firstmove", cellId.toString())
            } else {

                // Get current selected difficulty
                var currDiff: Int = curDiff
                // Use if conditon to get best move
                if (currDiff == 0) {
                    // Then its easy
                    var easy: EasyAI = EasyAI()
                    cellId = emptyCells.get(easy.Easy(emptyCells.size))
                    Log.d("Easy", cellId.toString())
                } else if (currDiff == 1) {
                    // Then its medium diff
                    // create a linear board
                    var board = CharArray(9)
                    for (i in 0..Player1.size - 1) {
                        board[Player1.get(i) - 1] = 'X'
                    }
                    for (i in 0..Player2.size - 1) {
                        board[Player2.get(i) - 1] = 'O'
                    }
                    for (i in 0..8) {
                        if (board[i] != 'X' && board[i] != 'O') {
                            board[i] = '0'
                        }
                    }
                    var mediumAi = MediumAI(board, this)
                    cellId = mediumAi.getBestMove() + 1
                    Log.d("Medium", cellId.toString())

                } else if (currDiff == 2) {
                    // Then its hard diff
                    var xy: HardAI = HardAI()
                    var ans: Int = xy.constructBoard(Player1, Player2)
                    cellId = ans
                    Log.d("Hard", cellId.toString())
                }
            }


            // Toast.makeText(this,ans.toString(),Toast.LENGTH_SHORT).show()

            var buSelected: ImageView?
            when (cellId) {

                cellId -> buSelected = cells.get(cellId - 1)
                else -> {
                    buSelected = cells.get(0)
                }

            }
            // Toast.makeText(this,"CellId "+cellId, Toast.LENGTH_SHORT).show()
            if(sharedPref.getMusic())
            mediaPlayer2.start()
            ActivePlayer = 2
            PlayGame(cellId, buSelected)
        }
        catch (e:Exception){
            e.printStackTrace()
        }
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



        if (winner != -1){

            if(winner == 1){
                //Toast.makeText(this,"Player 1 win", Toast.LENGTH_SHORT).show()
                (namebar.getParent() as ViewGroup).removeView(namebar)
                tvResult?.setText("Cross wins !")
                redundant()
            }
            else{
                //Toast.makeText(this,"PLayer 2 win", Toast.LENGTH_SHORT).show()

                (namebar.getParent() as ViewGroup).removeView(namebar)
                tvResult?.setText("Circle wins !")
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
    fun redundant(){
        tvResult?.setTextSize(TypedValue.COMPLEX_UNIT_SP,30f)
        setMargins(tvResult as View,0,60,0,0)
        rlRefresh.visibility = View.VISIBLE
    }


    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
    fun redundant1(){

        namebar = findViewById(R.id.llDiff)
        storedView = namebar
    }

}
