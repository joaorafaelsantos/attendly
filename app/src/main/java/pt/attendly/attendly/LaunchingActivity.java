package pt.attendly.attendly;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import pt.attendly.attendly.model.User;
import pt.attendly.attendly.other.userPref;

public class LaunchingActivity extends AppCompatActivity {

    ImageView ivLaunching;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_launching);

        ivLaunching = findViewById(R.id.ivLaunching);

        AnimationDrawable loading_animation = (AnimationDrawable) ivLaunching.getDrawable();
        loading_animation.start();
        cdt.start();
        User currentUser = userPref.loadUserInfo(this);
        if (currentUser == null) {
            intent = new Intent(this, LoginActivity.class);

        } else {

            LoginActivity.loggedUser = currentUser;
            ProfileActivity.userImage = userPref.loadUserImage(this);
            intent = new Intent(this, MainActivity.class);
        }

    }

    CountDownTimer cdt = new CountDownTimer(3000, 3000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            startActivity(intent);
        }
    };

}
