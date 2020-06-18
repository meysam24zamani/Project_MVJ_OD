package com.company;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        ArrayList<Movies> movies = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
            String row1;
            int number = 0;
            File csvFile = new File("movies.csv");
            FileWriter writer = new FileWriter("movies2.csv");
            if (csvFile.isFile()) {

                BufferedReader csvReader = new BufferedReader(new FileReader("movies.csv"));
                while ((row1 = csvReader.readLine()) != null) {

                    String[] data = row1.split(",");

                    if (number == 0) {

                        String header = "id" + "," + "MovieTitle" + "," + "ReleaseDate" + "," + "Genere1" + "," + "Genere2" + "," + "\n";
                        writer.write(header);
                        number = number + 1;
                    } else {

                        Movies b = new Movies();
                        Movies aux = b.ConvertoMovie(data);
                        movies.add(aux);
                        int k = 0;
                        if (aux.getGenere2() == "") {
                            String p = aux.getMovieid() + "," + aux.getMovietitle() + "," + aux.getReleasedate() + "," + aux.getGenere1() + "\n";
                            writer.write(p);
                        } else if (aux.getGenere2() != "") {
                            String p = aux.getMovieid() + "," + aux.getMovietitle() + "," + aux.getReleasedate() + "," + aux.getGenere1() + "," + aux.getGenere2() + "\n";
                            writer.write(p);
                        }
                    }
                }

csvReader.close();
writer.close();
            }

            String row2;
            int number2 = 0;
            File csvFile2 = new File("user.csv");
            if (csvFile2.isFile()) {

                BufferedReader csvReader = new BufferedReader(new FileReader("user.csv"));
                while ((row2 = csvReader.readLine()) != null) {

                    String[] data = row2.split(",");

                    if (number == 0) {

                        number = number + 1;
                    } else {

                        User aux = new User(data[0],data[1],data[2],data[3]);
                        users.add(aux);

                    }
                }
                csvReader.close();


            String row3;
            int number3 = 0;
            File csvFile3 = new File("usermovies.csv");
            if (csvFile3.isFile()) {

                BufferedReader csvReader2 = new BufferedReader(new FileReader("usermovies.csv"));
                FileWriter writer2 = new FileWriter("usermovies2.csv");
                while ((row3 = csvReader2.readLine()) != null) {

                    String[] data = row3.split(",");

                    if (number2 == 0) {
                        String header="MovieTitle"+","+"ReleaseDate"+","+"Genere1"+","+"Genere2"+","+"Userid"+","+"AgeGrader"+","+"GenderGrader"+","+"OccupationGrader"+","+"RatingGrader"+"\n";
                        writer2.write(header);
                        number2 = number2 + 1;
                    } else {


                       if(Integer.parseInt(data[1])<movies.size() && Integer.parseInt(data[0])<users.size()) {
                           Movies moviefound = movies.get(Integer.parseInt(data[1]));
                           User userfound = users.get(Integer.parseInt(data[0]));
                           UserMovies joined = new UserMovies(moviefound.getMovietitle(), moviefound.getReleasedate(), moviefound.getGenere1(), moviefound.getGenere2(),userfound.getUserid(), userfound.getAge(),
                                   userfound.getGender(), userfound.getOccupation(), Integer.parseInt(data[2]));
                           String toWrite = joined.getCsvFile();
                           writer2.write(toWrite);
                       }
                    }
                }
                csvReader2.close();
                writer2.close();
            }
        }
    }
}