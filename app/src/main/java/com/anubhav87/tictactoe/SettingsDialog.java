package com.anubhav87.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

public class SettingsDialog extends Dialog{
    public Activity c;
    public Dialog d;
    public PrefManager sharedPref;
    public CheckBox cbSound,cbEasy,cbMedium,cbHard;
    public ImageView ivCancel,ivShare,ivProfile,ivRate;
    public SettingsDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settingsdialog);
        sharedPref = new PrefManager(this.c);
        cbSound = findViewById(R.id.cbSound);
        cbEasy = findViewById(R.id.cbEasy);
        cbMedium = findViewById(R.id.cbMedium);
        ivShare = findViewById(R.id.ivShare);
        ivProfile = findViewById(R.id.ivProfile);
        ivRate = findViewById(R.id.ivRate);
        cbHard = findViewById(R.id.cbHard);
        ivCancel = findViewById(R.id.ivCancel);
        cbSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.d("TAG","On");
                    sharedPref.setMusic(true);
                }
                else {
                    Log.d("TAG","Off");
                    sharedPref.setMusic(false);
                }
            }
        });
        cbEasy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbMedium.setChecked(false);
                    cbHard.setChecked(false);
                    sharedPref.setDifficulty(0);
                }
                else {
                    if ((!cbMedium.isChecked()) && (!cbHard.isChecked())) {
                        cbEasy.setChecked(true);
                        sharedPref.setDifficulty(0);
                    }
                }
            }
        });
        cbMedium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbEasy.setChecked(false);
                    cbHard.setChecked(false);
                    sharedPref.setDifficulty(1);
                }
                else {
                    if ((!cbEasy.isChecked()) && (!cbHard.isChecked())) {
                        cbMedium.setChecked(true);
                        sharedPref.setDifficulty(1);
                    }
                }
            }
        });
        cbHard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbEasy.setChecked(false);
                    cbMedium.setChecked(false);
                    sharedPref.setDifficulty(2);
                }
                else {
                    if ((!cbEasy.isChecked()) && (!cbMedium.isChecked())) {
                        cbHard.setChecked(true);
                        sharedPref.setDifficulty(2);
                    }
                }
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Tic Tac Toe");
                String sAux = "\nPlay Tic Tac Toe on your Android phone. Our new modern version appears in a cool design.\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.anubhav87.tictactoe \n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                c.startActivity(Intent.createChooser(i, "choose one"));
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = "devAnubhav87"; // getPackageName() from Context or Activity object
                try {
                    c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=" + appPackageName)));
                }
            }
        });
        ivRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = c.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        c.setFinishOnTouchOutside(true);
    }


}