package ru.alex.sibrivertest.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import ru.alex.sibrivertest.model.JsonArrayModel;
import ru.alex.sibrivertest.model.ResponseArrayModel;
import ru.alex.sibrivertest.ormlitedatabase.dao.ItemModelDAO;
import ru.alex.sibrivertest.ormlitedatabase.helper.HelperFactory;
import ru.alex.sibrivertest.ormlitedatabase.model.ItemModel;

public class ApiPostData {

    private RetrofitService retrofitService;

    public ApiPostData(){}

    public RetrofitService getRetrofitService(){
        return retrofitService;
    }

    public void sendData() throws SQLException {
        JsonArrayModel jsonArrayModel = generateJson();
        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.myjson.com/")
                .setConverter(new GsonConverter(gson))
                .build();

        retrofitService = restAdapter.create(RetrofitService.class);
        retrofitService.createTask(jsonArrayModel, new Callback<JsonArrayModel>() {
            @Override
            public void success(JsonArrayModel jsonArrayModel, Response response) {
                System.out.println("success\n" + response.getUrl() +
                        "\nResponse status: " + response.getStatus() +
                        "\nResponse headers: " + response.getHeaders() +
                        "\nResponse body: " + response.getBody());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error!\n" + error.getResponse() +
                        "\nResponse message" + error.getMessage());
            }
        });
    }

    public JsonArrayModel generateJson() throws SQLException {
        ArrayList<ResponseArrayModel> responseArrayModels = new ArrayList<>();
        JsonArrayModel jsonArrayModel;
        /*--------------------------------------------------------------------------------------*/
        ItemModelDAO itemModelDAO = HelperFactory.getDatabaseHelper().getItemModelDAO();
        List<ItemModel>itemModels = itemModelDAO.getAllItems();
        for(ItemModel models : itemModels){
            ResponseArrayModel responseArrayModel = new ResponseArrayModel();
            responseArrayModel.setId(models.getRid());
            responseArrayModel.setName(models.getName());
            responseArrayModel.setStatus(models.getStatus());
            responseArrayModel.setAddress(models.getAddress());
            responseArrayModel.setLat(models.getLat());
            responseArrayModel.setLon(models.getLon());
            responseArrayModel.setCreated(models.getCreated());
            responseArrayModels.add(responseArrayModel);
        }
        return jsonArrayModel = new JsonArrayModel(200, responseArrayModels);
    }

}
