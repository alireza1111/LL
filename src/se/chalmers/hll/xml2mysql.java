/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.hll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author root
 */
public class xml2mysql {
    public void xml2mysql() throws SQLException{
         try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Class.forName("com.mysql.jdbc.Driver");
            Properties config = new Properties();
            InputStream input = null;
            input = new FileInputStream("/root/NetBeansProjects/Comet2mysql/src/resources/config.properties");
            config.load(input);
            String url = config.getProperty("url");
            String username = config.getProperty("username");
            String password = config.getProperty("password");
            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            
            Date date= new Date();
            String timestamp= dateFormat.format(date);
            File folder = new File("/usr/local/bin/comet/temp");
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File child : listOfFiles) {
                    if (child.isFile() && child.getName().endsWith(".xml")) {
                        Document doc = docBuilder.parse(new File(child.getPath()));
                        doc.getDocumentElement().normalize();
                        System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
                        NodeList errorTag = doc.getElementsByTagName("err");
                        String error=errorTag.item(0).toString();
                        NodeList serialNumber = doc.getElementsByTagName("devsn");
                        String sn= serialNumber.item(0).toString();
                        NodeList synCh= doc.getElementsByTagName("synch");
                        String synch= synCh.item(0).toString();
                        NodeList ch1= doc.getElementsByTagName("ch1");
                        String ch1_name= ch1
                        
                        
                        for (int s = 0; s < listOfSensors.getLength(); s++) {
                            Node firstSensorNode = listOfSensors.item(s);
                            if (firstSensorNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element firstElement = (Element) firstSensorNode;
                                String sensor_id = firstElement.getAttribute("sensor_id");
                                if (sensor_id.equals("1D300008")
                                        || sensor_id.equals("1D30000D")
                                        || sensor_id.equals("1D300072")
                                        || sensor_id.equals("1D3000B9")
                                        || sensor_id.equals("1D300111")
                                        || sensor_id.equals("1D3001AC")
                                        || sensor_id.equals("1D30021A")
                                        || sensor_id.equals("1D30028D")
                                        || sensor_id.equals("1D3002A7")
                                        || sensor_id.equals("1D300372")
                                        || sensor_id.equals("1D30038D")
                                        || sensor_id.equals("1D3003A3")
                                        || sensor_id.equals("1D3003CF")) {
                                    String time = firstElement.getAttribute("read_date");
                                    time = time.substring(0,time.length()-5);//time.replace(".000Z", "");
                                    //System.out.println(time);
                                    time = time.replace("T", " ");
                                    String temperature = firstElement.getAttribute("temperature");
                                    String humidity = firstElement.getAttribute("humidity");
                                    String wood_pct = firstElement.getAttribute("wood_pct");
                                    String gpp = firstElement.getAttribute("gpp");
                                    String dew_point = firstElement.getAttribute("dew_point");
                                    String sts_msg = firstElement.getAttribute("sts_msg");
                                    String time_added_to_mysql = LocalDateTime.now().toString();
                                    time_added_to_mysql= time_added_to_mysql.replace(".000Z", "");
                                    time_added_to_mysql = time_added_to_mysql.replace("T", " ");
                                    int i = st.executeUpdate("insert into omnisense(sensor_id,time,temperature,humidity,wood_pct,gpp,dew_point,msg,time_added_to_mysql) values('" + sensor_id
                                            + "','" + time + "','" + temperature + "','" + humidity + "','" + wood_pct + "','" + gpp + "','" + dew_point + "','" + sts_msg + "','" + time_added_to_mysql + "')");
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Data is successfully inserted!");
        } catch (IOException | ClassNotFoundException | SQLException | ParserConfigurationException | SAXException err) {
            System.out.println(" " + err.getMessage());
        }
    }

        
    }
    
    
    
}
