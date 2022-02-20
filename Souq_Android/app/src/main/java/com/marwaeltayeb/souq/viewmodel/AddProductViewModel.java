package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.repository.AddProductRepository;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class AddProductViewModel extends ViewModel {

    private final AddProductRepository addProductRepository;

    public AddProductViewModel() {
        addProductRepository = new AddProductRepository();
    }

    public LiveData<ResponseBody> addProduct(String token, Map<String, RequestBody> productInfo, MultipartBody.Part image) {
        return addProductRepository.addProduct(token,productInfo,image);
    }
}
