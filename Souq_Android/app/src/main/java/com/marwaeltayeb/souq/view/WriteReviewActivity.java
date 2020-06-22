package com.marwaeltayeb.souq.view;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.WriteReviewViewModel;
import com.marwaeltayeb.souq.databinding.ActivityWriteReviewBinding;
import com.marwaeltayeb.souq.model.Review;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.io.IOException;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT_ID;

public class WriteReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "WriteReviewActivity";

    private ActivityWriteReviewBinding binding;
    private int productId;

    private WriteReviewViewModel writeReviewViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_review);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.write_review));

        writeReviewViewModel = ViewModelProviders.of(this).get(WriteReviewViewModel.class);

        binding.btnSubmit.setOnClickListener(this);
        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getName());

        getCurrentTextLength();

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
        String feedback = binding.editFeedback.getText().toString().trim();
        float rate = binding.rateProduct.getRating();

        // Check if there are no empty values
        if (TextUtils.isEmpty(feedback) || rate == 0.0f) {
            Toast.makeText(this, getString(R.string.required_data), Toast.LENGTH_SHORT).show();
            return;
        }

        Review review = new Review(userId, productId, rate, feedback);
        writeReviewViewModel.writeReview(review).observe(this, responseBody -> {
            if ((responseBody != null)) {
                try {
                    Toast.makeText(this, responseBody.string(), Toast.LENGTH_SHORT).show();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCurrentTextLength(){
        binding.editFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int textLength = 150;
                int writtenTextLength = s.toString().length();
                binding.textLength.setText(String.valueOf(textLength - writtenTextLength));
            }
        });
    }
}
