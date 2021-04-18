package com.example.cupodraft.api.model;

public class DataKembali {
    private String id_produk;

    private String id_pinjam;

    private String id_kembali;

    private String tanggal_kembali;

    private String terlambat;

    private String denda;

    private String id_user;

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

    public String getId_pinjam ()
    {
        return id_pinjam;
    }

    public void setId_pinjam (String id_pinjam)
    {
        this.id_pinjam = id_pinjam;
    }

    public String getId_kembali ()
    {
        return id_kembali;
    }

    public void setId_kembali (String id_kembali)
    {
        this.id_kembali = id_kembali;
    }

    public String getTanggal_kembali ()
    {
        return tanggal_kembali;
    }

    public void setTanggal_kembali (String tanggal_kembali)
    {
        this.tanggal_kembali = tanggal_kembali;
    }

    public String getTerlambat ()
    {
        return terlambat;
    }

    public void setTerlambat (String terlambat)
    {
        this.terlambat = terlambat;
    }

    public String getDenda ()
    {
        return denda;
    }

    public void setDenda (String denda)
    {
        this.denda = denda;
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
        return "ClassPojo [id_produk = "+id_produk+", id_pinjam = "+id_pinjam+", id_kembali = "+id_kembali+", tanggal_kembali = "+tanggal_kembali+", terlambat = "+terlambat+", denda = "+denda+", id_user = "+id_user+", id_mitra = "+id_mitra+", status = "+status+"]";
    }

}
