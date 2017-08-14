package oucFreeTalk.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.*;  
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginAction extends HttpServlet {
	String truePassword;
	ResultSet rs;
	String returnJSon;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");  
		BufferedReader reader = req.getReader();
		String json= reader.readLine();
		JSONObject jsonObject = new JSONObject(json);
		int user = jsonObject.getInt("username");
		String pass = jsonObject.getString("password");
		Untils untils = new Untils();
		String sql = "select PASSWORD FROM students WHERE id = " + user ;
		try {
			rs = untils.select(sql);
			truePassword = rs.getString("password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (truePassword == null) {
			returnJSon = "{'result':" + 0 + "}";
		}else if (truePassword == pass) {
			returnJSon = "{'result':" + 1 + "}";
		}else {
			returnJSon = "{'result':" + 2 + "}";
		}
		resp.setCharacterEncoding("UTF-8");	
		PrintWriter out = resp.getWriter();
		JSONObject outjson = new JSONObject(returnJSon);
		out.println(outjson);
		
		out.flush();
		out.close();
	}
	
}
