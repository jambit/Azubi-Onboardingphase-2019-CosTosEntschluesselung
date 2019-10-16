package com.jambit;

import java.util.Scanner;

public class DecryptionKey {
    public int decryptionKey;


    /**
     *
     */

    public void enterDecryptionKey(){
        System.out.println("Please enter the decryption key:");
        Scanner keySkanner = new Scanner(System.in);
        decryptionKey = keySkanner.nextInt();
    }

}
