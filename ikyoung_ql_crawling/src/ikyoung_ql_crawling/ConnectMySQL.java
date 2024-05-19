package ikyoung_ql_crawling;

import java.sql.Connection;

import java.sql.*;

//DB와 table은 application running 이전에 미리 만들어져 있다고 가정. 

public class ConnectMySQL {
    private static Connection conn;

    public static void mySQLConnect(String dbid, String userid, String passwd) {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbid + "?useUnicode=true&useSSL=false&serverTimezone=UTC", userid, passwd);
            System.out.println("++++++Test: MySQL connected without an error.++++++");
        } catch (SQLException sqle) {
            System.out.println("SQLException:" + sqle);
        }
    }

 

    public static void insertValuesUser(String handle, String userlink, int solvednum, int tier, int rank_ingroup) {
        String query = "INSERT INTO User (handle, userlink, solvednum, tier, rank_ingroup) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, handle);
            pstmt.setString(2, userlink);
            pstmt.setInt(3, solvednum);
            pstmt.setString(4, String.valueOf(tier)); // ENUM: String element라고 가정 
            pstmt.setInt(5, rank_ingroup);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException during insert: " + e);
        }
    }
}