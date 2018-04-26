/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json2mysql;

/**
 *
 * @author root
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receiver {

    private DatagramSocket serverSocket;

        public static void main(String[] args) {
        int port = args.length == 0 ? 34335 : Integer.parseInt(args[0]);
        new Receiver().run(port);
    }

    public void run(int port){
      try {
        serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[65536];
        util u= new util();
        System.out.printf("Listening on udp:%s:%d%n",
                InetAddress.getLocalHost().getHostAddress(), port);
        DatagramPacket receivePacket = new DatagramPacket(receiveData,
                           receiveData.length);
       // while(true)
        //{
          //    Thread.sleep(30000);
              serverSocket.receive(receivePacket);
              String sentence = new String( receivePacket.getData(), 0,
                                 receivePacket.getLength() );
              System.out.println(sentence);
              
              // send to file
              sentence.trim();
              u.stream2File(sentence);

              

              // now send acknowledgement packet back to sender
              InetAddress IPAddress = receivePacket.getAddress();
              String sendString = "who?";
              byte[] sendData = sendString.getBytes("UTF-8");
              DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                   IPAddress, receivePacket.getPort());
              serverSocket.send(sendPacket);
        //}
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
finally
      {
          serverSocket.close();
      }
      // should close serverSocket in finally block

    }
}

