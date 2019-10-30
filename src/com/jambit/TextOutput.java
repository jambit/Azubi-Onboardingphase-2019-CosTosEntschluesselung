package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextOutput {


    public void writeFile(StringArray decryptedContend) throws IOException {
        FileWriter fw = new FileWriter("DecryptedText.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

//        bw.newLine();
//        bw.append(decryptedContend);

        String decryptedContendArray[] = decryptedContend.getArray();

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
