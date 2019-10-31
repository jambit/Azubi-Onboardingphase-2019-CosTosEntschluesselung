package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import java.io.*;

public class OutputWriter {

    public void writeFile(StringArray content, String path) throws IOException {
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

    public void clearFile(String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        while (br != null) {
            bw.append(null);
            bw.newLine();
        }
        bw.close();
    }

}
