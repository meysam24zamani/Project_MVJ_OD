package com.company;

public class Movies {

    private String Company;
    private String Country;
    private String Director;
    private String Genre;
    private String Name;
    private String Runtime;
    private String writer;
    private String year;

    public Movies(String company, String country, String director, String genre, String name, String runtime, String writer, String year) {
        Company = company;
        Country = country;
        Director = director;
        Genre = genre;
        Name = name;
        Runtime = runtime;
        this.writer = writer;
        this.year = year;
    }

    public String getCompany() {
        return Company;
    }




    public String getCountry() {
        return Country;
    }

    public String getDirector() {
        return Director;
    }

    public String getGenre() {
        return Genre;
    }

    public String getName() {
        return Name;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getWriter() {
        return writer;
    }

    public String getYear() {
        return year;
    }
}
