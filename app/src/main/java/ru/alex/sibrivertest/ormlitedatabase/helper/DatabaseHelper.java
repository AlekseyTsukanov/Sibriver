package ru.alex.sibrivertest.ormlitedatabase.helper;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ru.alex.sibrivertest.ormlitedatabase.dao.ItemModelDAO;
import ru.alex.sibrivertest.ormlitedatabase.model.ItemModel;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME ="items.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    private ItemModelDAO itemModelDAO = null;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, ItemModel.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, ItemModel.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /*Singleton*/
    public ItemModelDAO getItemModelDAO() throws java.sql.SQLException {
        if(itemModelDAO == null){
            itemModelDAO = new ItemModelDAO(getConnectionSource(), ItemModel.class);
        }
        return itemModelDAO;
    }

    @Override
    public void close(){
        super.close();
        itemModelDAO = null;
    }
}
