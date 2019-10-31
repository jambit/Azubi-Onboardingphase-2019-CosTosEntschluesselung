package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.Random;

public class EncryptionHelper {

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
                String encryptedMessage = encrypt(completeArray[i], key);
                encryptedContent.add(String.valueOf(encryptedMessage));
            }
        }
        return encryptedContent;
    }


    String encrypt(String decryptedLine, int key) {
        String keyset = Constants.KEYSETSTRING;
        int keysetLength = Constants.KEYSETSTRING.length();
        int lineLength = decryptedLine.length();
        int charLocationInKeyset;
        int lookAtKeysetPos;
        char encryptedChar;
        String encryptedLine = "";

        for (int i = 0; i < lineLength; i++) {
            char currentChar = decryptedLine.charAt(i);
            charLocationInKeyset = keyset.indexOf(currentChar);
            lookAtKeysetPos = charLocationInKeyset + key;

            if (lookAtKeysetPos + 1 > keysetLength) {
                lookAtKeysetPos = lookAtKeysetPos - keysetLength;
            }
            encryptedChar = keyset.charAt(lookAtKeysetPos);
            encryptedLine = encryptedLine + encryptedChar;
        }
        return encryptedLine;
    }


    /**
     * Swap 2 indexes in a string
     * @param str string to swap string
     * @param i1  index1
     * @param i2  index2
     * @return string after swap
     */
    private String swapIndex(String str, int i1, int i2) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i1, str.charAt(i2));
        sb.setCharAt(i2, str.charAt(i1));
        return sb.toString();
    }

    /**
     * Randomizes the character set
     * @param seed seed to use for the randomization
     */
    public String randomGenerator(int seed) {
        String keyset = "";
        Random generator = new Random(seed);
        for (int i = 1; i < generator.nextInt(1000); i++) {
            for (int j = 0; j < keyset.length(); j++) {
                generator = new Random(seed + (i * 3 * (j + 1)));
                int randNum = generator.nextInt(keyset.length() - 1);
                keyset = swapIndex(keyset, j, randNum);
            }
        }
        return keyset;
    }
}

