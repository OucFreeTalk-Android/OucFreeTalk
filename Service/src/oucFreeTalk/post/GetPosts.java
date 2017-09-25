package oucFreeTalk.post;

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

import net.sf.json.JSONArray;
import oucFreeTalk.login.Untils;

public class GetPosts extends HttpServlet {
	ResultSet rs;
	JSONArray returnJSon = new JSONArray();
	String getPost;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");  
		BufferedReader reader = req.getReader();
		String json= reader.readLine();
		JSONObject jsonObject = new JSONObject(json);
		int perpage = jsonObject.getInt("perpage");
		int pclass = jsonObject.getInt("pclass");
		String selectSQL = "Select * from posts order by updatetime desc";
		Untils untils = new Untils();
		for(int i=0;i<perpage;i++)
		{
			try {
				rs = untils.select(selectSQL);
				getPost = untils.resultSetToJson(rs); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnJSon.add((new JSONObject(getPost)));
		}
		resp.setCharacterEncoding("UTF-8");	
		PrintWriter out = resp.getWriter();
		out.println(returnJSon);
		untils.close();
		out.flush();
		out.close();
	}
}
