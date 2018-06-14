package com.anubhav87.tictactoelocal

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.view.Window

import android.widget.ImageView
import android.widget.TextView

class InfoDialoG(var c: Activity)// TODO Auto-generated constructor stub
    : Dialog(c) {
    internal var libServices = ("<a href='https://developer.android.com/topic/libraries/support-library/index.html'>Android Support: appcompat,support,design</a> <br>"
            + "<a href='https://github.com/medyo/Fancybuttons'>Fancybuttons</a> <br>"
            + "<a href='https://drive.google.com/open?id=1ph74fREwycQbRq2lgIc5-cVOZurRHJF0'>Privacy Policy</a> <br>")
    var d: Dialog? = null
    lateinit var sharedPref: PrefManager
    lateinit var tvLibraries: TextView
    lateinit var ivCancel: ImageView
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.infodialog)
        sharedPref = PrefManager(this.c)
        tvLibraries = findViewById(R.id.tvLibraries)
        tvLibraries.isClickable = true
        tvLibraries.movementMethod = LinkMovementMethod.getInstance()
        tvLibraries.text = Html.fromHtml(libServices)
        tvLibraries.setLinkTextColor(this.c.resources.getColor(android.R.color.white))
        c.setFinishOnTouchOutside(true)
        ivCancel = findViewById(R.id.ivCancel)
        ivCancel.setOnClickListener { dismiss() }
    }
}
