package com.example.cupodraft.api.model;

public class LokasiResponse
{
    private DataLokasi[] data;

    private String status;

    public DataLokasi[] getData ()
    {
        return data;
    }

    public void setData (DataLokasi[] data)
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