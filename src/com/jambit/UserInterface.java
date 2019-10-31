package com.jambit;

import com.sun.tools.internal.jxc.ap.Const;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class UserInterface {

   int key;
   int seed = 0;
   StringArray decryptedContent;
   StringArray encryptedContent;
   String pathname;
   public static String keysetString =
       "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÜÖabcdefghijklmnopqrstuvwxyzäüöß0123456789,.!?\"§$%&/"
           +
           "()" +
           "=+-*\\_#~<>| ";


   void startUi() throws IOException {
      Scanner sc = new Scanner(System.in);
      EncryptionHelper encryptionHelper = new EncryptionHelper();
      while (true) {
         System.out.println("do you want to encrypt[e] or decrypt[d] the file");
         String input = sc.next();

         //TODO: Handle user input for file path
         final StringArray decryptedFileContent = readFile(Constants.DEFAULT_DECRYPTED_FILE_PATH);
         final StringArray encryptedFileContent = readFile(Constants.DEFAULT_ENCRYPTED_FILE_PATH);
         switch (input) {
            case "e":
               System.out
                   .println("do you want to generate a key and seed? [y] [n]");
               input = sc.next();

               switch (input) {
                  case "y":
                     encryptWithRandoms(input);
                     break;

                  case "n":
                     System.out.println("please enter your [seed]:[key]");

                     input = sc.next();
                     CodecUtility seedAndKey = splitSeedAndKey(input);

                     if (seedAndKey.getSeed() != 0) { //if seed is supplied
                        encryptedContent = encryptionHelper
                            .encrypt(decryptedFileContent, seedAndKey.getKey(), seedAndKey.getSeed());

                     } else {
                        encryptedContent = encryptionHelper //if seed is not supplied
                            .encrypt(decryptedFileContent, seedAndKey.getKey());
                     }
                     break;

                  default:
                     break;
               }

               writeEncryptedContentToFile(encryptedContent);
               break;

            case "d":
               DecryptionHelper decryptionHelper = new DecryptionHelper();
               System.out.println("please enter your [seed]:[key]");

               input = sc.next();
               CodecUtility seedAndKey = splitSeedAndKey(input);

               if (seedAndKey.getSeed() != 0) { //if seed is supplied
                  //TODO: decryptedContent not global!
                  decryptedContent = decryptionHelper.decrypt(encryptedFileContent, seedAndKey.getKey(), seedAndKey.getSeed());

               } else {
                  decryptedContent = decryptionHelper //if seed is not supplied
                      .decrypt(decryptedFileContent, seedAndKey.getKey());
               }

               break;

            default:
               break;
         }
      }
   }

   private StringArray encryptWithRandoms(String input) {
      EncryptionHelper encryptionHelper = new EncryptionHelper();
      CodecUtility randomKeyAndSeed = splitSeedAndKey(input);
      randomKeyAndSeed.setKey(generateRandomKey());
      randomKeyAndSeed.setSeed(generateRandomSeed());
      System.out.println("your seed and key are: " + randomKeyAndSeed.getSeed() + ":" + randomKeyAndSeed.getKey());
      return encryptionHelper.encrypt(decryptedContent, randomKeyAndSeed.getSeed(), randomKeyAndSeed.getKey());

   }

   private StringArray readFile(String s) throws IOException {
      InputReader inputReader = new InputReader();
      return inputReader.readFile(s);
   }

   private StringArray printSeedMenue() throws IOException {
      Scanner sc = new Scanner(System.in);
      InputReader inputReader = new InputReader();

      System.out.println("please enter your [seed]:[key]");

      //Todo: Extract to METHOD
      final StringArray decryptedFileContent = inputReader
          .readFile("DecryptedText.txt");
      return decryptedFileContent;
   }

   private CodecUtility splitSeedAndKey(String keyString) {
      String[] keyAndSeed = keyString.split(":");
      CodecUtility codecUtility = new CodecUtility();

      try {
         if (keyAndSeed.length == 2) {
            codecUtility.setKey(Integer.parseInt(keyAndSeed[1]));
            codecUtility.setSeed(Integer.parseInt(keyAndSeed[0]));
         }
      } catch (NumberFormatException e) {
         System.out.println("Invalid input. Try again");
      }

      return codecUtility;
   }

   boolean checkFileIfExists(String pathname) {
      boolean fileExists;
      File file = new File(pathname);
      fileExists = file.exists();
      return fileExists;
   }

   void enterKeyAndEncrypt() throws IOException {
      final StringArray decryptedFileContent = readFile("DecryptedText.txt");
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

      EncryptionHelper encryptionHelper = new EncryptionHelper();
      if (seed != 0) {
         encryptedContent = encryptionHelper
             .encrypt(decryptedFileContent, key, seed);

      } else {
         encryptedContent = encryptionHelper.encrypt(decryptedFileContent, key);
      }
   }

   void enterKeyAndDecrypt() throws IOException {

      final StringArray encryptedFileContent = readFile("EncryptedText.txt");

      System.out.println("enter a key (format: 12345:67 or just 67):");
      Scanner scanner = new Scanner(System.in);
      String keyString = scanner.next();
      String[] keyAndSeed = keyString.split(":");

      if (keyAndSeed.length == 2) {
         key = Integer.parseInt(keyAndSeed[1]);
         seed = Integer.parseInt(keyAndSeed[0]);
      } else {
         key = Integer.parseInt(keyAndSeed[0]);
      }

      DecryptionHelper decryptionHelper = new DecryptionHelper();

      if (seed != 0) {
         decryptedContent = decryptionHelper.decrypt(encryptedFileContent, key,
             seed);
      } else {
         decryptedContent = decryptionHelper.decrypt(encryptedFileContent, key);
      }
   }

   void printContent(StringArray content) {
      // prints out the decrypted text
      for (int i = 0; i < content.getSize(); i++) {
         System.out.println(content.get(i));
      }
   }

   void writeDecryptedContentInFile(StringArray decryptedContent)
       throws IOException {
      CustomFileWriter customFileWriter = new CustomFileWriter();
      customFileWriter.write(decryptedContent, Constants.DEFAULT_DECRYPTED_FILE_PATH);
   }

   void writeEncryptedContentToFile(StringArray encryptedContent)
       throws IOException {
      CustomFileWriter customFileWriter = new CustomFileWriter();
      customFileWriter.write(encryptedContent, Constants.DEFAULT_ENCRYPTED_FILE_PATH);
   }

   private int generateRandomKey() {
      int min = 1;
      int max = keysetString.length() - 1;
      Random r = new Random();
      return r.nextInt((max - min) + 1) + min;
   }

   private int generateRandomSeed() {
      int min = -2147483648;
      int max = 2147483647;
      Random r = new Random();
      return r.nextInt((max - min) + 1) + min;
   }

   private boolean checkSeedKeyFormat(String seedKey) {

      String[] keyAndSeed = seedKey.split(":");

      if (keyAndSeed.length == 2) {
         key = Integer.parseInt(keyAndSeed[1]);
         seed = Integer.parseInt(keyAndSeed[0]);
      } else {
         key = Integer.parseInt(keyAndSeed[0]);
      }
      return true;

   }
}
