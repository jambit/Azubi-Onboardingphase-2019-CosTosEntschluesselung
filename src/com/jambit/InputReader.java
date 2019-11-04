package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {

    /**
     * reads a .txt file and returns the content as a StringArray
     *
     * @param fileName the path&filename of the .txt file from which the content gets returned
     * @return the content of the .txt file as a StringArray
     * @throws IOException
     */
    StringArray readFile(String fileName) throws IOException {

        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        StringArray content = new StringArray();
        String line;

        do {
            line = br.readLine();
            if (line != null) {
                content.add(line);
//                System.out.println(line);
            }
        }
        while (line != null);
        br.close();
        return content;
    }

    /**
     * prints the content of a .txt file to the console
     *
     * @param fileName the path&filename of the .txt file from which the content printed on the console
     * @throws IOException
     */
    void showContentOfFile(String fileName) throws IOException {

        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;

        do {
            line = br.readLine();
            if (line != null) {
                System.out.println(line);
            }
        }
        while (line != null);
        br.close();
    }
}
