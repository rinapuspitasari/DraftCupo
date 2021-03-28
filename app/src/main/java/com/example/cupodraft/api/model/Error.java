package com.example.cupodraft.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Error {
    @SerializedName("email")
    @Expose
    private List<String> email = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    @SerializedName("password")
    @Expose
    private List<String> password = null;

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    @SerializedName("fullname")
    @Expose
    private List<String> fullname = null;

    public List<String> getFullname() {
        return fullname;
    }

    public void setName(List<String> fullname) {
        this.fullname = fullname;
    }

    @SerializedName("username")
    @Expose
    private List<String> username = null;

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }
}
