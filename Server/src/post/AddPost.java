package post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Until.Untils;

public class AddPost extends HttpServlet {
	ResultSet rs;
	String returnJSon, id, title, contentext;
	int pclass;
	int realbody = 1;
	int body = 1;
	boolean State = true;
	String createTime, updateTime;
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
		Date date = new Date();
		try {
			// 使用JSONObject的parseObject方法解析JSON字符串
			JSONObject jsonObject = new JSONObject(jb.toString());
			id = jsonObject.getString("id");
			title = jsonObject.getString("title");
			contentext = jsonObject.getString("content");
			pclass = jsonObject.getInt("pclass");
		} catch (Exception e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
		createTime = dateToString(date);
		updateTime = dateToString(date);
		Untils untils = new Untils();
		String sql = "insert into posts(title,createtime,updatetime,realbody,body,owner,contenttext,ownclass)"
				+ "VALUES(\"" + title + "\",\"" + createTime + "\",\""
				+ updateTime + "\"," + realbody + "," + body + ",\"" + id
				+ "\",\"" + contentext + "\"," + pclass + ")";
		System.out.println(sql);
		try {
			untils.insert(sql);
			returnJSon = "{\"result\": \"" + 1 + "\" }";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			returnJSon = "{\"result\": \"" + 0 + "\" }";
			e.printStackTrace();
		}

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
	public static String dateToString(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = formatter.format(time);
		return ctime;
	}
}
