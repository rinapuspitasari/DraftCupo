package com.example.cupodraft.api.model;

public class DataUser {
    private String password;

    private String no_hp;

    private String updated_at;

    private String created_at;

    private String id_cust;

    private String fullname;

    private String avatar;

    private String limit_pinjam;

    private String email;

    private String username;

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getNo_hp ()
    {
        return no_hp;
    }

    public void setNo_hp (String no_hp)
    {
        this.no_hp = no_hp;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
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

    public String getFullname ()
    {
        return fullname;
    }

    public void setFullname (String fullname)
    {
        this.fullname = fullname;
    }

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
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

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [password = "+password+", no_hp = "+no_hp+", updated_at = "+updated_at+", created_at = "+created_at+", id_cust = "+id_cust+", fullname = "+fullname+", avatar = "+avatar+", limit_pinjam = "+limit_pinjam+", email = "+email+", username = "+username+"]";
    }
}
