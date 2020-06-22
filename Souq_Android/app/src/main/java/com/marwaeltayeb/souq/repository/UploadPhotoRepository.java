package com.marwaeltayeb.souq.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhotoRepository {

    private static final String TAG = UploadPhotoRepository.class.getSimpleName();
    private Application application;

    public UploadPhotoRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> uploadPhoto(String pathname) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();

        File file = new File(pathname);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part photo = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(LoginUtils.getInstance(application).getUserInfo().getId()));

        RetrofitClient.getInstance().getApi().uploadPhoto(photo, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + "Image Updated");

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
