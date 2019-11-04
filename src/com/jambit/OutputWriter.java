package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;

public class OutputWriter {

    public void writeFile(StringArray content, String path) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);
        clearFile(path);
        String decryptedContendArray[] = content.getArray();

        for (int i = 0; i < decryptedContendArray.length; i++) {

            if (decryptedContendArray[i] != null) {
                bw.append(decryptedContendArray[i]);
                bw.newLine();
            } else {
                bw.close();
            }
        }
    }

    public void clearFile(String filePath) {

        try {
            FileWriter fw = new FileWriter(filePath, false); //true für append!
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("");
            bw.close();
        } catch (IOException e) {

        }

//        FileWriter fw = new FileWriter(filePath, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//        BufferedReader br = new BufferedReader(new FileReader(filePath));
//
//        while (br != null) {
//            bw.append(null);
//            bw.newLine();
//        }
//        bw.close();
    }

    public void addToFile(StringArray content, String path) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);

        String decryptedContendArray[] = content.getArray();

        for (int i = 0; i < decryptedContendArray.length; i++) {

            if (decryptedContendArray[i] != null) {
                bw.append(decryptedContendArray[i]);
                bw.newLine();
            } else {
                bw.close();
            }
        }
    }

}
