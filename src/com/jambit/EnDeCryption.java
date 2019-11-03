package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import java.util.Random;

public class EnDeCryption {


    StringArray decrypt(StringArray encryptedContent, int key, int seed) {
        String keyset = Constants.KEYSETSTRING;
        if(seed != 0){
            keyset = randomGenerator(seed);
        }
        StringArray decryptedContent = new StringArray();
        String[] completeArray = encryptedContent.getArray();
        for (int i = 0; i < completeArray.length; i++) {
            if (completeArray[i] != null) {
                String decryptedMessage = decrypt(completeArray[i], key, keyset);
                decryptedContent.add(decryptedMessage);
            }
        }
        return decryptedContent;
    }

    public String decrypt(String encryptedLine, int key ,String keyset) {
        int charLocationInKeyset;
        int lookAtKeysetPos;
        char decryptedChar;
        String decryptedLine = "";

        for (int i = 0; i < encryptedLine.length(); i++) {
            char currentChar = encryptedLine.charAt(i);
            charLocationInKeyset = keyset.indexOf(currentChar);
            lookAtKeysetPos = charLocationInKeyset - key;

            if (lookAtKeysetPos < 0) {
                lookAtKeysetPos = lookAtKeysetPos + keyset.length();
            }
            decryptedChar = keyset.charAt(lookAtKeysetPos);
            decryptedLine = decryptedLine + decryptedChar;
        }
        return decryptedLine;
    }


    StringArray encrypt(StringArray decryptedContent, int key, int seed) {
        String keyset = Constants.KEYSETSTRING;
        if(seed != 0){
            keyset = randomGenerator(seed);
        }
        StringArray encryptedContent = new StringArray();
        String[] completeArray = decryptedContent.getArray();
        for (int i = 0; i < completeArray.length; i++) {
            if (completeArray[i] != null) {
                String encryptedMessage = encrypt(completeArray[i], key ,keyset);
                encryptedContent.add(String.valueOf(encryptedMessage));
            }
        }
        return encryptedContent;
    }

    String encrypt(String decryptedLine, int key, String keyset) {
        int charLocationInKeyset;
        int lookAtKeysetPos;
        char encryptedChar;
        String encryptedLine = "";

        for (int i = 0; i < decryptedLine.length(); i++) {
            char currentChar = decryptedLine.charAt(i);
            charLocationInKeyset = keyset.indexOf(currentChar);
            lookAtKeysetPos = charLocationInKeyset + key;

            if (lookAtKeysetPos + 1 > keyset.length()) {
                lookAtKeysetPos = lookAtKeysetPos - keyset.length();
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
        String changedKeyset = "";
        Random generator = new Random(seed);
        for (int i = 1; i < generator.nextInt(1000); i++) {
            for (int j = 0; j < Constants.KEYSETSTRING.length(); j++) {
                generator = new Random(seed + (i * 3 * (j + 1)));
                int randNum = generator.nextInt(Constants.KEYSETSTRING.length() - 1);
                changedKeyset = swapIndex(Constants.KEYSETSTRING, j, randNum);
            }
        }
        return changedKeyset;
    }
}
