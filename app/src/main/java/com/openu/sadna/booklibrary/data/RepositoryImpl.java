package com.openu.sadna.booklibrary.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.network.APIInterface;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//The real repository of the app
public class RepositoryImpl implements Repository {

    private static Repository instance = null;

    private final MutableLiveData<User> currentUser;
    private final APIInterface apiService;
    private final SharedPrefs sharedPrefs;

    private RepositoryImpl(APIInterface apiService, SharedPrefs sharedPrefs){
        this.apiService = apiService;
        this.sharedPrefs = sharedPrefs;
        currentUser = new MutableLiveData<>(this.sharedPrefs.getUser());
    }

    public synchronized static Repository getInstance(APIInterface apiService, SharedPrefs sharedPrefs){
        if(instance == null)
            instance = new RepositoryImpl(apiService, sharedPrefs);
        return instance;
    }

    @Override
    public void login(String username, String password, final RequestCallback<Void> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<User> loginCall = apiService.login(body);
        loginCall.enqueue(new Callback<User>() {
              @Override
              public void onResponse(Call<User> call, Response<User> response) {
                  if(response.code() == 200 || response.code() == 403) {
                      User user = response.body();
                      if(user.getToken() != null) {
                          currentUser.setValue(user);
                          sharedPrefs.setUser(user);
                      }
                      else
                          currentUser.setValue(null);

                      if(callback != null)
                          callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                  }
                  else if(callback != null)
                      callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
              }

              @Override
              public void onFailure(Call<User> call, Throwable t) {
                  if(callback != null)
                      callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
              }
          });
    }

    @Override
    public void register(String username, String password, String fname, String lname, final RequestCallback<Void> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("password", password);
            object.put("firstname", fname);
            object.put("lastname", lname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<User> call = apiService.register(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200 || response.code() == 403) {
                    User user = response.body();
                    if(user.getToken() != null) {
                        currentUser.setValue(user);
                        sharedPrefs.setUser(user);
                    }
                    else
                        currentUser.setValue(null);

                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public LiveData<User> getCurrentUser(){
        return currentUser;
    }

    @Override
    public void logout(){
        sharedPrefs.clearUser();
        currentUser.setValue(null);
    }


    @Override
    public void addBook(int bookISBN, String bookName, String authorName, String authorFamily, String description, String category, final RequestCallback<Void> callback){
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
            object.put("isbn", bookISBN);
            object.put("bookName", bookName);
            object.put("authorName", authorName);
            object.put("authorFamily", authorFamily);
            object.put("description", description);
            object.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<Void> call = apiService.addBook(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200 || response.code() == 403 || response.code() == 201) {
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void getBooks(String textQuery, String category, final RequestCallback<List<Book>> callback) {
        if(textQuery == null)
            textQuery = "";
        if(category == null)
            category = "";

        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
            object.put("textquery", textQuery);
            object.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<List<Book>> call = apiService.getBooks(body);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(response.code() == 200 || response.code() == 403) {
                    List<Book> books = response.body();
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, books);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void getBook(int bookID, final RequestCallback<Book> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
            object.put("isbn", bookID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<Book> loginCall = apiService.getBook(body);
        loginCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if(response.code() == 200 || response.code() == 403) {
                    Book book = response.body();
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, book);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void orderBook(int bookID, final RequestCallback<Void> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
            object.put("isbn", bookID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<Void> call = apiService.orderBook(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200 || response.code() == 403) {
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void returnBook(int bookID, final RequestCallback<Void> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
            object.put("isbn", bookID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<Void> call = apiService.returnBook(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200 || response.code() == 403) {
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void getBookReviews(int bookID, final RequestCallback<List<Review>> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
            object.put("isbn", bookID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<List<Review>> loginCall = apiService.getBookReviews(body);
        loginCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.code() == 200 || response.code() == 403) {
                    List<Review> reviews = response.body();
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, reviews);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void addBookReview(int bookID, String review, final RequestCallback<Void> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
            object.put("isbn", bookID);
            object.put("content", review);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<Void> call = apiService.addBookReview(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200 || response.code() == 403) {
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void getUserOrderHistory(final RequestCallback<List<Book>> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<List<Book>> call = apiService.getUserOrderHistory(body);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(response.code() == 200 || response.code() == 403) {
                    List<Book> books = response.body();
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, books);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void getLentBooks(final RequestCallback<List<Book>> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<List<Book>> call = apiService.getLentBooks(body);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(response.code() == 200 || response.code() == 403) {
                    List<Book> books = response.body();
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, books);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }

    @Override
    public void getCategories(final RequestCallback<Categories> callback) {
        JSONObject object = new JSONObject();
        try {
            object.put("token", currentUser.getValue().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());

        Call<List<String>> call = apiService.getCategories(body);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.code() == 200 || response.code() == 403) {
                    List<String> categories = response.body();
                    Categories cats = new Categories(categories);
                    if(callback != null)
                        callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, cats);
                }
                else if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if(callback != null)
                    callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
            }
        });
    }


}
