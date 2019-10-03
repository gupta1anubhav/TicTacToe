package com.anubhav87.tictactoe

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
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import mehdi.sakout.fancybuttons.FancyButton
import android.graphics.Typeface
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {
lateinit var btnSingle: FancyButton
lateinit var btnMulti:FancyButton
    lateinit var ivInfo: ImageView
    lateinit var ivSettings:ImageView
    lateinit var sharedPef:PrefManager
    lateinit var gamesPlayed: TextView
    lateinit var l1:LinearLayout
    lateinit var l2:LinearLayout
    lateinit var uptodown:Animation
    lateinit var downtoup:Animation
    lateinit var l3:LinearLayout
    lateinit var l4:RelativeLayout
    lateinit var l5:ImageView
    private var mInterstitialAd: InterstitialAd? = null
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
        l3 = findViewById(R.id.l3)
        l4 = findViewById(R.id.l4)
        l5 = findViewById(R.id.l5)
        l1 = findViewById(R.id.ll1)
        l2 = findViewById(R.id.ll2)
        uptodown = AnimationUtils.loadAnimation(this,R.anim.abc_grow_fade_in_from_bottom)
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup)
       // fadeIn = AnimationUtils.loadAnimation(this,R.anim.abc_fade_in)
        l1.animation = uptodown
        l2.animation = downtoup
        setFont()

        gamesPlayed.setText(sharedPef.getGamesPlayed().toString())

        btnSingle.setOnClickListener{
            mInterstitialAd?.let {
                if (it.isLoaded) {
                    it.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
            }
            sharedPef.setFirstMove(true)
            val intent = Intent(this, SinglePlayerActivity::class.java)
            startActivity(intent)
        }
        btnMulti.setOnClickListener{
            mInterstitialAd?.let {
                if (it.isLoaded) {
                    it.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
            }
            sharedPef.setFirstMove(true)
            val intent = Intent(this,TwoPlayerActivity::class.java)
            startActivity(intent)
        }
        ivInfo.setOnClickListener{
            mInterstitialAd?.let {
                if (it.isLoaded) {
                    it.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
            }
            val infoDialog:InfoDialog = InfoDialog(this)
            infoDialog.show()
        }
        ivSettings.setOnClickListener{
            mInterstitialAd?.let {
                if (it.isLoaded) {
                    it.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
            }
            val settingsDialog = SettingsDialog(this)
            settingsDialog.show()
        }
        MobileAds.initialize(this,
                "ca-app-pub-1587436717199769~2220155849")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd?.adUnitId = "ca-app-pub-1587436717199769/7348020136"
        mInterstitialAd?.loadAd(AdRequest.Builder().build())

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


