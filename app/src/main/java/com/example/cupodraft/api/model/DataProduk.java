package com.example.cupodraft.api.model;

public class DataProduk {
    private String id_produk;

    private String nama_produk;

    private String qr_code;

    private String id_mitra;

    private String status;

    public String getId_produk ()
    {
        return id_produk;
    }

    public void setId_produk (String id_produk)
    {
        this.id_produk = id_produk;
    }

    public String getNama_produk ()
    {
        return nama_produk;
    }

    public void setNama_produk (String nama_produk)
    {
        this.nama_produk = nama_produk;
    }

    public String getQr_code ()
    {
        return qr_code;
    }

    public void setQr_code (String qr_code)
    {
        this.qr_code = qr_code;
    }

    public String getId_mitra ()
    {
        return id_mitra;
    }

    public void setId_mitra (String id_mitra)
    {
        this.id_mitra = id_mitra;
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
        return "ClassPojo [id_produk = "+id_produk+", nama_produk = "+nama_produk+", qr_code = "+qr_code+", id_mitra = "+id_mitra+", status = "+status+"]";
    }
}
