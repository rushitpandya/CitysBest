package com.cs442.group3.citysbest.Client;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cs442.group3.citysbest.R;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        ImageView myImageView= (ImageView)findViewById(R.id.imgLogo);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        myImageView.startAnimation(myFadeInAnimation); //Set animation to your ImageView

//        ImageView myImageView2= (ImageView)findViewById(R.id.skylineimg);
//        Animation myFadeInAnimation2 = AnimationUtils.loadAnimation(this, R.anim.fadein);
//        myImageView2.startAnimation(myFadeInAnimation2); //Set animation to your ImageView


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 5000);
    }
}
