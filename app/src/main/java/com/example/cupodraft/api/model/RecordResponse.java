package com.example.cupodraft.api.model;

public class RecordResponse {
    private DataRecord[] data;

    private String status;

    public RecordResponse(DataRecord[] data, String status) {
        this.data = data;
        this.status = status;
    }

    public DataRecord[] getData ()
    {
        return data;
    }

    public void setData (DataRecord[] data)
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
