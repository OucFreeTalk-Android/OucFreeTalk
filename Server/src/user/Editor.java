package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.sun.swing.internal.plaf.basic.resources.basic;

import Until.Untils;

public class Editor extends HttpServlet {
	ResultSet rs;
	String returnJSon, id, nikename, family, intro, birth, year, mobile, email,
			pic;
	boolean sex, ifname, ifsex, ifmobile, ifemail;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		StringBuffer jb = new StringBuffer();
		String line = null;
		String result = "";
		try {
			// 读取输入流到StringBuffer中
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);

		} catch (Exception e) {
			/* report an error */ }

		try {
			// 使用JSONObject的parseObject方法解析JSON字符串
			JSONObject jsonObject = new JSONObject(jb.toString());
			id = jsonObject.getString("id");
			nikename = jsonObject.getString("nikename");
			sex = jsonObject.getBoolean("sex");
			birth = jsonObject.getString("birth");
			year = jsonObject.getString("year");
			family = jsonObject.getString("family");
			pic = jsonObject.getString("pic");
			ifname = jsonObject.getBoolean("ifname");
			ifsex = jsonObject.getBoolean("ifsex");
			ifmobile = jsonObject.getBoolean("ifmobile");
			ifemail = jsonObject.getBoolean("ifemail");
			intro = jsonObject.getString("intro");

		} catch (Exception e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
		String sql = "update students set nikename = \"" + nikename
				+ "\",sex = " + sex + ",birth = \"" + birth + "\",year = \""
				+ year + "\",family = \"" + family + "\",ifname = " + ifname
				+ ",ifsex =" + ifsex + ",ifmobile =" + ifmobile + ",ifemail = "
				+ ifemail + ",pic =\"" + pic + "\",introduction = \"" + intro
				+ "\" where id = \"" + id + "\"";
		System.out.println(sql);
		Untils untils = new Untils();
		try {
			untils.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJSon = "{'result':" + 2 + "}";
		}
		returnJSon = "{'result':" + 1 + "}";
		JSONObject rjson = new JSONObject(returnJSon);
		resp.setHeader("content-type", "application/json;charset=utf-8");
		resp.setCharacterEncoding("utf8");
		PrintWriter out = resp.getWriter();
		out.println(rjson);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

}
