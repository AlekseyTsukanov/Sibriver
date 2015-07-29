package ru.alex.sibrivertest.ormlitedatabase.dao;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.alex.sibrivertest.ormlitedatabase.helper.DatabaseHelper;
import ru.alex.sibrivertest.ormlitedatabase.helper.HelperFactory;
import ru.alex.sibrivertest.ormlitedatabase.model.ItemModel;

public class ItemModelDAO extends BaseDaoImpl<ItemModel, Integer> {

    public ItemModelDAO(ConnectionSource connectionSource,
                        Class<ItemModel> dataClass) throws SQLException{
        super(connectionSource, dataClass);
    }

    public List<ItemModel> getAllItems() throws SQLException {
        return this.queryForAll();
    }

    public int addData(ItemModel itemModel) throws SQLException {
        return this.create(itemModel);
    }

    public void deleteAll() throws SQLException {
        List<ItemModel> itemModels = this.queryForAll();
        this.delete(itemModels);
    }
}
