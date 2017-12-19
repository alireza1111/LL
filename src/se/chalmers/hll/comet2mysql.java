/**
 * This script is created to import Comet sensors measuring infomation from Comet server to Mysql database table "sensors.comet"
 */
package se.chalmers.hll;

/**
 *
 * @author Alireza Davoudian
 * @date 06/12/2017 16:30 PM
 * @updted 18/12/2017
 */
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.*;
import org.xml.sax.SAXException;

public class comet2mysql {

    public static void main(String[] args) throws IOException, SQLException, ParserConfigurationException, SAXException, InterruptedException {
        cleanFolder();

        ArrayList<String> ip = new ArrayList<String>();
        xml2mysql x2s = new xml2mysql();
        try (BufferedReader br = new BufferedReader(new FileReader("/root/NetBeansProjects/Comet2mysql/src/resources/iplist"))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                ip.add(sCurrentLine);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(comet2mysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(comet2mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 0;
        String fileName, fileURL;
        while (i < ip.size()) {
            fileName = "value" + i + ".xml";
            fileURL = "http://" + ip.get(i) + "/values.xml";
            saveXML(fileName, fileURL);
            System.out.println(fileURL);
            i++;
        }

        x2s.xml2mysql();
    }

    public static void saveXML(String fileName, String fileUrl) throws MalformedURLException, IOException {

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream("/usr/local/bin/comet/temp/" + fileName);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }

    public static void cleanFolder() {
        File folder = new File("/usr/local/bin/comet/temp");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            file.delete();
        }
    }
}
