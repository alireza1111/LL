/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json2mysql;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Json2posgresBat {

    static String filepath = "/tmp/quuppaBat/qbattery.log";
    static GsonBuilder gsonbuilder = new GsonBuilder();

    static PreparedStatement preparedStatement = null;
    static int status = 0;

    private static Connection connect = null;

    public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException, SQLException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        String watchedFile = "/tmp/quuppaBat/";
        Path f = Paths.get(watchedFile);
        WatchKey watchKey = f.register(watchService, ENTRY_MODIFY);
        watchKey = watchService.take();
        Gson gson = gsonbuilder.serializeNulls().create();
        int port = args.length == 0 ? 33334 : Integer.parseInt(args[0]);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date timestamp = new Date();
        String timestamp2 = dateFormat.format(timestamp);

        BufferedReader str = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filepath),
                        "UTF-8"));
        // System.out.println(str);

        JSONParser parser = new JSONParser();
        Object object = parser.parse(str);

        Properties props = new Properties();
        props.setProperty("user", "livinglab");
        props.setProperty("password", "mCEqz2sG");
        connect = DriverManager.getConnection("jdbc:postgresql://botanix.livinglab.chalmers.se:5432/livinglab", props);
        // Prepared statements
        String sql = "insert into quuppa_health (tag_id, tag_name, tag_group, voltage, alarm) values (?,?,?,?,?);";
        preparedStatement = connect.prepareStatement(sql);
        JSONObject jsonObject = (JSONObject) object;

        String alarm = null;
        if (!jsonObject.get("batteryAlarm").equals("null")) {
            alarm = (String) jsonObject.get("batteryAlarm");
        }

        String tagName = null;
        if (jsonObject.get("name") != "null") {
            tagName = (String) jsonObject.get("name");
        } else {
            tagName = null;
        }

        // Set alarm to file
        Logger logger = Logger.getLogger("Json2posgresBat.class");
        logger.setLevel(Level.SEVERE);
        if (alarm.equals("ok")) {
            logger.log(Level.INFO, "Battery is ok for tag " + tagName);
        } else {
            logger.log(Level.SEVERE, "Battery problem with tag " + tagName);
        }
    }

    private static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
}
