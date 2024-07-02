package com.dodi.saw.laptop;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dodih
 */
public class Koneksi {
     public static Connection Go(){
        Connection k = null;
        try {
            MysqlDataSource mds = new MysqlDataSource();
            mds.setServerName("localhost");
            mds.setDatabaseName("pemkom-saw-laptop"); 
            mds.setUser("root"); 
            mds.setPassword(""); 
            mds.setPortNumber(3306); 
            mds.setServerTimezone("Asia/Jakarta"); 
            k = mds.getConnection();
            return k;
        } catch (Exception e) {
            System.out.println("koneksi DB error : " + e.getMessage());
        }
        return k;
    }
}
