package com.marwaeltayeb.souq.net;

import com.marwaeltayeb.souq.model.FavoriteApiResponse;
import com.marwaeltayeb.souq.model.Favorite;
import com.marwaeltayeb.souq.model.LoginApiResponse;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.model.RegisterApiResponse;
import com.marwaeltayeb.souq.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("users/register")
    Call<RegisterApiResponse> createUser(@Body User user);

    @GET("users/login")
    Call<LoginApiResponse> logInUser(@Query("email") String email, @Query("password") String password);

    @DELETE("users/{userId}")
    Call<ResponseBody> deleteAccount(@Path("userId") int userId);

    @POST("products/insert")
    Call<ResponseBody> insertProduct(@Body Product product);

    @GET("products")
    Call<ProductApiResponse> getProducts(@Query("page") int page);

    @GET("products")
    Call<ProductApiResponse> getProductsByCategory(@Query("category") String category,@Query("page") int page);

    @GET("products/search")
    Call<ProductApiResponse> searchForProduct(@Query("q") String keyword);

    @POST("favorites/add")
    Call<ResponseBody> addFavorite(@Body Favorite favorite);

    @DELETE("favorites/remove")
    Call<ResponseBody> removeFavorite(@Query("userId") int userId, @Query("productId") int productId);

    @GET("favorites")
    Call<FavoriteApiResponse> getFavorites(@Query("userId") int userId);

}
