package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String row1,row2,row3;
        int number = 0;
        ArrayList <Integer> matches = new ArrayList<>();
        int counter=0;
        File csvFile = new File("usermovies2.csv");
        if (csvFile.isFile()) {
            BufferedReader csvReader = new BufferedReader(new FileReader("movies10k.csv"));
            row1 = csvReader.readLine();
            while ((row1 = csvReader.readLine()) != null) {
                BufferedReader csvReader2 = new BufferedReader(new FileReader("movies.csv"));
                row2 = csvReader2.readLine();
                while ((row2 = csvReader2.readLine()) != null) {
                    String[] data = row1.split(",");
                    String[] data2 = row2.split(",");
                    if (number == 0) {

                        number = number + 1;
                    } else {

                        data[1]=data[1].replaceAll("\\(.*\\)", "");
                        data[1]=data[1].replaceAll("[^a-zA-Z0-9]","");
                        data2[6]=data2[6].replaceAll("[^a-zA-Z0-9]","");
                        if (data[1].equals(data2[6])){
                            counter=counter+1;
                            matches.add(Integer.parseInt(data[0]));
                        }

                    }
                }
                csvReader2.close();
            }
            System.out.println(counter);
            csvReader.close();
            int counter2=0;
            BufferedReader csvReader3 = new BufferedReader(new FileReader("usermovies.csv"));
            row3=csvReader3.readLine();
            while ((row3 = csvReader3.readLine()) != null) {
                String[] data3 = row3.split(",");
                for(int i=0;i<matches.size();i++){

                    if (matches.get(i)==Integer.parseInt(data3[1])) {
                        counter2++;
                    }
                }

            }
System.out.println(counter2);
        }
    }
}
