package com.jambit;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class UserInterface {

    /**
     * the core of the program which gives the user the option of choosing between encryption and decryption and lets
     * the user enter the seed and key. It also gives the user the additional option of generating a random seed and
     * key if he doesn't want to enter his own seed and key
     *
     * @throws IOException
     */
    void startUi() throws IOException {
        Scanner sc = new Scanner(System.in);

        InputReader inputReader = new InputReader();
        //TODO: Handle user input for file path (change default file path)

        while (true) {
            System.out.println("do you want to encrypt[e] or decrypt[d] the file\n");
            String input = sc.next();
            System.out.println();

            switch (input) {
                case "e":
                    System.out.println("do you want to generate a key and seed? [y] [n]\n");
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
                    System.out.println();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * encrypts the default decrypted file (Constants.DEFAULT_DECRYPTED_FILE_PATH) with a GIVEN seed and key,
     * saves the encrypted result in the default decrypted file (Constants.DEFAULT_ENCRYPTED_FILE_PATH)
     *
     * @throws IOException
     */
    private void encryptWithoutRandoms() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your [seed]:[key]\n");
        String input = sc.next();
        CodecUtility seedAndKey = splitSeedAndKey(input);
        int seed = seedAndKey.getSeed();
        int key = seedAndKey.getKey();
        encryptFromToWithSeedAndKey(Constants.DEFAULT_DECRYPTED_FILE_PATH, Constants.DEFAULT_ENCRYPTED_FILE_PATH, seed, key);
    }

    /**
     * encrypts the default decrypted file (Constants.DEFAULT_DECRYPTED_FILE_PATH) with a RANDOM GENERATED seed and key,
     * saves the encrypted result in the default decrypted file (Constants.DEFAULT_ENCRYPTED_FILE_PATH) and prints
     * the seed and key out on the console
     *
     * @throws IOException
     */
    private void encryptWithRandoms() throws IOException {
        CodecUtility codecUtility = new CodecUtility();
        codecUtility.setKey(generateRandomKey());
        codecUtility.setSeed(generateRandomSeed());
        System.out.println("\nyour seed and key are: " + codecUtility.getSeed() + ":" + codecUtility.getKey() + "\n");
        int seed = codecUtility.getSeed();
        int key = codecUtility.getKey();
        encryptFromToWithSeedAndKey(Constants.DEFAULT_DECRYPTED_FILE_PATH, Constants.DEFAULT_ENCRYPTED_FILE_PATH, seed, key);
    }

    /**
     * encrypts the file from a given path with the given seed and key and saves the encrypted result in another given
     * file
     *
     * @param pathFrom the path of the decrypted file
     * @param pathTo   the path of the file in which the encrypted content should be saved
     * @param seed     the seed to encrypt the decrypted content
     * @param key      the key to encrypt the decrypted content
     * @throws IOException
     */
    private void encryptFromToWithSeedAndKey(String pathFrom, String pathTo, int seed, int key) throws IOException {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();
        EnDeCryption enDeCryption = new EnDeCryption();
        outputWriter.writeFile(enDeCryption.encrypt(inputReader.readFile(pathFrom), key, seed), pathTo);
    }

    /**
     * decrypts the default encrypted file (Constants.DEFAULT_ENCRYPTED_FILE_PATH)
     * and saves the result in the default decrypted file (Constants.DEFAULT_DECRYPTED_FILE_PATH)
     */
    private void decrypt(CodecUtility codecUtility) throws IOException {
        decryptFromToWithSeedAndKey(Constants.DEFAULT_ENCRYPTED_FILE_PATH,
                Constants.DEFAULT_DECRYPTED_FILE_PATH, codecUtility.getSeed(), codecUtility.getKey());
    }

    /**
     * decrypts the file from a given path with the given seed and key and saves the result in another given file
     *
     * @param pathFrom the path of the encrypted file
     * @param pathTo   the path of the file in which the decrypted content should be saved
     * @param seed     the seed to decrypt the encrypted content
     * @param key      the key to decrypt the encrypted content
     * @throws IOException
     */
    private void decryptFromToWithSeedAndKey(String pathFrom, String pathTo, int seed, int key) throws IOException {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();
        EnDeCryption enDeCryption = new EnDeCryption();
        outputWriter.writeFile(enDeCryption.decrypt(inputReader.readFile(pathFrom), key, seed), pathTo);
    }

    /**
     * asks for the seed and key input and returns the input as a String (hast to be in this format: [seed]:[key] and
     * both seed and key have to be int
     *
     * @return
     */
    private String enterSeedAndKey() {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your [seed]:[key]");
        String input = sc.next();
        System.out.println();
        return input;
    }

    /**
     * takes the input String and separates in the two int numbers for the key ans seed
     *
     * @param keyString the input should be a String (2 int numbers seperated by a ":")
     * @return a codecUtility containing the key and seed
     */
    private CodecUtility splitSeedAndKey(String keyString) {
        String[] keyAndSeed = keyString.split(":");
        CodecUtility codecUtility = new CodecUtility();
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

    /**
     * generates a random int between 1 and the length of the keyset (saved under Constants.KEYSETSTRING) -1
     *
     * @return a random generated int between 1 and the length of the keyset (saved under Constants.KEYSETSTRING) -1
     */
    private int generateRandomKey() {
        int min = 1;
        int max = Constants.KEYSETSTRING.length() - 1;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * generates a random seed between 1 and 2147483647
     *
     * @return a random int between 1 and 2147483647
     */
    private int generateRandomSeed() {
        int max = 2147483646;
        Random r = new Random();
        return r.nextInt(max) + 1;
    }
}