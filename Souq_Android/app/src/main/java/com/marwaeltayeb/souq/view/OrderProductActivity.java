package com.marwaeltayeb.souq.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityOrderProductBinding;

import java.lang.reflect.Field;

public class OrderProductActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityOrderProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_product);

        binding.addCard.setOnClickListener(this);

        populateSpinner();
    }

    private void orderProduct() {
        String nameOnCard = binding.nameOnCard.getText().toString().trim();
        String cardNumber = binding.cardNumber.getText().toString().trim();

        String year = binding.spinnerYear.getSelectedItem().toString().toLowerCase();
        String day = binding.spinnerMonth.getSelectedItem().toString().toLowerCase();

        Toast.makeText(this, year + " " + day, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addCard) {
            orderProduct();
        }
    }

    private void populateSpinner() {
        String[] years = {"2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        binding.spinnerYear.setAdapter(langAdapter);

        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<CharSequence> langAdapter2 = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, months );
        langAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        binding.spinnerMonth.setAdapter(langAdapter2);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindowYear = (android.widget.ListPopupWindow) popup.get(binding.spinnerYear);
            android.widget.ListPopupWindow popupWindowMonth = (android.widget.ListPopupWindow) popup.get(binding.spinnerMonth);

            // Set popupWindow height to 500px
            popupWindowYear.setHeight(500);
            popupWindowMonth.setHeight(500);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
    }
}
