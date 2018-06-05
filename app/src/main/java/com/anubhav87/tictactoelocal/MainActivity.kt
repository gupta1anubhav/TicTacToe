package com.anubhav87.tictactoelocal


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.collections.ArrayList
import android.view.ViewGroup
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.view.ViewTreeObserver
import android.widget.*
import android.util.DisplayMetrics

import android.widget.Toast
import android.widget.LinearLayout






class MainActivity : AppCompatActivity() {
    var screen_width : Float = 0.0f
    var screen_height : Float = 0.0f
    var cells =  ArrayList<ImageView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSystemUI()
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

    fun dpToPx(dp: Int): Float {
        val displayMetrics = this.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
    }

    fun CELL_CLICK(v: View) {
         val buSelected = v as ImageView
         val buSelectedId = buSelected.id
        when(buSelectedId){
            cells.get(buSelectedId - 2000).id -> Toast.makeText(this,"CellId "+(buSelected.toString()), Toast.LENGTH_SHORT).show()
        }
        this.cells.get(buSelectedId - 2000).setImageResource(R.drawable.player2)

        //Toast.makeText(this,"CellId "+cellId, Toast.LENGTH_SHORT).show()

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


