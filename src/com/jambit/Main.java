package com.jambit;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        DecryptionKey enterTheKey = new DecryptionKey();
//        enterTheKey.enterDecryptionKey();

        ReadTheEncryptedFile inputFile = new ReadTheEncryptedFile();
        System.out.println(inputFile.readThisLine(3));
    }
}
