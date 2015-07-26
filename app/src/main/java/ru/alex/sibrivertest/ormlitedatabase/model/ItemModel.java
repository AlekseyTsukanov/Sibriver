package ru.alex.sibrivertest.ormlitedatabase.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "items")
public class ItemModel {

    public ItemModel(){}
    public ItemModel(int id, int rid, String name, int status, String address, float lat, float lon, String created){
        this.id = id;
        this.rid = rid;
        this.name = name;
        this.status = status;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.created = created;
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(dataType = DataType.INTEGER, columnName = "rid")
    private int rid;

    @DatabaseField(dataType = DataType.STRING, columnName = "name")
    private String name;

    @DatabaseField(dataType = DataType.INTEGER, columnName = "status")
    private int status;

    @DatabaseField(dataType = DataType.STRING, columnName = "address")
    private String address;

    @DatabaseField(dataType = DataType.FLOAT, columnName = "lat")
    private float lat;

    @DatabaseField(dataType = DataType.FLOAT, columnName = "lon")
    private float lon;

    @DatabaseField(dataType = DataType.STRING, columnName = "created")
    private String created;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemModel itemModel = (ItemModel) o;

        if (id != itemModel.id) return false;
        if (rid != itemModel.rid) return false;
        if (status != itemModel.status) return false;
        if (Float.compare(itemModel.lat, lat) != 0) return false;
        if (Float.compare(itemModel.lon, lon) != 0) return false;
        if (!name.equals(itemModel.name)) return false;
        if (!address.equals(itemModel.address)) return false;
        if (!created.equals(itemModel.created)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + rid;
        result = 31 * result + name.hashCode();
        result = 31 * result + status;
        result = 31 * result + address.hashCode();
        result = 31 * result + (lat != +0.0f ? Float.floatToIntBits(lat) : 0);
        result = 31 * result + (lon != +0.0f ? Float.floatToIntBits(lon) : 0);
        result = 31 * result + created.hashCode();
        return result;
    }
}