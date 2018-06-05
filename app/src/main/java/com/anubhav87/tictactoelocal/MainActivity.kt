package com.anubhav87.tictactoelocal


import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import mehdi.sakout.fancybuttons.FancyButton

class MainActivity : AppCompatActivity() {
lateinit var btnSingle:FancyButton
lateinit var btnMulti:FancyButton
    lateinit var ivGithub:ImageView
    lateinit var ivShare:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // hideSystemUI()
        setListeners()
    }

    fun setListeners(){
        btnSingle = (findViewById(R.id.btnSingle)) as FancyButton
        btnMulti = (findViewById(R.id.btnMulti)) as FancyButton
        ivGithub = (findViewById(R.id.ivGithub)) as ImageView
        ivShare = (findViewById(R.id.ivShare)) as ImageView

        btnSingle.setOnClickListener{
            val intent = Intent(this, SinglePlayerActivity::class.java)
            startActivity(intent)
        }
        btnMulti.setOnClickListener{
            val intent = Intent(this,SinglePlayerActivity::class.java)
            startActivity(intent)
        }
        ivGithub.setOnClickListener{
            val url = "https://github.com/gupta1anubhav/TicTacToe"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        ivShare.setOnClickListener{

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




    }


