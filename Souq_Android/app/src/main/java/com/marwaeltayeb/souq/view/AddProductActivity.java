package com.marwaeltayeb.souq.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityAddProductBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.souq.utils.Constant.PICK_IMAGE;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddProductActivity";
    private ActivityAddProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);

        binding.btnSelectImage.setOnClickListener(this);

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
        double price = Double.parseDouble(binding.txtPrice.getText().toString());
        int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());


        String name = binding.txtName.getText().toString().trim();
        String priceString = binding.txtPrice.getText().toString().trim();
        String quantityString = binding.txtQuantity.getText().toString().trim();
        String supplier = binding.txtSupplier.getText().toString().trim();
        String category = binding.categorySpinner.getSelectedItem().toString().toLowerCase();

        // Check if there are no empty values
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceString) ||
                TextUtils.isEmpty(quantityString) || TextUtils.isEmpty(supplier)
                || TextUtils.isEmpty(category)) {
            Toast.makeText(this, getString(R.string.required_data), Toast.LENGTH_SHORT).show();
        }

        Product product = new Product(name,price,quantity,supplier,category," ");
        RetrofitClient.getInstance().getApi().insertProduct(product).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(AddProductActivity.this, response.body().string() + "", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);
    }

    private void getImageFromGallery() {
        try {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, PICK_IMAGE);
        } catch (Exception exp) {
            Log.i("Error", exp.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            Log.d(TAG, "onActivityResult: " + selectedImage);
            binding.imageOfProduct.setImageURI(selectedImage);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSelectImage) {
            getImageFromGallery();
        }
    }

}
