package com.openu.sadna.booklibrary.network;

import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//API interface implementation for Retrofit
public interface APIInterface {

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<User> login(@Body RequestBody params);


    @POST("/register")
    Call<User> register(@Body RequestBody params);


    @POST("/books/new")
    Call<Void> addBook(@Body RequestBody params);


    @POST("/searchbooks")
    Call<List<Book>> getBooks(@Body RequestBody params);


    @POST("/getbook")
    Call<Book> getBook(@Body RequestBody params);


    @POST("/orderbook")
    Call<Void> orderBook(@Body RequestBody params);


    @POST("/returnbook")
    Call<Void> returnBook(@Body RequestBody params);


    @POST("/getreviews")
    Call<List<Review>> getBookReviews(@Body RequestBody params);



    @POST("/addreview")
    Call<Void> addBookReview(@Body RequestBody params);


    @POST("/books/categories")
    Call<List<String>> getCategories(@Body RequestBody params);



    @POST("/getrenthistory")
    Call<List<Book>> getUserOrderHistory(@Body RequestBody params);


    @POST("/getlentbooks")
    Call<List<Book>> getLentBooks(@Body RequestBody params);

  /*  @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Field("page") String page);


    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}