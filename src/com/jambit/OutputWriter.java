package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;

public class OutputWriter {

    /**
     * saves the given content in the given file (and removes the previous content)
     *
     * @param content the content which gets saved in the .txt file
     * @param path    the path & filename of the file in which the content should be saved
     * @throws IOException
     */
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

    /**
     * deletes all content of a .txt file
     *
     * @param filePath the path of the file
     */
    public void clearFile(String filePath) {

        try {
            FileWriter fw = new FileWriter(filePath, false); //true für append!
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("");
            bw.close();
        } catch (IOException e) {

        }

    }
}
