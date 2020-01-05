package com.marwaeltayeb.souq.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.marwaeltayeb.souq.R;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = findViewById(R.id.imageSlider);

        int[] images = {R.drawable.slide1, R.drawable.slide2,
                R.drawable.slide3, R.drawable.slide4,
                R.drawable.slide5, R.drawable.slide6, R.drawable.slide7};

        for (int image: images) {
            flipperImages(image);
        }
    }


    private void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        // Set the animation for the view that enters the screen
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
    }
}
