package oucFreeTalk.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import oucFreeTalk.login.Untils;

public class Editor extends HttpServlet{
	String returnJSon;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");  
		BufferedReader reader = req.getReader();
		String json= reader.readLine();
		JSONObject jsonObject = new JSONObject(json);
		int id = jsonObject.getInt("id");
		String name = jsonObject.getString("name");
		boolean sex = jsonObject.getBoolean("sex");
		String birth = jsonObject.getString("birth");
		String year = jsonObject.getString("year");
		String family = jsonObject.getString("family");
		String pic = jsonObject.getString("pic");
		Untils untils = new Untils();
		String SQL = "update students set name = "+ name +",sex = " + sex + ",birth = " + birth 
				+ ",year = " + year + ",family = "+ family + ",pic = " + pic + " where id = " + id;
		try {
			untils.insert(SQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJSon = "{'result':" + 2 + "}";
		}
		returnJSon = "{'result':" + 1 + "}";
		
		resp.setCharacterEncoding("UTF-8");	
		PrintWriter out = resp.getWriter();
		JSONObject outjson = new JSONObject(returnJSon);
		out.println(outjson);
		untils.close();
		out.flush();
		out.close();
	}
}
