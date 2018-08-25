package com.pmaptechnotech.smartworkflow.activites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pmaptechnotech.smartworkflow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.image_View)
    ImageView image_View;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);
        context = SplashScreenActivity.this;
        final Animation animation_2 = AnimationUtils.loadAnimation(context, R.anim.anim_slash);

        image_View.startAnimation(animation_2);

        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(context, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
