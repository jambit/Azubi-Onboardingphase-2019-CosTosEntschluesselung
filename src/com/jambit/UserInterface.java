package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    int key;
    int seed = 0;

    void enterKey() throws IOException {

       /* File tempFile = new File("C:\\Users\\ceisenschmid\\Downloads\\encrypted_file (2).txt");
        boolean exists = tempFile.exists();
        System.out.println(exists);*/

        InputReader inputReader = new InputReader();
        final StringArray encryptedFileContent = inputReader.readFile("EncryptedText.txt");
        final StringArray decryptedFileContent = inputReader.readFile("DecryptedText.txt");

        System.out.println("enter the decryption key:");
        Scanner scanner = new Scanner(System.in);
        String keyString = scanner.next();
        String[] keyAndSeed = keyString.split(":");

        if (keyAndSeed.length == 2) {
            key = Integer.parseInt(keyAndSeed[1]);
            seed = Integer.parseInt(keyAndSeed[0]);
        } else {
            key = Integer.parseInt(keyAndSeed[0]);
        }

        DecryptionHelper decryptionHelper = new DecryptionHelper();
        StringArray decryptFileContent;
        if (seed != 0) {
            decryptFileContent = decryptionHelper.decrypt(encryptedFileContent, key,
                    seed);
        } else {
            decryptFileContent = decryptionHelper.decrypt(encryptedFileContent, key);
        }

        // prints out the decrypted text
        for (int i = 0; i < decryptFileContent.getSize(); i++) {
            System.out.println(decryptFileContent.get(i));
        }
    }
}
