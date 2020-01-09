package com.marwaeltayeb.souq.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityProductBinding;
import com.marwaeltayeb.souq.utils.Slide;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        binding.txtSeeAllMobiles.setOnClickListener(this);
        binding.txtSeeAllComputers.setOnClickListener(this);

        flipImages(Slide.getSlides());
    }



    private void flipImages(ArrayList<Integer> images){
        for (int image: images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            binding.imageSlider.addView(imageView);
        }

        binding.imageSlider.setFlipInterval(2000);
        binding.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        binding.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSeeAllMobiles:
                goToSeeAllMobiles();
                break;
            case R.id.txtSeeAllComputers:
                //goToSeeAllComputers();
                break;
        }
    }

    private void goToSeeAllMobiles(){
        Intent intent = new Intent(this, SeeAllActivity.class);
        startActivity(intent);
    }
}
