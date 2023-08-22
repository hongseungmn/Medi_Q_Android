package com.kosmo.kosmo.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class UserDTO implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("pasword")
    private String pasword;
    @Expose
    @SerializedName("birth")
    private String birth;
    @Expose
    @SerializedName("gender")
    private String gender;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("regDate")
    private String regDate;
    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("inactive_date")
    private String inactive_date;
    @Expose
    @SerializedName("site")
    private String site;
    @Expose
    @SerializedName("social_Fl")
    private String social_Fl;

    public UserDTO() {
    }

    public UserDTO(String id, String name, String pasword, String birth, String gender, String email, String regDate, String active, String inactive_date, String site, String social_Fl) {
        this.id = id;
        this.name = name;
        this.pasword = pasword;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.regDate = regDate;
        this.active = active;
        this.inactive_date = inactive_date;
        this.site = site;
        this.social_Fl = social_Fl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getInactive_date() {
        return inactive_date;
    }

    public void setInactive_date(String inactive_date) {
        this.inactive_date = inactive_date;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSocial_Fl() {
        return social_Fl;
    }

    public void setSocial_Fl(String social_Fl) {
        this.social_Fl = social_Fl;
    }
}
