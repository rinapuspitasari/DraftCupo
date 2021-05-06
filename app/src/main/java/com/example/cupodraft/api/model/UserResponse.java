package com.example.cupodraft.api.model;

public class UserResponse {
    private DataUser[] data;

    private String status;

    public DataUser[] getData ()
    {
        return data;
    }

    public void setData (DataUser[] data)
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
