package pt.attendly.attendly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pt.attendly.attendly.model.User;
import pt.attendly.attendly.other.loadingAnimation;
import pt.attendly.attendly.other.userPref;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtEmail, txtPassword;
    final Context con = this;

    ImageView ivLoading;

    public static User loggedUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        ivLoading = findViewById(R.id.ivLoading);

    }

    public void createLogin(View view) {

        loadingAnimation.execute(ivLoading);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseDatabase.getInstance().getReference("User").orderByChild("id").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                    for (DataSnapshot child : children) {
                                        loggedUser = child.getValue(User.class);
                                    }
                                    User newUser = loggedUser;
                                    userPref.saveUserInfo(newUser, con);
                                    loadUserImage();
                                    Intent intent = new Intent(con, MainActivity.class);
                                    if(newUser.getType() == 1)
                                    {
                                        intent = new Intent(con, MainTeacherActivity.class);
                                    }
                                    startActivity(intent);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    public void loadUserImage() {
        new GetImage().execute();
    }

    private class GetImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL myFileUrl = new URL(loggedUser.getUrl_picture());
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                ProfileActivity.userImage = bitmap;
                userPref.saveUserImage(bitmap, con);

            } catch (MalformedURLException e) {
//                e.printStackTrace();
            } catch (IOException e) {
//                e.printStackTrace();
            }

            return null;
        }
    }

    //DISABLE BACK BUTTON PRESS TO PREVIOUS ACTIVITY
    @Override
    public void onBackPressed() {
        //EXIT APPLICATION
        this.finishAffinity();
    }

}
