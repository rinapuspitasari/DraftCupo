package com.example.cupodraft.api.model;
public class Data
{
    private String full_name;

    private String created_at;

    private String id_cust;

    private String limit_pinjam;

    private String email;

    private String token;

    public String getFull_name ()
    {
        return full_name;
    }

    public void setFull_name (String full_name)
    {
        this.full_name = full_name;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getId_cust ()
    {
        return id_cust;
    }

    public void setId_cust (String id_cust)
    {
        this.id_cust = id_cust;
    }

    public String getLimit_pinjam ()
    {
        return limit_pinjam;
    }

    public void setLimit_pinjam (String limit_pinjam)
    {
        this.limit_pinjam = limit_pinjam;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [full_name = "+full_name+", created_at = "+created_at+", id_cust = "+id_cust+", limit_pinjam = "+limit_pinjam+", email = "+email+", token = "+token+"]";
    }
}
