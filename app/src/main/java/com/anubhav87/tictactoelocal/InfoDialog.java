package com.anubhav87.tictactoelocal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.ImageView;
import android.widget.TextView;

public class InfoDialog extends Dialog{
    String libServices = "<a href='https://developer.android.com/topic/libraries/support-library/index.html'>Android Support: appcompat,support,design</a> <br>"
            + "<a href='https://github.com/medyo/Fancybuttons'>Fancybuttons</a> <br>"
            + "<a href='https://drive.google.com/open?id=1ph74fREwycQbRq2lgIc5-cVOZurRHJF0'>Privacy Policy</a> <br>";

    public Activity c;
    public Dialog d;
    public PrefManager sharedPref;
    public TextView tvLibraries;
    public ImageView ivCancel;
    public InfoDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.infodialog);
        sharedPref = new PrefManager(this.c);
        tvLibraries = findViewById(R.id.tvLibraries);
        tvLibraries.setClickable(true);
        tvLibraries.setMovementMethod(LinkMovementMethod.getInstance());
        tvLibraries.setText(Html.fromHtml(libServices));
        tvLibraries.setLinkTextColor(this.c.getResources().getColor(android.R.color.white));
        c.setFinishOnTouchOutside(true);
        ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
