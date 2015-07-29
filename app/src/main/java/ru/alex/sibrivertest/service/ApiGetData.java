package ru.alex.sibrivertest.service;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.alex.sibrivertest.R;
import ru.alex.sibrivertest.model.JsonArrayModel;
import ru.alex.sibrivertest.model.ResponseArrayModel;
import ru.alex.sibrivertest.ormlitedatabase.dao.ItemModelDAO;
import ru.alex.sibrivertest.ormlitedatabase.helper.HelperFactory;
import ru.alex.sibrivertest.ormlitedatabase.model.ItemModel;

public class ApiGetData extends AsyncTask<String, Void, String> {
    Context context;
    private ProgressDialog progressDialog;
    private BufferedReader reader = null;
    private HttpURLConnection urlConnection = null;
    private String resultJsonString = "";
    private JsonArrayModel jsonArrayModel;
    private ArrayList<ResponseArrayModel> responseArrayModels;
    private ItemModel itemModel = new ItemModel();
    private int connectionRequest;
    private TextView emptyData;
    private Toast toast;

    public ApiGetData(Context context, TextView emptyData){
        this.context = context;
        this.emptyData = emptyData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Getting Json data");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String resultJson;
        try{
            resultJson = getJsonFromUri(context.getResources().getString(R.string.url));
        } catch (IOException io){
            resultJson = io.getMessage();
        }
        return resultJson;
    }

    @Override
    protected void onPostExecute(String resultJson) {
        if(isNetworkAvailable()) {
            if (connectionRequest == 200) {
                writeData();
                emptyData.setVisibility(View.INVISIBLE);
            }
            else if(connectionRequest == 404){
                try {
                    getData();
                    toast = Toast.makeText(context, context.getResources().getString(R.string.notFound), Toast.LENGTH_SHORT);
                    toast.show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            else if(connectionRequest == 400){
                try {
                    getData();
                    toast = Toast.makeText(context, context.getResources().getString(R.string.badRequest), Toast.LENGTH_SHORT);
                    toast.show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(connectionRequest == 500){
                try {
                    getData();
                    toast = Toast.makeText(context, context.getResources().getString(R.string.serverError), Toast.LENGTH_SHORT);
                    toast.show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        progressDialog.dismiss();
        super.onPostExecute(resultJson);
    }


    private String getJsonFromUri(String uri) throws IOException {
        URL url = new URL(uri);
        urlConnection = (HttpURLConnection) url.openConnection();
        connectionRequest = urlConnection.getResponseCode();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        resultJsonString = buffer.toString();
        return resultJsonString;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void writeData(){
        jsonArrayModel = new GsonBuilder().create().fromJson(resultJsonString, JsonArrayModel.class);
        responseArrayModels = jsonArrayModel.getResponseArrayModels();
        try {
            ItemModelDAO itemModelDAO = HelperFactory.getDatabaseHelper().getItemModelDAO();
            itemModelDAO.deleteAll();
            for (ResponseArrayModel arrayModel : responseArrayModels){
                itemModel.setRid(arrayModel.getId());
                itemModel.setName(arrayModel.getName());
                itemModel.setStatus(arrayModel.getStatus());
                itemModel.setAddress(arrayModel.getAddress());
                itemModel.setLat(arrayModel.getLat());
                itemModel.setLon(arrayModel.getLon());
                itemModel.setCreated(arrayModel.getCreated());
                itemModelDAO.addData(itemModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getData() throws SQLException {
        ItemModelDAO itemModelDAO = HelperFactory.getDatabaseHelper().getItemModelDAO();
        List<ItemModel> itemModels = itemModelDAO.getAllItems();
    }
}

