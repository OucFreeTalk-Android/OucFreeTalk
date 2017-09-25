package oucFreeTalk.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import oucFreeTalk.login.Untils;

public class GetUser extends HttpServlet{
	ResultSet rs;
	String returnJSon;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");  
		BufferedReader reader = req.getReader();
		String json= reader.readLine();
		JSONObject jsonObject = new JSONObject(json);
		int id = jsonObject.getInt("id");
		String selectSQL = "select * from students where id = " + id; 
		Untils untils = new Untils();
		try {
			rs = untils.select(selectSQL);
			returnJSon = untils.resultSetToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setCharacterEncoding("UTF-8");	
		PrintWriter out = resp.getWriter();
		JSONObject outjson = new JSONObject(returnJSon);
		out.println(outjson);
		untils.close();
		out.flush();
		out.close();
		
	}
}
