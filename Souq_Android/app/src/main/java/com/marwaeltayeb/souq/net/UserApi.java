package com.marwaeltayeb.souq.net;

import com.marwaeltayeb.souq.model.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("register")
    Call<ResponseBody> insertUser(@Body UserModel body);
}
