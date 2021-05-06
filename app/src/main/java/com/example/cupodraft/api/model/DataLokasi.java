package com.example.cupodraft.api.model;

public class DataLokasi {
    private String id_lokasi;

    private String latitude;

    private String id_mitra;

    private String fullname;

    private String alamat;

    private String longitude;

    public String getId_lokasi ()
    {
        return id_lokasi;
    }

    public void setId_lokasi (String id_lokasi)
    {
        this.id_lokasi = id_lokasi;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getId_mitra ()
    {
        return id_mitra;
    }

    public void setId_mitra (String id_mitra)
    {
        this.id_mitra = id_mitra;
    }

    public String getFullname ()
    {
        return fullname;
    }

    public void setFullname (String fullname)
    {
        this.fullname = fullname;
    }

    public String getAlamat ()
    {
        return alamat;
    }

    public void setAlamat (String alamat)
    {
        this.alamat = alamat;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id_lokasi = "+id_lokasi+", latitude = "+latitude+", id_mitra = "+id_mitra+", fullname = "+fullname+", alamat = "+alamat+", longitude = "+longitude+"]";
    }
}
