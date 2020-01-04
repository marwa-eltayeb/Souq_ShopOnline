package com.marwaeltayeb.souq.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.marwaeltayeb.souq.R;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private Animation in_from_right, out_to_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] images = {R.drawable.slide1, R.drawable.slide2,
                R.drawable.slide3, R.drawable.slide4,
                R.drawable.slide5, R.drawable.slide6, R.drawable.slide7};

        viewFlipper = findViewById(R.id.imageSlider);

        for (int image: images) {
            flipperImages(image);
        }

        // Create animations
        in_from_right = AnimationUtils.loadAnimation(this, R.anim.in_from_right);
        out_to_left = AnimationUtils.loadAnimation(this, R.anim.out_to_left);

    }


    private void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setAutoStart(true);

        //set the animation for the view that enters the screen
        viewFlipper.setInAnimation(in_from_right);
        //set the animation for the view leaving th screen
        viewFlipper.setOutAnimation(out_to_left);
    }
}
