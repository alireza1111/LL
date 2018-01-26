package se.chalmers.hll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
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
 * @author Alireza Davoudian
 * @date 06/12/2017 16:30
 * @date 09/12/2017 17:40
 */
public class xml2mysql {

    public void xml2mysql() throws InterruptedException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            String timestamp = dateFormat.format(date);
            File folder = new File("/usr/local/bin/comet/temp");
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File child : listOfFiles) {
                    if (child.isFile() && child.getName().endsWith(".xml")) {
                        Document doc = docBuilder.parse(new File(child.getPath()));
                        doc.getDocumentElement().normalize();
                        System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
                        NodeList xmlroot = doc.getElementsByTagName("root");
                        NodeList err = doc.getElementsByTagName("err");
                        NodeList serial_number = doc.getElementsByTagName("devsn");
                        NodeList ch1 = doc.getElementsByTagName("ch1");
                        NodeList ch2 = doc.getElementsByTagName("ch2");
                        NodeList ch3 = doc.getElementsByTagName("ch3");
                        NodeList ch4 = doc.getElementsByTagName("ch4");

                        String error = err.item(0).getTextContent();
                        String sn = serial_number.item(0).getTextContent();

                        Node ch1Node = ch1.item(0);
                        Element ch1Element = (Element) ch1Node;
                        String channel_name = ch1Element.getElementsByTagName("name").item(0).getTextContent();
                        String value = ch1Element.getElementsByTagName("aval").item(0).getTextContent();
                        String unit = ch1Element.getElementsByTagName("unit").item(0).getTextContent();
                        String alarm = ch1Element.getElementsByTagName("alarm").item(0).getTextContent();
                        String channel_number = "1";
                        int j = st.executeUpdate("insert into cometTemp(timestamp,serial_number,error,"
                                + " channel_number, channel_name, value, unit, alarm) "
                                + "values('" + timestamp
                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                + "','" + unit + "','" + alarm + "')");

                        Node ch2Node = ch2.item(0);
                        Element ch2Element = (Element) ch2Node;
                        channel_name = ch2Element.getElementsByTagName("name").item(0).getTextContent();
                        value = ch2Element.getElementsByTagName("aval").item(0).getTextContent();
                        unit = ch2Element.getElementsByTagName("unit").item(0).getTextContent();
                        alarm = ch2Element.getElementsByTagName("alarm").item(0).getTextContent();
                        channel_number = "2";
                        j = st.executeUpdate("insert into cometTemp(timestamp,serial_number,error,"
                                + " channel_number, channel_name, value, unit, alarm) "
                                + "values('" + timestamp
                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                + "','" + unit + "','" + alarm + "')");

                        Node ch3Node = ch3.item(0);
                        Element ch3Element = (Element) ch3Node;
                        channel_name = ch3Element.getElementsByTagName("name").item(0).getTextContent();
                        value = ch3Element.getElementsByTagName("aval").item(0).getTextContent();
                        unit = ch3Element.getElementsByTagName("unit").item(0).getTextContent();
                        alarm = ch3Element.getElementsByTagName("alarm").item(0).getTextContent();
                        channel_number = "3";
                        j = st.executeUpdate("insert into cometTemp(timestamp,serial_number,error,"
                                + " channel_number, channel_name, value, unit, alarm) "
                                + "values('" + timestamp
                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                + "','" + unit + "','" + alarm + "')");

                        Node ch4Node = ch4.item(0);
                        Element ch4Element = (Element) ch4Node;
                        channel_name = ch4Element.getElementsByTagName("name").item(0).getTextContent();
                        value = ch4Element.getElementsByTagName("aval").item(0).getTextContent();
                        unit = ch4Element.getElementsByTagName("unit").item(0).getTextContent();
                        alarm = ch4Element.getElementsByTagName("alarm").item(0).getTextContent();
                        channel_number = "4";
                        j = st.executeUpdate("insert into cometTemp(timestamp,serial_number,error,"
                                + " channel_number, channel_name, value, unit, alarm) "
                                + "values('" + timestamp
                                + "','" + sn + "','" + error + "','" + channel_number + "','" + channel_name + "','" + value
                                + "','" + unit + "','" + alarm + "')");
                    }
                }
            }
            TimeUnit.SECONDS.sleep(3);
            checkQ chq = new checkQ();
            if (chq.checkQ()) {
                int jj = st.executeUpdate("set @timestamp:=CURRENT_TIMESTAMP;");
                jj = st.executeUpdate(" insert into Comet select @timestamp,serial_number,error,channel_number,"
                        + " channel_name,channel_description,avg(value), unit, alarm from cometTemp"
                        + " where channel_number in(select channel_number from cometTemp group by channel_number) "
                        + "and serial_number in(select serial_number from cometTemp group by serial_number) "
                        + "and error in (select error from cometTemp group by error) group by serial_number, channel_number, "
                        + "channel_name, channel_description,unit, alarm,error");
                jj = st.executeUpdate("truncate table cometTemp");
            }

        } catch (IOException | ClassNotFoundException | SQLException | ParserConfigurationException | SAXException err) {
            System.out.println(" " + err.getMessage());
        }

    }

}
