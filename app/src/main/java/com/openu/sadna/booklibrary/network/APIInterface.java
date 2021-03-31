package com.openu.sadna.booklibrary.network;

import com.openu.sadna.booklibrary.network.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/users/1")
    Call<User> login(@Query("username") String username, @Query("password") String password);

  /*  @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}