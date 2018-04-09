package pt.attendly.attendly.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import pt.attendly.attendly.model.User;

public class userPref {

    public static void saveUserInfo(User user, Context con) {
        SharedPreferences sharedPref = con.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("userObject", json);
        editor.apply();

    }

    public static User loadUserInfo(Context con){
        SharedPreferences sharedPref = con.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String json = sharedPref.getString("userObject", "");
        if(json.isEmpty())
        {
            return null;
        }
        else
        {
            Gson gson = new Gson();
            User currentUser = gson.fromJson(json, User.class);
            return currentUser;
        }

    }

    public static void saveUserImage(Bitmap bitmap, Context con)
    {
        ByteArrayOutputStream baos =new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        SharedPreferences sharedPref = con.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userImage", temp);
        editor.apply();
    }

    public static Bitmap loadUserImage(Context con)
    {
        SharedPreferences sharedPref = con.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String temp = sharedPref.getString("userImage", "");
        if(temp.isEmpty()) {
            return null;
        }
        else
        {
            byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString,0, decodedString.length);
            return bitmap;
        }
    }

}
