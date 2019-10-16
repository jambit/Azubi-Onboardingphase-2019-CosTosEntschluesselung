package com.jambit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadTheEncryptedFile {

    String readThisLine(int linecount) throws IOException {
        String line = null;
        int countToLine = 1;
        File encryptedTextFile = new File("EncryptedText.txt");

        BufferedReader br = new BufferedReader(new FileReader(encryptedTextFile));
        while (countToLine < linecount) {
            linecount++;
            line = br.readLine();
        }

//        BufferedReader br = new BufferedReader(new FileReader(encryptedTextFile));
//        line = br.readLine();
        return line;
    }
}
