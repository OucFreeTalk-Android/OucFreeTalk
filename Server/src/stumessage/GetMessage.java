package stumessage;

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

public class GetMessage extends HttpServlet{
	String createTime,content,pId,rId,pPic,pNikename,rPic,rNikename;
	ResultSet rs,rl,rt;
	int count;
	JSONObject jsonObject,returnjson;
	JSONArray jsonArray;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postid = req.getParameter("postid");
		String receiveid = req.getParameter("receiveid");
		String selectMessage= "select * from stumessage where postid in(\""+postid+"\",\""+receiveid+
				"\") and receiveid in (\""+receiveid+"\",\""+postid+ "\") order by createtime";
		System.out.println(selectMessage);
		jsonArray = new JSONArray();
		returnjson = new JSONObject();
		count = 0;
		Untils untils = new Untils();
		try {
			rs = untils.select(selectMessage);
			while(rs.next()) {
				pId = rs.getString("postid");
				rId = rs.getString("receiveid");
				createTime = rs.getString("createtime");
				content = rs.getString("content");
				String selectPost = "select pic,nikename from students where id ="+pId ; 
				String selectRecieve = "select pic,nikename from students where id ="+rId ; 
				rl = untils.select(selectPost);
				while(rl.next()) {
					pPic = rl.getString("pic");
					pNikename = rl.getString("nikename");
				}
				rt = untils.select(selectRecieve);
				while(rt.next()) {
					rPic = rt.getString("pic");
					rNikename = rt.getString("nikename");
				}
				jsonObject = new JSONObject();
				jsonObject.put("postid", pId);
				jsonObject.put("receiveid", rId);
				jsonObject.put("postPic", pPic);
				jsonObject.put("postNikename", pNikename);
				jsonObject.put("recievePic", rPic);
				jsonObject.put("recieveNikename", rNikename);
				jsonObject.put("createTime", createTime);
				jsonObject.put("content", content);
				jsonArray.add(count,jsonObject);
				count = count + 1;
			}
			returnjson.put("search", jsonArray);
			returnjson.put("count", count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
