package pt.attendly.attendly;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import pt.attendly.attendly.firebase.uploadFiles;
import pt.attendly.attendly.model.User;
import pt.attendly.attendly.other.userPref;

public class ProfileActivity extends AppCompatActivity  {

    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView imageView;
    private TextView txtName, txtEmail;
    static User user = LoginActivity.loggedUser;
    static Bitmap userImage;
    final Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        user = LoginActivity.loggedUser;
        setInfo();
    }

    private void setInfo(){

        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        imageView.setImageBitmap(userImage);
    }




    //method to show file chooser
    public void showFileChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                userImage = bitmap;
                imageView.setImageBitmap(bitmap);
                userPref.saveUserImage(userImage, con);

            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadFiles.upload(this, data.getData());
        }
    }

    public void resetPassword (View view)
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("verifying..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(LoginActivity.loggedUser.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Reset password instructions has sent to your email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Email don't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void executeLogout(View view)
    {
        SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userObject", null);
        editor.putString("userImage", null);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
