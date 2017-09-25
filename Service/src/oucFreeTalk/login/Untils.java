package oucFreeTalk.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			System.out.println("����ʧ��");
		}finally {
			System.out.println("���ӳɹ�");
			
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
	
	public String resultSetToJson(ResultSet rs) throws SQLException,JSONException  
	{  
	   // json����  
	   JSONArray array = new JSONArray();  
	    
	   // ��ȡ����  
	   ResultSetMetaData metaData = rs.getMetaData();  
	   int columnCount = metaData.getColumnCount();  
	    
	   // ����ResultSet�е�ÿ������  
	    while (rs.next()) {  
	        JSONObject jsonObj = new JSONObject();  
	         
	        // ����ÿһ��  
	        for (int i = 1; i <= columnCount; i++) {  
	            String columnName =metaData.getColumnLabel(i);  
	            String value = rs.getString(columnName);  
	            jsonObj.put(columnName, value);  
	        }   
	        array.put(jsonObj);   
	    }  
	    
	   return array.toString();  
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