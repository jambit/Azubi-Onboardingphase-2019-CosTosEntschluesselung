package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.Random;

public class EncryptionHelper {

    public String keysetString = UserInterface.keysetString;

    StringArray encrypt(StringArray decryptedContent, int key1) {
        StringArray encryptedContent = new StringArray();
        String[] completeArray = decryptedContent.getArray();
        for (int i = 0; i < completeArray.length; i++) {
            if (completeArray[i] != null) {
                String encryptedMessage = encrypt(completeArray[i], key1);
                encryptedContent.add(encryptedMessage);
            }
        }
        return encryptedContent;
    }

    StringArray encrypt(StringArray decryptedContent, int key, int seed) {
        randomGenerator(seed);
        StringArray encryptedContent = new StringArray();
        String[] completeArray = decryptedContent.getArray();
        for (int i = 0; i < completeArray.length; i++) {
            if (completeArray[i] != null) {
                String encryptedMessage = encrypt(completeArray[i], key, seed);
                encryptedContent.add(encryptedMessage);
            }
        }
        return encryptedContent;
    }


    String encrypt(String decryptedLine, int key) {
        int keysetLength = keysetString.length();
        int lineLength = decryptedLine.length();
        int charLocationInKeyset;
        int lookAtKeysetPos;
        char encryptedChar;
        String encryptedLine = "";

        for (int i = 0; i < lineLength; i++) {
            char currentChar = decryptedLine.charAt(i);
            charLocationInKeyset = keysetString.indexOf(currentChar);
            lookAtKeysetPos = charLocationInKeyset + key;

            if (lookAtKeysetPos + 1 > keysetLength) {
                lookAtKeysetPos = lookAtKeysetPos - keysetLength;
            }
            encryptedChar = keysetString.charAt(lookAtKeysetPos);
            encryptedLine = encryptedLine + encryptedChar;
        }
        return encryptedLine;
    }

    public String encrypt(String decryptedLine, int key1, int seed) {
        int keysetLength = keysetString.length();
        int lineLength = decryptedLine.length();
        int charLocationInKeyset;
        int lookAtKeysetPos;
        char encryptedChar;
        String encryptedLine = "";

        for (int i = 0; i < lineLength; i++) {
            char currentChar = decryptedLine.charAt(i);
            charLocationInKeyset = keysetString.indexOf(currentChar);
            lookAtKeysetPos = charLocationInKeyset + key1;

            if (lookAtKeysetPos + 1 > keysetLength) {
                lookAtKeysetPos = lookAtKeysetPos - keysetLength;
            }
            encryptedChar = keysetString.charAt(lookAtKeysetPos);
            encryptedLine = encryptedLine + encryptedChar;
        }
        return encryptedLine;
    }


    private String swapIndex(String str, int i1, int i2) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i1, str.charAt(i2));
        sb.setCharAt(i2, str.charAt(i1));
        return sb.toString();
    }

    /**
     * Randomizes the character set
     *
     * @param seed seed to use for the randomization
     */
    public String randomGenerator(int seed) {
        Random generator = new Random(seed);
        for (int i = 1; i < generator.nextInt(1000); i++) {
            for (int j = 0; j < keysetString.length(); j++) {
                generator = new Random(seed + (i * 3 * (j + 1)));
                int randNum = generator.nextInt(keysetString.length() - 1);
                keysetString = swapIndex(keysetString, j, randNum);
            }
        }
        return keysetString;
    }
}

