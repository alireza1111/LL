/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json2mysql;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 * @author root
 */
public class util {
    public void stream2File(String stream) 
  throws IOException {
    //String value = "Hello";
    FileOutputStream fos = new FileOutputStream("src/resources/file.json");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/resources/file.json"))){
            bw.write(stream);
        }
            //outStream.writeUTF(stream);
        
    }
}
