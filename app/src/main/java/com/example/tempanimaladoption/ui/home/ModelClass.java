package com.example.tempanimaladoption.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelClass implements Parcelable {

    private  String name;
    private String url;
    private String age,desc;
    private String atype,gender;
    private String status,id;

    ModelClass()
    {

    }

    public ModelClass(String id,String name, String url, String age, String desc, String atype, String gender, String status) {
        this.name = name;
        this.id = id;
        this.url = url;
        this.age = age;
        this.desc = desc;
        this.atype = atype;
        this.gender = gender;
        this.status=status;
    }

    protected ModelClass(Parcel in) {
        id = in.readString();
        name = in.readString();
        url = in.readString();
        age = in.readString();
        desc = in.readString();
        atype = in.readString();
        gender = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(age);
        dest.writeString(desc);
        dest.writeString(atype);
        dest.writeString(gender);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelClass> CREATOR = new Creator<ModelClass>() {
        @Override
        public ModelClass createFromParcel(Parcel in) {
            return new ModelClass(in);
        }

        @Override
        public ModelClass[] newArray(int size) {
            return new ModelClass[size];
        }
    };

    public String getAid() { return id; }

    public void setAid(String id) { this.id=id; };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
