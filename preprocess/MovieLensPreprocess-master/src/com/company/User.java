package com.company;

public class User {

    private String userid;
    private String age;
    private String gender;
    private String occupation;


    public User() {

        userid="";
        age="";
        gender="";
        occupation="";

    }

    public User(String userid, String age, String gender, String occupation) {
        this.userid = userid;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
    }

    public String getUserid() {
        return userid;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getOccupation() {
        return occupation;
    }
}
