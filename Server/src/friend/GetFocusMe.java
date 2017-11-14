package friend;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Until.Untils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetFocusMe extends HttpServlet{
	ArrayList<String> user = new ArrayList<String>();
	ResultSet rs,rl;
	JSONObject returnjson,jsonObject;
	JSONArray jsonArray;
	String id,nikename,intro,birth,year;
	boolean sex;
	int count;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		id = req.getParameter("id");
        String selectfocus = "SELECT focus FROM friends where befocus =\""+ id+"\"";
        System.out.println(selectfocus);
        Untils untils = new Untils();
        count = 0;
        jsonArray = new JSONArray();
        try {
			rs = untils.select(selectfocus);
			while (rs.next()) {
				String userid = rs.getString("focus");
				String selectUser = "select * from students where id =\"" +userid+ "\"";
				System.out.println(selectUser);
				rl = untils.select(selectUser);
				while (rl.next()) {
					nikename =rl.getString("nikename");
					intro = rl.getString("introduction");
					birth = rl.getString("birth");
					year = rl.getString("year");
					sex = rl.getBoolean("sex");					
				}
				jsonObject = new JSONObject();
				jsonObject.put("stuid", userid);
				jsonObject.put("nikename", nikename);
				jsonObject.put("intro", intro);
				jsonObject.put("birth", birth);
				jsonObject.put("year", year);
				jsonObject.put("sex", sex);
				System.out.println(jsonObject.toString());
				jsonArray.add(count,jsonObject);
				count = count+1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        returnjson = new JSONObject();
        returnjson.put("search", jsonArray);
        returnjson.put("count", count);
        
        resp.setHeader("content-type","application/json;charset=utf-8");
		resp.setCharacterEncoding("utf8");
		PrintWriter out = resp.getWriter(); 
        out.println(returnjson);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
