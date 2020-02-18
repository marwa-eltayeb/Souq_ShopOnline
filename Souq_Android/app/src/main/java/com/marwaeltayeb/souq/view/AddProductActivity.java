package com.marwaeltayeb.souq.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityAddProductBinding;

public class AddProductActivity extends AppCompatActivity {

    private ActivityAddProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);

        populateSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            addProduct();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addProduct() {
        String name = binding.txtName.getText().toString();
        String price = binding.txtPrice.getText().toString();
        String quantity = binding.txtQuantity.getText().toString();
        String supplier = binding.txtSupplier.getText().toString();
        String category = binding.categorySpinner.getSelectedItem().toString().toLowerCase();

        // Check if there are no empty values
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) ||
                TextUtils.isEmpty(quantity) || TextUtils.isEmpty(supplier)
                || TextUtils.isEmpty(category)) {
            Toast.makeText(this, getString(R.string.required_data), Toast.LENGTH_SHORT).show();
            return;
        }


    }


    private void populateSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);
    }
}
