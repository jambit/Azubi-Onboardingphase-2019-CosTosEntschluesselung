package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    int key;
    int seed = 0;
    StringArray content;
    String pathname;


    void startUi() throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("do you want to encrypt[e] or decrypt[d] the file");
            String input = sc.next();
            switch (input) {
                case "e":
                    //Todo add encryption
                    break;

                case "d":
                    if (checkFile("EncryptedText.txt")) {
                        enterKeyAndDecrypt();
                    } else {
                        System.out.println("please enter a valid path + filename");
                        pathname = sc.next();

                        while (checkFile(pathname) == false) {
                            System.out.println("please enter a valid path + filename");
                            pathname = sc.next();
                        }
                    }
                    printContend(content);
                    writeDecryptedContentInFile(content);
                    break;

                default:
                    break;
            }
        }
    }

    boolean checkFile(String pathname) {
        boolean fileExists;
        File file = new File(pathname);
        fileExists = file.exists();
        return fileExists;
    }


    void enterKeyAndDecrypt() throws IOException {

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

        if (seed != 0) {
            content = decryptionHelper.decrypt(encryptedFileContent, key,
                    seed);
        } else {
            content = decryptionHelper.decrypt(encryptedFileContent, key);
        }
    }


    void printContend(StringArray content) {
        // prints out the decrypted text
        for (int i = 0; i < content.getSize(); i++) {
            System.out.println(content.get(i));
        }
    }


    void writeDecryptedContentInFile(StringArray decryptedContent) throws IOException {
        TextOutput textOutput = new TextOutput();
        textOutput.writeFile(decryptedContent);
    }
}
