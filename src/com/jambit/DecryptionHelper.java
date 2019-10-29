package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class DecryptionHelper {

    StringArray decrypt(StringArray encryptedContent) {
        StringArray decryptedContent = new StringArray();
        String[] completeArray = encryptedContent.getArray();
        for (int i = 0; i < completeArray.length; i++) {
            if (completeArray[i] != null) {
                String decryptedMessage = decrypt(completeArray[i]);
                decryptedContent.add(decryptedMessage);
            }
        }

        return decryptedContent;
    }

    public String decrypt(String s) {

        String keysetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÜÖabcdefghijklmnopqrstuvwxyzäüöß0123456789,.!?\"§$%&/()" +
                "=+-*\\_#~<>| ";
        char[] keyset = keysetString.toCharArray();
        char[] chararray = s.toCharArray();


        return "ENCRYPTED";

    }
}
