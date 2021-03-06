package post;

import Until.Untils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.swing.internal.plaf.basic.resources.basic;

public class GetPosts extends HttpServlet {
	ResultSet rs;
	ResultSet rl, rt, re, ru;
	String returnJSon;
	String title;
	String createtime;
	String updatetime;
	String owner;
	String content, mNikename, mIntro, mBirth, mYear, mPic;
	String nikename;
	String pic;
	boolean mSex;
	int id, focus, befocus;
	int outbody;
	int realbody;
	int body;
	int page;
	JSONObject jsonObject;
	JSONArray jsonArray;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		int pclass = Integer.parseInt(req.getParameter("pclass"));
		int index = Integer.parseInt(req.getParameter("index"));
		String userid = req.getParameter("id");
		int perpage = 20;
		jsonArray = new JSONArray();
		String selectPost = "SELECT * FROM posts where ownclass = " + pclass
				+ " order by updatetime desc";
		System.out.println(selectPost);
		Untils untils = new Untils();
		page = 0;
		mNikename = "";
		mBirth = "";
		mIntro = "";
		mYear = "";
		try {
			rs = untils.select(selectPost);
			while (rs.next()) {
				id = rs.getInt("id");
				title = rs.getString("title");
				owner = rs.getString("owner");
				outbody = rs.getInt("realbody");
				content = rs.getString("contenttext");
				updatetime = rs.getString("updatetime");
				String selectUser = "select nikename,pic from students where id = \""
						+ owner + "\"";
				System.out.println(selectUser);
				rl = untils.select(selectUser);
				while (rl.next()) {
					nikename = rl.getString("nikename");
					pic = rl.getString("pic");
				}
				jsonObject = new JSONObject();
				jsonObject.put("id", id);
				jsonObject.put("title", title);
				jsonObject.put("owner", owner);
				jsonObject.put("realbody", outbody);
				jsonObject.put("content", content);
				jsonObject.put("updatetime", updatetime);
				jsonObject.put("nikename", nikename);
				jsonObject.put("pic", pic);
				System.out.println(jsonObject);
				System.out.println(page);
				jsonArray.add(page, jsonObject);
				page = page + 1;
			}
			String selectFocus = "select count(focus) as focus from friends where befocus ="
					+ userid;
			String selectBefocus = "select count(befocus) as befocus from friends where focus ="
					+ userid;
			rt = untils.select(selectFocus);
			while (rt.next()) {
				focus = rt.getInt("focus");
			}
			re = untils.select(selectBefocus);
			while (re.next()) {
				befocus = re.getInt("befocus");
			}
			String selectMe = "select * from students where id =" + userid;
			ru = untils.select(selectMe);
			while (ru.next()) {
				mNikename = ru.getString("nikename");
				mIntro = ru.getString("introduction");
				mBirth = ru.getString("birth");
				mYear = ru.getString("year");
				mSex = ru.getBoolean("sex");
				mPic = ru.getString("pic");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject returnJSon = new JSONObject();
		returnJSon.put("mPic", mPic);
		returnJSon.put("focus", focus);
		returnJSon.put("mNikename", mNikename);
		returnJSon.put("mIntro", mIntro);
		returnJSon.put("mYear", mYear);
		returnJSon.put("mBirth", mBirth);
		returnJSon.put("mSex", mSex);
		returnJSon.put("befocus", befocus);
		returnJSon.put("search", jsonArray);
		returnJSon.put("allpage", page);
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
