package post;

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

public class GetMeFocusPost extends HttpServlet {
	ArrayList<String> user = new ArrayList<String>();
	ResultSet rs, rl, rt;
	JSONObject returnjson, jsonObject;
	JSONArray jsonArray;
	String id, nikename, intro, birth, year, title, updatetime, content, pic;
	int postid, outbody;
	boolean sex;
	int count, allpage;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		id = req.getParameter("id");
		String selectfocus = "SELECT befocus FROM friends where focus =\"" + id
				+ "\"";
		System.out.println(selectfocus);
		Untils untils = new Untils();
		count = 0;
		allpage = 0;
		jsonArray = new JSONArray();
		try {
			rs = untils.select(selectfocus);
			while (rs.next()) {
				String userid = rs.getString("befocus");
				String selectPost = "SELECT * FROM posts  where owner =\""
						+ userid + "\"";
				String selectUser = "select nikename,pic from students where id = \""
						+ userid + "\"";

				System.out.println(selectPost);
				System.out.println(selectUser);
				rl = untils.select(selectPost);
				while (rl.next()) {
					postid = rl.getInt("id");
					title = rl.getString("title");
					outbody = rl.getInt("realbody");
					content = rl.getString("contenttext");
					updatetime = rl.getString("updatetime");
					rt = untils.select(selectUser);
					while (rt.next()) {
						nikename = rt.getString("nikename");
						pic = rt.getString("pic");
					}
					jsonObject = new JSONObject();
					jsonObject.put("id", postid);
					jsonObject.put("title", title);
					jsonObject.put("owner", userid);
					jsonObject.put("realbody", outbody);
					jsonObject.put("content", content);
					jsonObject.put("updatetime", updatetime);
					jsonObject.put("nikename", nikename);
					jsonObject.put("pic", pic);
					System.out.println(jsonObject);
					System.out.println(count);
					jsonArray.add(count, jsonObject);
					allpage = allpage + 1;
				}
				count = count + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject returnJSon = new JSONObject();
		returnJSon.put("search", jsonArray);
		returnJSon.put("count", count);
		returnJSon.put("allpage", allpage);
		resp.setHeader("content-type", "application/json;charset=utf-8");
		resp.setCharacterEncoding("utf8");
		PrintWriter out = resp.getWriter();
		out.println(returnJSon);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
