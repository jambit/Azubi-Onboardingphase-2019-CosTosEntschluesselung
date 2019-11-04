package com.jambit;

public class Constants {

    /**
     * to change the path of the input and output files just copy the path of the new file to the String
     */
    public final static String DEFAULT_DECRYPTED_FILE_PATH = "DecryptedText.txt";
    public final static String DEFAULT_ENCRYPTED_FILE_PATH = "EncryptedText.txt";

    /**
     * to change the keyset which is consists of all usable chars in the textfile
     */
    public static String KEYSETSTRING =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÜÖabcdefghijklmnopqrstuvwxyzäüöß0123456789,.!?\"§$%&/"
                    + "()=+-*\\_#~<>| ";
}
