package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextOutput {


    public void writeFileDecryptedContend(StringArray decryptedContent) throws IOException {
        FileWriter fw = new FileWriter("DecryptedText.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

//        bw.newLine();
//        bw.append(decryptedContent);

        String decryptedContentArray[] = decryptedContent.getArray();

        for (int i = 0; i < decryptedContentArray.length; i++) {

            if (decryptedContentArray[i] != null) {
                bw.append(decryptedContentArray[i]);
                bw.newLine();
            } else {
                bw.close();
            }
        }

    }

    public void writeFileEncryptedContend(StringArray encryptedContent) throws IOException {
        FileWriter fw = new FileWriter("DecryptedText.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

//        bw.newLine();
//        bw.append(decryptedContent);

        String decryptedContentArray[] = encryptedContent.getArray();

        for (int i = 0; i < decryptedContentArray.length; i++) {

            if (decryptedContentArray[i] != null) {
                bw.append(decryptedContentArray[i]);
                bw.newLine();
            } else {
                bw.close();
            }
        }

    }
}
