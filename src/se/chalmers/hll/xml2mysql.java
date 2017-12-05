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

    public void xml2mysql() {
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

            Date date = new Date();
            String timestamp = dateFormat.format(date).toString();
            File folder = new File("/usr/local/bin/comet/temp");
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File child : listOfFiles) {
                    if (child.isFile() && child.getName().endsWith(".xml")) {
                        Document doc = docBuilder.parse(new File(child.getPath()));
                        doc.getDocumentElement().normalize();
                        System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
                        NodeList xmlroot = doc.getElementsByTagName("root");

                        for (int s = 0; s < xmlroot.getLength(); s++) {
                            Node firstNode = xmlroot.item(s);
                            if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element firstElement = (Element) firstNode;
                                String error = firstElement.getElementsByTagName("err").toString();
                                String sn = firstElement.getElementsByTagName("devsn").toString();
                                NodeList ch1 = firstElement.getElementsByTagName("ch1");
                                for (int i = 0; i < ch1.getLength(); i++) {
                                    Node firstCh1Node = ch1.item(i);
                                    if (firstCh1Node.getNodeType() == Node.ELEMENT_NODE) {
                                        Element firstCh1Element = (Element) firstCh1Node;
                                        String channel_name = firstCh1Element.getElementsByTagName("name").toString();
                                        String value = firstCh1Element.getElementsByTagName("aval").toString();
                                        String unit = firstCh1Element.getElementsByTagName("\ufffdC").toString();
                                        String alarm = firstCh1Element.getElementsByTagName("alarm").toString();
                                        String channel_number = "1";
                                        int j = st.executeUpdate("insert into comet(timestamp,serial_number,error,"
                                                + " channel_number, channel_name, value, unit, alarm) "
                                                + "values('" + timestamp
                                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                                + "','" + unit + "','" + alarm + "')");
                                    }
                                }
                                NodeList ch2 = firstElement.getElementsByTagName("ch2");
                                for (int i = 0; i < ch2.getLength(); i++) {
                                    Node firstCh2Node = ch2.item(i);
                                    if (firstCh2Node.getNodeType() == Node.ELEMENT_NODE) {
                                        Element firstCh2Element = (Element) firstCh2Node;
                                        String channel_name = firstCh2Element.getElementsByTagName("name").toString();
                                        String value = firstCh2Element.getElementsByTagName("aval").toString();
                                        String unit = firstCh2Element.getElementsByTagName("%RH").toString();
                                        String alarm = firstCh2Element.getElementsByTagName("alarm").toString();
                                        String channel_number = "2";
                                        int j = st.executeUpdate("insert into comet(timestamp,serial_number,error,"
                                                + " channel_number, channel_name, value, unit, alarm) "
                                                + "values('" + timestamp
                                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                                + "','" + unit + "','" + alarm + "')");
                                    }
                                }
                                NodeList ch3 = firstElement.getElementsByTagName("ch3");
                                for (int i = 0; i < ch3.getLength(); i++) {
                                    Node firstCh3Node = ch3.item(i);
                                    if (firstCh3Node.getNodeType() == Node.ELEMENT_NODE) {
                                        Element firstCh3Element = (Element) firstCh3Node;
                                        String channel_name = firstCh3Element.getElementsByTagName("name").toString();
                                        String value = firstCh3Element.getElementsByTagName("aval").toString();
                                        String unit = firstCh3Element.getElementsByTagName("\ufffdC").toString();
                                        String alarm = firstCh3Element.getElementsByTagName("alarm").toString();
                                        String channel_number = "3";
                                        int j = st.executeUpdate("insert into comet(timestamp,serial_number,error,"
                                                + " channel_number, channel_name, value, unit, alarm) "
                                                + "values('" + timestamp
                                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                                + "','" + unit + "','" + alarm + "')");
                                    }
                                }
                                NodeList ch4 = firstElement.getElementsByTagName("ch2");
                                for (int i = 0; i < ch4.getLength(); i++) {
                                    Node firstCh4Node = ch4.item(i);
                                    if (firstCh4Node.getNodeType() == Node.ELEMENT_NODE) {
                                        Element firstCh4Element = (Element) firstCh4Node;
                                        String channel_name = firstCh4Element.getElementsByTagName("name").toString();
                                        String value = firstCh4Element.getElementsByTagName("aval").toString();
                                        String unit = firstCh4Element.getElementsByTagName("ppm").toString();
                                        String alarm = firstCh4Element.getElementsByTagName("alarm").toString();
                                        String channel_number = "4";
                                        int j = st.executeUpdate("insert into comet(timestamp,serial_number,error,"
                                                + " channel_number, channel_name, value, unit, alarm) "
                                                + "values('" + timestamp
                                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                                + "','" + unit + "','" + alarm + "')");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException | ParserConfigurationException | SAXException err) {
            System.out.println(" " + err.getMessage());

        }

        /*  
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
        } */
    }

}
