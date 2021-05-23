package utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    static String driver;
    static String url;
    static String userName;
    static String userPassword;

    static{
        try{
            InputStream resourceAsStream = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(resourceAsStream);
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            userName = props.getProperty("userName");
            userPassword = props.getProperty("userPassword");
            Class.forName(driver);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, userName, userPassword);
    }
    public void release(Connection conn, Statement statement, ResultSet rs) throws Exception{
        if(rs != null)
            rs.close();
        if(statement != null)
            statement.close();
        if(conn != null)
            conn.close();
    }
}
