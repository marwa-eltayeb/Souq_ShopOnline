package com.marwaeltayeb.souq.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.WriteReviewViewModel;
import com.marwaeltayeb.souq.databinding.ActivityWriteReviewBinding;
import com.marwaeltayeb.souq.model.Review;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.io.IOException;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT_ID;

public class WriteReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "WriteReviewActivity";

    private ActivityWriteReviewBinding binding;
    private int productId;

    private WriteReviewViewModel writeReviewViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_review);

        writeReviewViewModel = ViewModelProviders.of(this).get(WriteReviewViewModel.class);

        binding.btnSubmit.setOnClickListener(this);
        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getName());

        Intent intent = getIntent();
        productId = intent.getIntExtra(PRODUCT_ID, 0);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            writeReview();
        }
    }

    private void writeReview() {
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        String feedback = binding.txtFeedback.getText().toString().trim();
        int rate = (int) binding.rateProduct.getRating();

        Review review = new Review(userId, productId, rate, feedback);
        writeReviewViewModel.writeReview(review).observe(this, responseBody -> {
            if ((responseBody != null)) {
                try {
                    Toast.makeText(this, responseBody.string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
