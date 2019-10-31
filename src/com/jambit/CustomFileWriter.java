package com.jambit;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CustomFileWriter {


   public void write(StringArray content, String path) throws IOException {
      FileWriter fw = new FileWriter(path, true);
      BufferedWriter bw = new BufferedWriter(fw);

      for (int i = 0; i < content.getArray().length; i++) {

         if (content.getArray()[i] != null) {
            bw.append(content.getArray()[i]);
            bw.newLine();
         } else {
            bw.close();
         }
      }
   }
}
