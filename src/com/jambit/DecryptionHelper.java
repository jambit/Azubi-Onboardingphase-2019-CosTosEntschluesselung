package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.Random;

public class DecryptionHelper {

    public String keysetString = UserInterface.keysetString;

    /**

     */
    StringArray decrypt(StringArray encryptedContent, int key1) {
        StringArray decryptedContent = new StringArray();
        String[] completeArray = encryptedContent.getArray();
        for (int i = 0; i < completeArray.length; i++) {
            if (completeArray[i] != null) {
                String decryptedMessage = decrypt(completeArray[i], key1);
                decryptedContent.add(decryptedMessage);
            }
        }
        return decryptedContent;
    }

    StringArray decrypt(StringArray encryptedContent, int key1, int seed) {
        randomGenerator(seed);
        StringArray decryptedContent = new StringArray();
        String[] completeArray = encryptedContent.getArray();
        for (int i = 0; i < completeArray.length; i++) {
            if (completeArray[i] != null) {
                String decryptedMessage = decrypt(completeArray[i], key1, seed);
                decryptedContent.add(decryptedMessage);
            }
        }
        return decryptedContent;
    }

    /**
     *
     */
    public String decrypt(String encryptedLine, int key) {

        int keysetLength = keysetString.length();
        int lineLength = encryptedLine.length();
        int charLocationInKeyset;
        int lookAtKeysetPos;
        char decryptedChar;
        String decryptedLine = "";

        for (int i = 0; i < lineLength; i++) {
            char currentChar = encryptedLine.charAt(i);
            charLocationInKeyset = keysetString.indexOf(currentChar);
            lookAtKeysetPos = charLocationInKeyset - key;

            if (lookAtKeysetPos < 0) {
                lookAtKeysetPos = lookAtKeysetPos + keysetLength;
            }
            decryptedChar = keysetString.charAt(lookAtKeysetPos);
            decryptedLine = decryptedLine + decryptedChar;
        }
        return decryptedLine;
    }

    /**

     */
    public String decrypt(String encryptedLine, int key1, int seed) {

        int keysetLength = keysetString.length();
        int lineLength = encryptedLine.length();
        int charLocationInKeyset;
        int lookAtKeysetPos;
        char decryptedChar;
        String decryptedLine = "";

        for (int i = 0; i < lineLength; i++) {
            char currentChar = encryptedLine.charAt(i);
            charLocationInKeyset = keysetString.indexOf(currentChar);
            lookAtKeysetPos = charLocationInKeyset - key1;

            if (lookAtKeysetPos < 0) {
                lookAtKeysetPos = lookAtKeysetPos + keysetLength;
            }
            decryptedChar = keysetString.charAt(lookAtKeysetPos);
            decryptedLine = decryptedLine + decryptedChar;
        }
        return decryptedLine;
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