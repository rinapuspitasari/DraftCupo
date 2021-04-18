package com.example.cupodraft.api.model;

public class DataRecord {
    private String id_produk;

    private String nama_customer;

    private String nama_produk;

    private String id_pinjam;

    private String tanggal_pinjam;

    private String nama_mitra;

    private String id_user;

    private String id_mitra;

    private String tanggal_haruskembali;

    private String status;

    public String getId_produk ()
    {
        return id_produk;
    }

    public void setId_produk (String id_produk)
    {
        this.id_produk = id_produk;
    }

    public String getNama_customer ()
    {
        return nama_customer;
    }

    public void setNama_customer (String nama_customer)
    {
        this.nama_customer = nama_customer;
    }

    public String getNama_produk ()
    {
        return nama_produk;
    }

    public void setNama_produk (String nama_produk)
    {
        this.nama_produk = nama_produk;
    }

    public String getId_pinjam ()
    {
        return id_pinjam;
    }

    public void setId_pinjam (String id_pinjam)
    {
        this.id_pinjam = id_pinjam;
    }

    public String getTanggal_pinjam ()
    {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam (String tanggal_pinjam)
    {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public String getNama_mitra ()
    {
        return nama_mitra;
    }

    public void setNama_mitra (String nama_mitra)
    {
        this.nama_mitra = nama_mitra;
    }

    public String getId_user ()
    {
        return id_user;
    }

    public void setId_user (String id_user)
    {
        this.id_user = id_user;
    }

    public String getId_mitra ()
    {
        return id_mitra;
    }

    public void setId_mitra (String id_mitra)
    {
        this.id_mitra = id_mitra;
    }

    public String getTanggal_haruskembali ()
    {
        return tanggal_haruskembali;
    }

    public void setTanggal_haruskembali (String tanggal_haruskembali)
    {
        this.tanggal_haruskembali = tanggal_haruskembali;
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
        return "ClassPojo [id_produk = "+id_produk+", nama_customer = "+nama_customer+", nama_produk = "+nama_produk+", id_pinjam = "+id_pinjam+", tanggal_pinjam = "+tanggal_pinjam+", nama_mitra = "+nama_mitra+", id_user = "+id_user+", id_mitra = "+id_mitra+", tanggal_haruskembali = "+tanggal_haruskembali+", status = "+status+"]";
    }
}
