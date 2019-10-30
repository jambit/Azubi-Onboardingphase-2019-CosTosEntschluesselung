package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        InputReader inputReader = new InputReader();
        int key1=45;
        int seed=23;
        StringArray sa = inputReader.readFile("DecryptedText.txt");


        EncryptionHelper encryptionHelper = new EncryptionHelper();
        encryptionHelper.encrypt(sa,key1);


        UserInterface userInterface = new UserInterface();
        userInterface.enterKey();
    }
}
