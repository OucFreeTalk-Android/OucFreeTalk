package oucFreeTalk.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	String url = "jdbc:mysql://localhost:3306/oucfreetalk";
	String user = "root";
	String passwd = "zjkzjk1996";
	public Untils() {
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
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(sql);
        return rs;
	}
	public void insert(String sql) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(sql);
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
        	ResultSet rs = null;
        	Statement st = null;
            connection.close();  
            rs.close();
            st.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
	
}
