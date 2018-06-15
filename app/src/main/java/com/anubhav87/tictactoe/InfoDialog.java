package com.anubhav87.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoDialog extends Dialog {
    String libServices = "<a href='https://developer.android.com/topic/libraries/support-library/index.html'>Android Support: appcompat,support,design</a> <br>"
            + "<a href='https://github.com/medyo/Fancybuttons'>Fancybuttons</a> <br>"
            + "<a href='https://drive.google.com/file/d/1uhlmrwUYuAo5OVrXVORfAHLW5WZ997zF/view?usp=sharing'>Privacy Policy</a> <br>";

    public Activity c;
    public Dialog d;
    public PrefManager sharedPref;
    public TextView tvLibraries;
    public ImageView ivCancel,ivGithub,ivLinkedin;
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
        ivGithub = findViewById(R.id.ivGithub);
        ivLinkedin = findViewById(R.id.ivLinkedin);
        tvLibraries = findViewById(R.id.tvLibraries);
        tvLibraries.setClickable(true);
        tvLibraries.setMovementMethod(LinkMovementMethod.getInstance());
        tvLibraries.setText(Html.fromHtml(libServices));
        tvLibraries.setLinkTextColor(this.c.getResources().getColor(android.R.color.white));
        c.setFinishOnTouchOutside(true);
        ivCancel = findViewById(R.id.ivCancel);
        ivGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/gupta1anubhav"));
                c.startActivity(browserIntent);
            }
        });
        ivLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/anubhav-gupta-217a87121/"));
                c.startActivity(browserIntent);
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}