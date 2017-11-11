package user;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Until.Untils;

public class GetUser extends HttpServlet{
	ResultSet rs;
	String returnJSon,id,nikename,family,intro,birth,year,mobile,email;
	boolean sex,ifname,ifsex,ifmobile,ifemail;
	JSONObject jsonobj;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		String id = req.getParameter("id");
		String sql = "select * from students where id = \""+id +"\"";
		Untils untils = new Untils();
		jsonobj = new JSONObject();
		try {
			rs = untils.select(sql);
			while (rs.next()) {
				nikename =rs.getString("nikename");
				family = rs.getString("family");
				intro = rs.getString("introduction");
				birth = rs.getString("birth");
				year = rs.getString("year");
				mobile =rs.getString("mobile");
				email = rs.getString("email");
				System.out.println("intro:"+intro+";nikename:"+nikename);
				jsonobj.put("nikename", nikename);
				jsonobj.put("family", family);
				jsonobj.put("intro", intro);
				jsonobj.put("birth", birth);
				jsonobj.put("year", year);
				jsonobj.put("mobile", mobile);
				jsonobj.put("email", email);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonobj.put("result", 0);
		}
		System.out.println(jsonobj.toString());   
		resp.setHeader("content-type","application/json;charset=utf-8");
		resp.setCharacterEncoding("utf8");
		PrintWriter out = resp.getWriter(); 
        out.println(jsonobj);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
