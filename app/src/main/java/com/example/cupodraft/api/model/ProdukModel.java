package com.example.cupodraft.api.model;

public class ProdukModel {
    private DataProduk[] data;

    private String status;

    public DataProduk[] getData ()
    {
        return data;
    }

    public void setData (DataProduk[] data)
    {
        this.data = data;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", status = "+status+"]";
    }
}
