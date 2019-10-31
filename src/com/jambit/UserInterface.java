package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class UserInterface {
    int key;
    int seed = 0;
    StringArray decryptedContent;
    StringArray encryptedContent;
    String pathname;
    public static String keysetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÜÖabcdefghijklmnopqrstuvwxyzäüöß0123456789,.!?\"§$%&/" +
            "()" +
            "=+-*\\_#~<>| ";


    void startUi() throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("do you want to encrypt[e] or decrypt[d] the file");
            String input = sc.next();
            switch (input) {
                case "e":
                    System.out.println("do you want to generate a key and seed? [y] [n]");
                    String doYou = sc.next();
                    switch (doYou) {
                        case "y":
                            key = getRandomKey();
                            seed = getRandomSeed();
                            System.out.println("your seed and key are: " + seed + ":" + key);
                            break;

                        case "n":
                            final StringArray decryptedFileContent = printSeedMenue();

                            EncryptionHelper encryptionHelper = new EncryptionHelper();
                            if (seed != 0) {
                                encryptedContent = encryptionHelper.encrypt(decryptedFileContent, key, seed);

                            } else {
                                encryptedContent = encryptionHelper.encrypt(decryptedFileContent, key);
                            }
                            break;

                        default:
                            break;
                    }
                    if (checkFile("DecryptedText.txt")) {

                        //todo use encrypt method
                    } else {
                        System.out.println("please enter a valid path + filename");
                        pathname = sc.next();

                        while (checkFile(pathname) == false) {
                            System.out.println("please enter a valid path + filename");
                            pathname = sc.next();
                        }
                    }
                    printContent(encryptedContent);
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
                    printContent(decryptedContent);
                    writeDecryptedContentInFile(decryptedContent);
                    break;

                default:
                    break;
            }
        }
    }

    private StringArray printSeedMenue() throws IOException {
        Scanner sc = new Scanner(System.in);
        InputReader inputReader = new InputReader();

        System.out.println("please enter your [seed]:[key]");

        //Todo: Extract to METHOD
        final StringArray decryptedFileContent = inputReader.readFile("DecryptedText.txt");
        System.out.println("enter the decryption key:");

        String keyString = sc.next();
        String[] keyAndSeed1 = getKeyAndSeed(keyString);
        return decryptedFileContent;
    }

    private String[] getKeyAndSeed(String keyString) {
        String[] keyAndSeed = keyString.split(":");

        if (keyAndSeed.length == 2) {
            key = Integer.parseInt(keyAndSeed[1]);
            seed = Integer.parseInt(keyAndSeed[0]);
        } else {
            try {
                key = Integer.parseInt(keyAndSeed[0]);
            }
            catch (NumberFormatException e){
                System.out.println("THE HELL IS OPEN");
            }
        }
        return keyAndSeed;
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
            encryptedContent = encryptionHelper.encrypt(decryptedFileContent, key, seed);

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

    void printContent(StringArray content) {
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

    private int getRandomKey() {
        int min = 1;
        int max = keysetString.length() - 1;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private int getRandomSeed() {
        int min = -2147483648;
        int max = 2147483647;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private boolean checkSeedKeyFormat(String seedKey) {

        String[] keyAndSeed = seedKey.split(":");

        if (keyAndSeed.length == 2) {
            key = Integer.parseInt(keyAndSeed[1]);
            seed = Integer.parseInt(keyAndSeed[0]);
        } else {
            key = Integer.parseInt(keyAndSeed[0]);
        }
        return true;

    }
}