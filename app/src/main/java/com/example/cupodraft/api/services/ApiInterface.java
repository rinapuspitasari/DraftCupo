package com.example.cupodraft.api.services;

import com.example.cupodraft.api.model.DataTotal;
import com.example.cupodraft.api.model.KembaliResponse;
import com.example.cupodraft.api.model.LoginResponse;
import com.example.cupodraft.api.model.LokasiResponse;
import com.example.cupodraft.api.model.PinjamResponse;
import com.example.cupodraft.api.model.ProdukModel;
import com.example.cupodraft.api.model.RecordResponse;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("api/customer/login")
    Call<LoginResponse> doLogin(@Field("username") String username, @Field("password") String password);

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("customer/register")
    Call<RegisterResponse> doRegister(@Field("fullname") String fullname ,@Field("username") String username, @Field("email") String email, @Field("no_hp") String no_hp, @Field("password") String password);

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

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("api/pengembalian/getDetail")
    Call<KembaliResponse> getPengembalian(@Field("id_user") String id_user, @Field("id_produk") String id_produk, @Field("id_pinjam") String id_pinjam);

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("pengembalian/add")
    Call<RegisterResponse> doPengembalian(@Field("id_user") String id_user, @Field("id_produk") String id_produk, @Field("id_mitra") String id_mitra, @Field("id_pinjam") String id_pinjam);

    @Headers("X-API-KEY: " + "apikey")
    @GET("peminjaman/getCust")
    Call<RecordResponse> getPinjam(@Query("id_user")String custId);

    @Headers("X-API-KEY: " + "apikey")
    @GET("api/lokasi")
    Call<LokasiResponse> getLokasi();

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @PUT("customer/update")
    Call<RegisterResponse> updateUser (@Field("id_cust")String custId, @Field("fullname")String fullname, @Field("username")String username, @Field("email")String email, @Field("no_hp")String noHp );

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @PUT("customer/update")
    Call<RegisterResponse> updatePass (@Field("id_cust")String custId, @Field("password")String password);

    @Headers("X-API-KEY: " + "apikey")
    @FormUrlEncoded
    @POST("api/customer/getCustomer")
    Call<UserResponse> getCustUsername (@Field("username")String username);

    @Headers("X-API-KEY: " + "apikey")
    @GET("api/customer")
    Call<UserResponse> getUser(@Query("id_user")String custId);

    @Headers("X-API-KEY: " + "apikey")
    @GET("api/peminjaman/getRows")
    Call<DataTotal> getTotal();
}
