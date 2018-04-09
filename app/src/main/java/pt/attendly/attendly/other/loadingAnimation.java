package pt.attendly.attendly.other;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

public class loadingAnimation {

    public static void execute(ImageView ivLoading){
        ivLoading.setVisibility(View.VISIBLE);
        AnimationDrawable loading_animation = (AnimationDrawable) ivLoading.getDrawable();
        loading_animation.start();
    }

}
