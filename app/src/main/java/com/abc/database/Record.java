package com.abc.database;

import android.widget.EditText;

public class Record
{
    String name,store_name,password,email,ph_no;

    public Record(String name, String sname, String pwd, String email, String ph_no) {
        this.name = name;
        this.store_name = sname;
        this.password = pwd;
        this.email = email;
        this.ph_no = ph_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPh_no() {
        return ph_no;
    }

    public void setPh_no(String ph_no) {
        this.ph_no = ph_no;
    }
}
