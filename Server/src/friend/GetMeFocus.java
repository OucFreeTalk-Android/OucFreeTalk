package friend;

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

public class GetMeFocus extends HttpServlet {
	ArrayList<String> user = new ArrayList<String>();
	ResultSet rs, rl;
	JSONObject returnjson, jsonObject;
	JSONArray jsonArray;
	String id, nikename, intro, birth, year, pic,createtime;
	boolean sex;
	int count;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		id = req.getParameter("id");
		String selectfocus = "SELECT befocus,createtime FROM friends where focus =\"" + id
				+ "\"";
		System.out.println(selectfocus);
		Untils untils = new Untils();
		count = 0;
		jsonArray = new JSONArray();
		try {
			rs = untils.select(selectfocus);
			while (rs.next()) {
				String userid = rs.getString("befocus");
				String createtime =rs.getString("createtime");
				String selectUser = "select * from students where id =\""
						+ userid + "\"";
				System.out.println(selectUser);
				rl = untils.select(selectUser);
				while (rl.next()) {
					nikename = rl.getString("nikename");
					intro = rl.getString("introduction");
					birth = rl.getString("birth");
					year = rl.getString("year");
					sex = rl.getBoolean("sex");
					pic = rl.getString("pic");
				}
				jsonObject = new JSONObject();
				jsonObject.put("stuid", userid);
				jsonObject.put("createtime", createtime);
				jsonObject.put("nikename", nikename);
				jsonObject.put("intro", intro);
				jsonObject.put("birth", birth);
				jsonObject.put("year", year);
				jsonObject.put("sex", sex);
				jsonObject.put("pic", pic);
				System.out.println(jsonObject.toString());
				jsonArray.add(count, jsonObject);
				count = count + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnjson = new JSONObject();
		returnjson.put("search", jsonArray);
		returnjson.put("count", count);

		resp.setHeader("content-type", "application/json;charset=utf-8");
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
