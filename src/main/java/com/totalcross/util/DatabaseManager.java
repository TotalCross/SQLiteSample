package com.totalcross.util;

import totalcross.db.sqlite.SQLiteUtil;
import java.sql.SQLException;
import totalcross.sql.Statement;
import totalcross.sql.Connection;

import totalcross.sys.Settings;

public class DatabaseManager {
    public static SQLiteUtil sqliteUtil;
    static {
        try {
            sqliteUtil = new SQLiteUtil(Settings.appPath, "test.db");
            Statement st = sqliteUtil.con().createStatement();
            st.execute("create table if not exists person (cpf varchar)");
            st.close();
 
        } catch ( java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
		return sqliteUtil.con();
	}
    
}
