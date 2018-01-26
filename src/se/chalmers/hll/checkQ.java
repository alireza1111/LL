/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.hll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class checkQ {

    public boolean checkQ() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {

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
        ResultSet rs;
        Statement stmt = con.createStatement();
        String q = "select id from cometTemp order by id desc limit 1";
        rs = stmt.executeQuery(q);
        rs.next();
        int ii = rs.getInt("id");
        System.out.println("number of recs: " + ii);
        stmt.close();
        if (ii >= 1600) {
            return true;
        } else {
            return false;
        }
    }

}
