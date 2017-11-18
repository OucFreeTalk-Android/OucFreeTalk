package stumessage;

import java.io.BufferedReader;
import java.io.IOException;
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

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;

import Until.PushMsgToTag;
import Until.Untils;

public class AddMessage extends HttpServlet {
	ResultSet rs, rl;
	String id, receiveid, content, nikename, createTime, returnJSon;
	int replyid;
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
			receiveid = jsonObject.getString("receiveid");
			content = jsonObject.getString("content");
		} catch (Exception e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		} ;
		Untils untils = new Untils();
		String selectNikename = "select nikename from students where id =" + id;
		try {
			rs = untils.select(selectNikename);
			while (rs.next()) {
				nikename = rs.getString("nikename");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			returnJSon = "{\"result\": \"" + 2 + "\" }";
			e.printStackTrace();
		}
		createTime = dateToString(date);
		String insertMessage = "insert into stumessage(postid,receiveid,state,createtime,content)"
				+ "values(\"" + id + "\",\"" + receiveid + "\"," + true + ",\""
				+ createTime + "\",\"" + content + "\")";
		String selectId = "SELECT LAST_INSERT_ID()";
		System.out.println(insertMessage);
		try {
			untils.insert(insertMessage);
			PushMsgToTag pushMsgToTag = new PushMsgToTag();
			pushMsgToTag.push(receiveid, nikename, 3);
			rl = untils.select(selectId);
			while (rl.next()) {
				replyid = rl.getInt("LAST_INSERT_ID()");
			}
			String insertNotice = "insert into notices(noticeclass,stuid,replyid,replystuid,state,createtime)"
					+ "values(" + 3 + ",\"" + id + "\"," + replyid + ",\""
					+ receiveid + "\"," + true + ",\"" + createTime + "\")";
			untils.insert(insertNotice);
			returnJSon = "{\"result\": \"" + 1 + "\" }";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			returnJSon = "{\"result\": \"" + 2 + "\" }";
			e.printStackTrace();
		} catch (PushClientException e) {
			// TODO Auto-generated catch block
			returnJSon = "{\"result\": \"" + 2 + "\" }";
			e.printStackTrace();
		} catch (PushServerException e) {
			// TODO Auto-generated catch block
			returnJSon = "{\"result\": \"" + 2 + "\" }";
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
