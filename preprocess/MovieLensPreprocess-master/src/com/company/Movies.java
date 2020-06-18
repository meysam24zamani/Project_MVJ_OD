package com.company;

import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Movies {

   private String movieid;
   private String movietitle;
   private String releasedate;
    private String genere1;
    private String genere2;
    private String genere3;

    public String getMovieid() {
        return movieid;
    }

    public String getMovietitle() {
        return movietitle;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public String getGenere1() {
        return genere1;
    }

    public String getGenere2() {
        return genere2;
    }

    public String getGenere3() {
        return genere3;
    }

    Movies(){
        movieid="";
        movietitle="";
        releasedate="";
        genere1="";
        genere2="";
        genere3="";
    }

    Movies(String mid,String mti,String date,String g1,String g2, String g3){

        movieid=mid;
        movietitle=mti;
        releasedate=date;
        genere1=g1;
        genere2=g2;
        genere3=g3;

    }


    public Movies ConvertoMovie(String[] data) throws ParseException {

movieid=data[0];
movietitle=data[1];
releasedate=data[2];
ArrayList<String> genereNames = new ArrayList<String>();
        genereNames.add("basura");
        genereNames.add("basura");
        genereNames.add("basura");
        genereNames.add("unknown");
        genereNames.add("Action");
        genereNames.add("Adventure");
        genereNames.add("Animation");
        genereNames.add("Children");
        genereNames.add("Comedy");
        genereNames.add("Crime");
        genereNames.add("Documentary");
        genereNames.add("Drama");
        genereNames.add("Fantasy");
        genereNames.add("FilmNoir");
        genereNames.add("Horror");
        genereNames.add("Musical");
        genereNames.add("Mystery");
        genereNames.add("Romance");
        genereNames.add("SciFi");
        genereNames.add("Thriller");
        genereNames.add("war");
        genereNames.add("Western");
        ArrayList<String> genere=new ArrayList<>();
        for(int i=3;i<=21;i++){

            if(data[i].equals("1")) genere.add(genereNames.get(i));

        }
        genere.add("");
        genere.add("");
        genere.add("");
        genere1=genere.get(0);
        genere2=genere.get(1);
        genere3=genere.get(2);
        movietitle = movietitle.replaceAll("\\(.*\\)", "");
        //movietitle=movietitle.replaceAll("\\s+","");
        releasedate = releasedate.replaceAll("[^\\d.]", "");
        System.out.println(releasedate);
        if(releasedate.length()==6) releasedate=releasedate.substring(2);
        movietitle = movietitle.replaceAll("[^a-zA-Z0-9]", "");
        Movies b= new Movies(movieid,movietitle,releasedate,genere1,genere2,genere3);
return b;
    }

}
