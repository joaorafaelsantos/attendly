package pt.attendly.attendly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.firebase.uploadFiles;
import pt.attendly.attendly.model.User;

public class ProfileActivity extends AppCompatActivity  {

    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView imageView;
    private TextView txtName, txtEmail;
    static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        user = manageData.currentUser;
        setInfo();
    }

    private void setInfo(){
        new GetImage().execute();
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());

    }

    private class GetImage extends AsyncTask<Void, Void, Void> {
        Bitmap myBitmap = null;
        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL myFileUrl = new URL (user.getUrl_picture());
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                myBitmap = BitmapFactory.decodeStream(is);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            imageView.setImageBitmap(myBitmap);
        }
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
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadFiles.upload(this, data.getData(), MainActivity.userId);
        }
    }

}
