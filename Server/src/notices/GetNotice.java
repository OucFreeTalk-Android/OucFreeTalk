package notices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Until.Untils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.tools.jar.resources.jar;

public class GetNotice extends HttpServlet {
	ResultSet rs,rl,re;
	JSONObject jsonObject,returnjson;
	JSONArray jsonArray;
	String stuid,time,pic,nikename,title,postTime,context;
	int noticeClass,postid,commentid,count,postlocation,replyid,messageid;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String replystuid = req.getParameter("id");
		String selectNotice = "select * from notices where replystuid = "
				+ replystuid+ " order by createtime desc";
		System.out.println(selectNotice);
		Untils untils = new Untils();
		count = 0;
		jsonArray = new JSONArray();
		try {
			rs = untils.select(selectNotice);
			while(rs.next()) {
				stuid = rs.getString("stuid");
				noticeClass = rs.getInt("noticeClass");
				time = rs.getString("createtime");
				if (noticeClass == 1) {
					postid = rs.getInt("postid");
				}else if(noticeClass == 2){
					commentid = rs.getInt("commentsid");
				}else if (noticeClass == 3) {
					replyid = rs.getInt("replyid");
				}
				if (noticeClass == 1) {
					String selectPost = "select title,contenttext,createtime from posts where id = "+postid;
					re = untils.select(selectPost);
					while(re.next()) {
						title = re.getString("title");
						postTime = re.getString("createtime");
						context =re.getString("contenttext");
					}	
				}else if (noticeClass == 2) {
					String selectPost = "select postlocation,body,createtime from postc where id = "+commentid;
					re = untils.select(selectPost);
					while(re.next()) {
						postTime = re.getString("createtime");
						context =re.getString("body");
						postlocation = re.getInt("postlocation");
					}	
				}else if (noticeClass == 3) {
					String selectPost = "select content,createtime from stumessage where id = "+replyid;
					re = untils.select(selectPost);
					while(re.next()) {
						postTime = re.getString("createtime");
						context =re.getString("content");
					}	
				}
				String selectUser = "select * from students where id = "+stuid;
				System.out.println(selectUser);
				rl = untils.select(selectUser);
				while(rl.next()) {
					pic = rl.getString("pic");
					nikename = rl.getString("nikename");
				}
//				if (!stuid.equals(replystuid)) {
					jsonObject = new JSONObject();
					jsonObject.put("stuid", stuid);
					jsonObject.put("time", time);
					if (noticeClass == 1) {
						jsonObject.put("postid", postid);
						jsonObject.put("title", title);
					}else if(noticeClass == 2){
						jsonObject.put("commentid", commentid);
						jsonObject.put("postlocation", postlocation);
					}else if (noticeClass == 3) {
						jsonObject.put("replyid", replyid);
					}
					jsonObject.put("noticeClass", noticeClass);
					jsonObject.put("pic", pic);
					jsonObject.put("context", context);
					jsonObject.put("postTime", postTime);
					jsonObject.put("nikename", nikename);
					jsonArray.add(count, jsonObject);
					count = count + 1;
//				}
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
