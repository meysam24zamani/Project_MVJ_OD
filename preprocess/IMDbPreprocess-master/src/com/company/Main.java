package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Movies> movies = new ArrayList<>();
        String row1;
        int number = 0;
        File csvFile = new File("moviesdesc.csv");
        FileWriter writer = new FileWriter("moviesdesc2.csv");
        if (csvFile.isFile()) {

            BufferedReader csvReader = new BufferedReader(new FileReader("moviesdesc.csv"));
            while ((row1 = csvReader.readLine()) != null) {

                String[] data = row1.split("/");

                if (number == 0) {

                    String header = "Company,Country,Director,Genre,Title,Runtime,Writer,Year\n";
                    writer.write(header);
                    number = number + 1;
                } else {

                    for(int i=0;i<data.length;i++){

                        if(data[i].startsWith(","))data[i]=data[i].substring(1);
                        else if(data[i].startsWith("\""))data[i]=data[i].substring(1);
                        else if(data[i].startsWith("\",")) data[i]=data[i].substring(2);
                    }
                    if(data.length==8) {
                        data[4] = data[4].replaceAll("[^a-zA-Z0-9]", "");
                        Movies b = new Movies(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                        writer.write(b.getCompany() + "," + b.getCountry() + "," + b.getDirector() + "," + b.getGenre() + "," + b.getName() + "," + b.getRuntime() + "," + b.getWriter() + "," + b.getYear() + "\n");
                        number = number + 1;
                    }
                }
            }

            csvReader.close();
            writer.close();
        }
    }
}
