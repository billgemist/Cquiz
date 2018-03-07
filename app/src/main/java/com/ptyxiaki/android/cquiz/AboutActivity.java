package com.ptyxiaki.android.cquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
/*  THIS IS THE OFFICIAL PROJECT! */
public class AboutActivity extends AppCompatActivity {

    private Button mAboutButton;
    private Button mEmailButtonMine;
    private Button mEmailButtonProf;
    private Button mWebsiteButton;

    private static final String KEY_LEVEL = "pass to gameActivity the level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mEmailButtonMine = (Button) findViewById(R.id.mine_mail_button);
        mEmailButtonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sentEmail = new Intent(Intent.ACTION_SEND);
                sentEmail.setType("plain/text");
                sentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"cquizproject@gmail.com"});
                sentEmail.putExtra(Intent.EXTRA_SUBJECT, "");
                sentEmail.putExtra(Intent.EXTRA_TEXT, "");
                //startActivity(Intent.createChooser(intent, ""));
                startActivity(sentEmail);
            }
        });

      /*
        mEmailButtonProf = (Button) findViewById(R.id.prof_mail_button);
        mEmailButtonProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sentEmail = new Intent(Intent.ACTION_SEND);
                sentEmail.setType("plain/text");
                sentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"profmail@mailaddress.com"}); //I'll add professor email If he agrees
                sentEmail.putExtra(Intent.EXTRA_SUBJECT, "");
                sentEmail.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(sentEmail);
            }
        });

        mWebsiteButton = (Button) findViewById(R.id.prof_site_button);
        mWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cna.uop.gr/~ntsel/"));
                startActivity(browserIntent);
            }
        });
        */


    }
}


