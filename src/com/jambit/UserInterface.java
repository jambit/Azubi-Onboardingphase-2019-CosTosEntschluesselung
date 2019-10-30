package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    int key;
    int seed = 0;
    StringArray decryptedContent;
    StringArray encryptedContent;
    String pathname;


    void startUi() throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("do you want to encrypt[e] or decrypt[d] the file");
            String input = sc.next();
            switch (input) {
                case "e":
                    if (checkFile("DecryptedText.txt")) {
                        enterKeyAndEncrypt();
                    } else {
                        System.out.println("please enter a valid path + filename");
                        pathname = sc.next();

                        while (checkFile(pathname) == false) {
                            System.out.println("please enter a valid path + filename");
                            pathname = sc.next();
                        }
                    }
                    printContend(encryptedContent);
                    writeEncryptedContentInFile(encryptedContent);
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
                    printContend(decryptedContent);
                    writeDecryptedContentInFile(decryptedContent);
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

    void enterKeyAndEncrypt() throws IOException {
        InputReader inputReader = new InputReader();
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

        EncryptionHelper encryptionHelper = new EncryptionHelper();
        if (seed != 0) {
            encryptedContent = encryptionHelper.encrypt(decryptedFileContent, key,
                    seed);
        } else {
            encryptedContent = encryptionHelper.encrypt(decryptedFileContent, key);
        }
    }

    void enterKeyAndDecrypt() throws IOException {

        InputReader inputReader = new InputReader();
        final StringArray encryptedFileContent = inputReader.readFile("EncryptedText.txt");

        System.out.println("enter a key (format: 12345:67 or just 67):");
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
            decryptedContent = decryptionHelper.decrypt(encryptedFileContent, key,
                    seed);
        } else {
            decryptedContent = decryptionHelper.decrypt(encryptedFileContent, key);
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
        textOutput.writeFileDecryptedContend(decryptedContent);
    }

    void writeEncryptedContentInFile(StringArray encryptedContent) throws IOException {
        TextOutput textOutput = new TextOutput();
        textOutput.writeFileEncryptedContend(encryptedContent);
    }
}
