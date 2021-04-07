package com.example.tempanimaladoption.ui.request;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelClassreq implements Parcelable {

    private  String id,phoneno,name,gender,age,url,atype,status,parentid;

    ModelClassreq()
    {

    }

    public ModelClassreq(String phoneno,String parentid , String id, String name, String url, String age, String atype, String gender, String status) {
        this.parentid = parentid;
        this.phoneno=phoneno;
        this.name = name;
        this.id = id;
        this.url = url;
        this.age = age;
        this.atype = atype;
        this.gender = gender;
        this.status=status;
    }

    protected ModelClassreq(Parcel in) {
        parentid = in.readString();
        phoneno = in.readString();
        id = in.readString();
        name = in.readString();
        url = in.readString();
        age = in.readString();
        atype = in.readString();
        gender = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(parentid);
        dest.writeString(phoneno);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(age);
        dest.writeString(atype);
        dest.writeString(gender);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelClassreq> CREATOR = new Creator<ModelClassreq>() {
        @Override
        public ModelClassreq createFromParcel(Parcel in) {
            return new ModelClassreq(in);
        }

        @Override
        public ModelClassreq[] newArray(int size) {
            return new ModelClassreq[size];
        }
    };

    public String getId() { return id; }

    public void setId(String id) { this.id=id; };



    public String getParentid() { return parentid; }

    public void setParentid(String parentid) { this.parentid=parentid; };

    public String getPhoneno() { return phoneno; }

    public void setPhoneno(String phoneno) { this.phoneno=phoneno; };

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
