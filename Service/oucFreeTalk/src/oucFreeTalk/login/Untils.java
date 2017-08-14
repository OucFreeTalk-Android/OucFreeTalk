package oucFreeTalk.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Untils {
	Connection connection;
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/oucFreeTalk";
	String user = "root";
	String passwd = "zjkzjk1996";
	ResultSet rs;
	Statement stmt;
	public Untils () {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, passwd);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("连接失败");
		}finally {
			System.out.println("连接成功");
			
		}
		
	}
	public ResultSet select(String sql) throws SQLException {
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
        return rs;
	}
	public void insert(String sql) throws SQLException {
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
	}
	public Connection getConnection() {
		return connection;
	}
	
	public void close() {  
        try {  
            connection.close();  
            rs.close();
            stmt.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
	
}
