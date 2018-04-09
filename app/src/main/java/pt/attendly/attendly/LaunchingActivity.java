package pt.attendly.attendly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import pt.attendly.attendly.model.User;
import pt.attendly.attendly.userPref.userPref;

public class LaunchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);

        User currentUser = userPref.loadUserInfo(this);

        if(currentUser == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {

            LoginActivity.loggedUser = currentUser;
            ProfileActivity.userImage = userPref.loadUserImage(this);
            Intent intent = new Intent(this, MainActivity.class);
            if(currentUser.getType() == 1)
            {
                intent = new Intent(this, MainTeacherActivity.class);
            }
            startActivity(intent);
        }



    }
}
