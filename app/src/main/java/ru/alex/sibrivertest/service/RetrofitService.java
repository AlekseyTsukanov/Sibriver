package ru.alex.sibrivertest.service;



import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import ru.alex.sibrivertest.model.JsonArrayModel;
import ru.alex.sibrivertest.model.ResponseArrayModel;

public interface RetrofitService {

    @PUT("/bins/10fda")
    void createTask(@Body JsonArrayModel jam, Callback<JsonArrayModel> cb);

}
