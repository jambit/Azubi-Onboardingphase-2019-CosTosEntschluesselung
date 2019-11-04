package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {

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

     public void showContentOfFile(String fileName) throws IOException {

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


    //todo remove method
    StringArray readFile() throws IOException {

        FileReader fr = new FileReader("EncryptedText.txt");
        BufferedReader br = new BufferedReader(fr);
        StringArray content = new StringArray();
        String line;

        do {
            line = br.readLine();
            if (line != null) {
                content.add(line);
 //               System.out.println(line);
            }
        }
        while (line != null);
        br.close();
        return content;
    }
}
