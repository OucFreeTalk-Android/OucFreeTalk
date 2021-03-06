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

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSmartTagRequest;
import Until.PushMsgToTag;
import Until.Untils;

public class Addreply extends HttpServlet {
	String id, context, replyid, createTime;
	int commentId, ownlocation, postId;
	String truePassword = null;
	ResultSet rs, rl, rt;
	String returnJSon, owner, nikename;
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
			commentId = jsonObject.getInt("commentId");
			context = jsonObject.getString("context");
			replyid = jsonObject.getString("replyid");
		} catch (Exception e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
		Untils untils = new Untils();
		String select_sql = "select postlocation,ownpost,owner from postc where id = "
				+ commentId;
		System.out.println(select_sql);
		try {
			rs = untils.select(select_sql);
			while (rs.next()) {
				ownlocation = rs.getInt("postlocation");
				postId = rs.getInt("ownpost");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			returnJSon = "{\"result\": \"" + 4 + "\" }";
		}
		String selectNikename = "select nikename from students where id =" + id;
		try {
			rt = untils.select(selectNikename);
			while (rt.next()) {
				nikename = rt.getString("nikename");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date date = new Date();
		createTime = dateToString(date);
		String sql = "insert into postreply(owner,createtime,contenttext,replyto,ownlocation,state)"
				+ "values(\"" + id + "\",\"" + createTime + "\",\"" + context
				+ "\",\"" + replyid + "\"," + commentId + "," + true + ")";
		String insertNotice = "insert into notices(noticeclass,commentsid,stuid,replystuid,state,createtime)"
				+ "values(" + 2 + "," + commentId + ",\"" + id + "\",\""
				+ replyid + "\"," + true + ",\"" + createTime + "\")";
		System.out.println(insertNotice);
		try {
			untils.insert(sql);
			PushMsgToTag push = new PushMsgToTag();
			push.push(replyid, nikename,2);
			untils.insert(insertNotice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJSon = "{\"result\": \"" + 2 + "\" }";
		} catch (PushClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PushServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String update_Sql = "update posts set updatetime = \"" + createTime
				+ "\" where id = " + postId;
		try {
			untils.update(update_Sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnJSon = "{\"result\": \"" + 1 + "\" }";
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
