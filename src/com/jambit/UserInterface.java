package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class UserInterface {

    void startUi() throws IOException {

        Scanner sc = new Scanner(System.in);
        InputReader inputReader = new InputReader();
        //TODO: Handle user input for file path (change default file path)

        while (true) {
            System.out.println("do you want to encrypt[e] or decrypt[d] the file");
            String input = sc.next();

            switch (input) {
                case "e":
                    System.out.println("do you want to generate a key and seed? [y] [n]");
                    input = sc.next();

                    switch (input) {
                        case "y":
                            encryptWithRandoms();
                            System.out.println("your encrypted text:\n");
                            inputReader.showContentOfFile(Constants.DEFAULT_ENCRYPTED_FILE_PATH);
                            System.out.println();
                            break;

                        case "n":
                            encryptWithoutRandoms();
                            System.out.println("your encrypted text:\n");
                            inputReader.showContentOfFile(Constants.DEFAULT_ENCRYPTED_FILE_PATH);
                            System.out.println();
                            break;

                        default:
                            break;
                    }
                    break;

                case "d":
                    String seedAndKeyInput = enterSeedAndKey();
                    decrypt(splitSeedAndKey(seedAndKeyInput));
                    inputReader.showContentOfFile(Constants.DEFAULT_DECRYPTED_FILE_PATH);
                    break;

                default:
                    break;
            }
        }
    }


    private void encryptWithoutRandoms() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your [seed]:[key]");
        String input = sc.next();
        CodecUtility seedAndKey = splitSeedAndKey(input);
        int seed = seedAndKey.getSeed();
        int key = seedAndKey.getKey();
        encryptFromToWithSeedAndKey(Constants.DEFAULT_DECRYPTED_FILE_PATH, Constants.DEFAULT_ENCRYPTED_FILE_PATH, seed, key);
    }

    private void encryptWithRandoms() throws IOException {
        CodecUtility codecUtility = new CodecUtility();

        //todo understand what we did here
//        CodecUtility randomKeyAndSeed = null;  //why = null??
//        randomKeyAndSeed.setKey(generateRandomKey());
//        randomKeyAndSeed.setSeed(generateRandomSeed());
//        System.out.println("your seed and key are: " + randomKeyAndSeed.getSeed() + ":" + randomKeyAndSeed.getKey());
//        int seed = codecUtility.getSeed();
//        int key = codecUtility.getKey();

        codecUtility.setKey(generateRandomKey());
        codecUtility.setSeed(generateRandomSeed());
        System.out.println("\nyour seed and key are: " + codecUtility.getSeed() + ":" + codecUtility.getKey()+"\n");
        int seed = codecUtility.getSeed();
        int key = codecUtility.getKey();

        encryptFromToWithSeedAndKey(Constants.DEFAULT_DECRYPTED_FILE_PATH, Constants.DEFAULT_ENCRYPTED_FILE_PATH, seed, key);
    }

    private void encryptFromToWithSeedAndKey(String pathFrom, String pathTo, int seed, int key) throws IOException {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();
        EnDeCryption enDeCryption = new EnDeCryption();
        outputWriter.writeFile(enDeCryption.encrypt(inputReader.readFile(pathFrom), key, seed), pathTo);
    }

    private void decrypt(CodecUtility codecUtility) throws IOException {
        decryptFromToWithSeedAndKey(Constants.DEFAULT_ENCRYPTED_FILE_PATH,
                Constants.DEFAULT_DECRYPTED_FILE_PATH, codecUtility.getSeed(), codecUtility.getKey());
    }

    private void decryptFromToWithSeedAndKey(String pathFrom, String pathTo, int seed, int key) throws IOException {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();
        EnDeCryption enDeCryption = new EnDeCryption();
        outputWriter.writeFile(enDeCryption.decrypt(inputReader.readFile(pathFrom), key, seed), pathTo);
    }

    private String enterSeedAndKey() {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your [seed]:[key]");
        String input = sc.next();
        return input;
    }

    private CodecUtility splitSeedAndKey(String keyString) {
        String[] keyAndSeed = keyString.split(":");
        CodecUtility codecUtility = new CodecUtility();
        //todo check if it works
        try {
            if (keyAndSeed.length == 2) {
                codecUtility.setKey(Integer.parseInt(keyAndSeed[1]));
                codecUtility.setSeed(Integer.parseInt(keyAndSeed[0]));
            } else if (keyAndSeed.length == 1) {
                codecUtility.setKey(Integer.parseInt(keyAndSeed[0]));
            }
        } catch (NumberFormatException e) {
            System.err.println("\ninvalid seed & key format");
        }
        return codecUtility;
    }

    private int generateRandomKey() {
        int min = 1;
        int max = Constants.KEYSETSTRING.length() - 1;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private int generateRandomSeed() {
        int max = 2147483646;
        Random r = new Random();
        return r.nextInt(max) + 1;
    }




    //TODO: remove redundant code

    private StringArray readFile(String filePath) throws IOException {
        InputReader inputReader = new InputReader();
        return inputReader.readFile(filePath);
    }

    private StringArray printSeedMenue() throws IOException {
        Scanner sc = new Scanner(System.in);
        InputReader inputReader = new InputReader();

        System.out.println("please enter your [seed]:[key]");

        //Todo: Extract to METHOD
        final StringArray decryptedFileContent = inputReader.readFile("DecryptedText.txt");
        return decryptedFileContent;
    }

    boolean checkFileIfExists(String pathname) {
        boolean fileExists;
        File file = new File(pathname);
        fileExists = file.exists();
        return fileExists;
    }

    void printContent(StringArray content) {
        // prints out the content in console
        for (int i = 0; i < content.getSize(); i++) {
            System.out.println(content.get(i));
        }
    }

    void writeDecryptedContentInFile(StringArray decryptedContent)
            throws IOException {
        CustomFileWriter customFileWriter = new CustomFileWriter();
        customFileWriter.write(decryptedContent, Constants.DEFAULT_DECRYPTED_FILE_PATH);
    }

    void writeEncryptedContentToFile(StringArray encryptedContent)
            throws IOException {
        CustomFileWriter customFileWriter = new CustomFileWriter();
        customFileWriter.write(encryptedContent, Constants.DEFAULT_ENCRYPTED_FILE_PATH);
    }
}
