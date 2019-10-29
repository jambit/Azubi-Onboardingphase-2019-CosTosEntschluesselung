package com.jambit;

import java.util.Scanner;

public class UserInterface {
    int key;
    int seed = 0;

    void enterKey() {

        System.out.println("enter the decryption key:");
        Scanner scanner = new Scanner(System.in);
        String keyString = scanner.next();
        String[] keyAndSeed = keyString.split(":");

        if (keyAndSeed.length == 2) {
            key = Integer.parseInt(keyAndSeed[1]);
            seed = Integer.parseInt(keyAndSeed[0]);
        } else {
            key = Integer.parseInt(keyAndSeed[0]);
        }
    }
}
