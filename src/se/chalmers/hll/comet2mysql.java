/**
 * This script is created to import Comet sensors measuring infomation from Comet server to Mysql database table "sensors.comet"
 */
package se.chalmers.hll;

/**
 *
 * @author Alireza Davoudian
 * @date 04/12/2017 17:50 PM
 * @updated 20/10/2017 16:20
 * @updated 09/11/2017 18:40
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

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;

public class comet2mysql {

    public static void main(String[] args) throws IOException {

        ArrayList<String> ip = new ArrayList<String>();
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
        
//        try {
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//            Class.forName("com.mysql.jdbc.Driver");
//            Properties config = new Properties();
//            InputStream input = null;
//            input = new FileInputStream("/root/NetBeansProjects/Comet2mysql/src/resources/config.properties");
//            config.load(input);
//            String url = config.getProperty("url");
//            String username = config.getProperty("username");
//            String password = config.getProperty("password");
//            Connection con = DriverManager.getConnection(url, username, password);
//            Statement st = con.createStatement();
//            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//
//            File folder = new File("/home/omnisense/");
//            File[] listOfFiles = folder.listFiles();
//            if (listOfFiles != null) {
//                for (File child : listOfFiles) {
//                    if (child.isFile() && child.getName().endsWith(".xml")) {
//                        Document doc = docBuilder.parse(new File(child.getPath()));
//                        doc.getDocumentElement().normalize();
//                        System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
//                        NodeList listOfSensors = doc.getElementsByTagName("ut_sensor_reading");
//                        for (int s = 0; s < listOfSensors.getLength(); s++) {
//                            Node firstSensorNode = listOfSensors.item(s);
//                            if (firstSensorNode.getNodeType() == Node.ELEMENT_NODE) {
//                                Element firstElement = (Element) firstSensorNode;
//                                String sensor_id = firstElement.getAttribute("sensor_id");
//                                if (sensor_id.equals("1D300008")
//                                        || sensor_id.equals("1D30000D")
//                                        || sensor_id.equals("1D300072")
//                                        || sensor_id.equals("1D3000B9")
//                                        || sensor_id.equals("1D300111")
//                                        || sensor_id.equals("1D3001AC")
//                                        || sensor_id.equals("1D30021A")
//                                        || sensor_id.equals("1D30028D")
//                                        || sensor_id.equals("1D3002A7")
//                                        || sensor_id.equals("1D300372")
//                                        || sensor_id.equals("1D30038D")
//                                        || sensor_id.equals("1D3003A3")
//                                        || sensor_id.equals("1D3003CF")) {
//                                    String time = firstElement.getAttribute("read_date");
//                                    time = time.substring(0,time.length()-5);//time.replace(".000Z", "");
//                                    //System.out.println(time);
//                                    time = time.replace("T", " ");
//                                    String temperature = firstElement.getAttribute("temperature");
//                                    String humidity = firstElement.getAttribute("humidity");
//                                    String wood_pct = firstElement.getAttribute("wood_pct");
//                                    String gpp = firstElement.getAttribute("gpp");
//                                    String dew_point = firstElement.getAttribute("dew_point");
//                                    String sts_msg = firstElement.getAttribute("sts_msg");
//                                    String time_added_to_mysql = LocalDateTime.now().toString();
//                                    time_added_to_mysql= time_added_to_mysql.replace(".000Z", "");
//                                    time_added_to_mysql = time_added_to_mysql.replace("T", " ");
//                                    int i = st.executeUpdate("insert into omnisense(sensor_id,time,temperature,humidity,wood_pct,gpp,dew_point,msg,time_added_to_mysql) values('" + sensor_id
//                                            + "','" + time + "','" + temperature + "','" + humidity + "','" + wood_pct + "','" + gpp + "','" + dew_point + "','" + sts_msg + "','" + time_added_to_mysql + "')");
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            System.out.println("Data is successfully inserted!");
//        } catch (IOException | ClassNotFoundException | SQLException | ParserConfigurationException | SAXException err) {
//            System.out.println(" " + err.getMessage());
//        }
    }
//    private ArrayList ipList() {
//        ArrayList<String> ip= new ArrayList<String>();
//        try (BufferedReader br = new BufferedReader(new FileReader("/root/NetBeansProjects/Comet2mysql/src/resources/iplist"))){
//            String sCurrentLine;
//            while ((sCurrentLine= br.readLine())!=null){
//                ip.add(sCurrentLine);
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(comet2mysql.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(comet2mysql.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return ip;
//    }

//    public void catchXML(){
    // Using Java IO
    public static void saveXML(String fileName, String fileUrl) throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream("/usr/local/bin/comet/temp/"+fileName);

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
}
