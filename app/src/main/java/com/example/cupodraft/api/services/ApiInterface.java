package com.example.cupodraft.api.services;

import com.example.cupodraft.api.model.LoginResponse;
import com.example.cupodraft.api.model.PinjamResponse;
import com.example.cupodraft.api.model.ProdukModel;
import com.example.cupodraft.api.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("api/customer/login")
    Call<LoginResponse> doLogin(@Field("username") String username, @Field("password") String password);

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("customer/register")
    Call<RegisterResponse> doRegister(@Field("fullname") String fullname ,@Field("username") String username, @Field("email") String email, @Field("password") String password);

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("api/peminjaman/getId")
    Call<ProdukModel> getId(@Field("nama_produk") String nama_produk);

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("peminjaman/add")
    Call<RegisterResponse> doPeminjaman(@Field("id_user") String id_user, @Field("id_produk") String id_produk, @Field("id_mitra") String id_mitra);

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("api/peminjaman/getDetail")
    Call<PinjamResponse> getPeminjaman(@Field("id_user") String id_user, @Field("id_produk") String id_produk);

}
