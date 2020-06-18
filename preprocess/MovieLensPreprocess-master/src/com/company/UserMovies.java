package com.company;

public class UserMovies {

    private String movietitle;
    private String releasedate;
    private String genere1;
    private String genere2;
    private String genere3;
    private String userid;
    private String age;
    private String gender;
    private String occupation;
    private int Grade;

    public UserMovies(String movietitle, String releasedate, String genere1, String genere2,String Userid,String age, String gender, String occupation, int grade) {

        this.movietitle = movietitle;
        this.releasedate = releasedate;
        this.genere1 = genere1;
        this.genere2 = genere2;
        this.genere3 = genere3;
        userid=Userid;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        Grade = grade;
    }

    public String getCsvFile(){

        String line="";
       if(genere2!="") {
            line=movietitle+","+releasedate+","+genere1+","+genere2+","+userid+","+age+","+gender+","+occupation+","+Grade+"\n";
       }
       else line=movietitle+","+releasedate+","+genere1+","+","+userid+","+age+","+gender+","+occupation+","+Grade+"\n";

        return line;
    }
}
