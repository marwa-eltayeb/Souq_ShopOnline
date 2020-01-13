package com.marwaeltayeb.souq.net;

import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("users/register")
    Call<ResponseBody> insertUser(@Body User user);


    @GET("users/login")
    Call<ResponseBody> logInUser(@Query("email") String email, @Query("password") String password);


    @GET("products")
    Call<ProductApiResponse> getProducts(@Query("page") int page);

    @GET("products")
    Call<ProductApiResponse> getProductsByCategory(@Query("category") String category,@Query("page") int page);
}
