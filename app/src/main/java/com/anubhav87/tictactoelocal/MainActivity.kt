package com.anubhav87.tictactoelocal

//Todo player turn two player activity
//Todo Add tap sound on cell click
//Todo Add Info to app inside info activity
//Todo Create Image assets for all sizes
//Todo App icon using icon generator online
//Todo set Refresh button width to half of screen programmatically
//Todo Create mockups for app using mockup tool online
//Todo add DialogBox for singleplayer for selecting who should use first move
//Last Todo add animation to TicTacToe text
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import mehdi.sakout.fancybuttons.FancyButton
import android.graphics.Typeface



class MainActivity : AppCompatActivity() {
lateinit var btnSingle: FancyButton
lateinit var btnMulti:FancyButton
    lateinit var ivInfo: ImageView
    lateinit var ivSettings:ImageView
    lateinit var sharedPef:PrefManager
    lateinit var gamesPlayed: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // hideSystemUI()
        setListeners()
    }

    fun setListeners(){
        gamesPlayed = findViewById(R.id.gamesplayed)
        btnSingle = (findViewById(R.id.btnSingle)) as FancyButton
        btnMulti = (findViewById(R.id.btnMulti)) as FancyButton
        ivInfo = (findViewById(R.id.ivInfo)) as ImageView
        ivSettings = (findViewById(R.id.ivSettings)) as ImageView
        sharedPef = PrefManager(this)
        setFont()
        gamesPlayed.setText(sharedPef.getGamesPlayed().toString())
//        btnSingle.isSoundEffectsEnabled = false
//        btnMulti.isSoundEffectsEnabled = false
        btnSingle.setOnClickListener{
            sharedPef.setFirstMove(true)
            val intent = Intent(this, SinglePlayerActivity::class.java)
            startActivity(intent)
        }
        btnMulti.setOnClickListener{
            sharedPef.setFirstMove(true)
            val intent = Intent(this,TwoPlayerActivity::class.java)
            startActivity(intent)
        }
        ivInfo.setOnClickListener{
            val infoDialog:InfoDialog = InfoDialog(this)
            infoDialog.show()
        }
        ivSettings.setOnClickListener{
            val settingsDialog = SettingsDialog(this)
            settingsDialog.show()
        }
    }


    fun setFont(){
        val tx = findViewById(R.id.tv1) as TextView

        val custom_font = Typeface.createFromAsset(assets, "fonts/Raleway-SemiBold.ttf")

        tx.typeface = custom_font
        val ty = findViewById(R.id.tv2) as TextView

        ty.typeface = custom_font
        val tz = findViewById(R.id.tv3) as TextView

        tz.typeface = custom_font
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

    override fun onRestart() {
        super.onRestart()
        setListeners()
    }
}


