package Until;

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
	public void update(String sql) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(sql);
	}
	public void delete(String sql) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(sql);
	}
	
	
}
