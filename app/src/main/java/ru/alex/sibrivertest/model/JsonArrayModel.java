package ru.alex.sibrivertest.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JsonArrayModel {

    @SerializedName("code")
    private Integer code;

    @SerializedName("response")
    private ArrayList<ResponseArrayModel> responseArrayModels;

    public JsonArrayModel(Integer code, ArrayList<ResponseArrayModel> responseArrayModels){
        this.code = code;
        this.responseArrayModels = responseArrayModels;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ArrayList<ResponseArrayModel> getResponseArrayModels() {
        return responseArrayModels;
    }

    public void setResponseArrayModels(ArrayList<ResponseArrayModel> responseArrayModels) {
        this.responseArrayModels = responseArrayModels;
    }

}
