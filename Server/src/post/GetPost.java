package post;

import java.io.IOException;
import java.io.PrintStream;
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

public class GetPost extends HttpServlet{
	ResultSet rs;
    ResultSet rl,rp;
    String returnJSon;
    int commentid;
    String createtime;
    String updatetime;
    String owner;
    String content;
    String nikename;
    String pic;
    int postlocation;
    int realbody;
    int body,replybody;
    int page;
    JSONObject jsonObject;
    JSONArray jsonArray;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		int postid = Integer.parseInt(req.getParameter("postid"));
        int index = Integer.parseInt(req.getParameter("index"));
        jsonArray = new JSONArray();
        String selectPost = "SELECT * FROM postc where ownpost = "+ postid;
        System.out.println(selectPost);
        Untils untils = new Untils();
        page = 0;
        try {
			rs = untils.select(selectPost);
			while (rs.next()) {
                commentid = rs.getInt("id");
                owner = rs.getString("owner");
                content = rs.getString("body");
                createtime = rs.getString("createtime");
                postlocation = rs.getInt("postlocation");
                String selectUser = "select nikename,pic from students where id = \""+ owner + "\"";
                String countReply = "select count(ownlocation) as replybody from postreply where ownlocation = "+ commentid;
                System.out.println(selectUser);
                rl = untils.select(selectUser);
                while (rl.next()) {
                    nikename = rl.getString("nikename");
                    pic = rl.getString("pic");
                }
                rp = untils.select(countReply);
                while (rp.next()) {
                	replybody=rp.getInt("replybody");
				}
                jsonObject = new JSONObject();
                jsonObject.put("commentid", commentid);
                jsonObject.put("id", owner);
                jsonObject.put("replybody", replybody);
                jsonObject.put("postlocation", postlocation);
                jsonObject.put("commentcontext", content);
                jsonObject.put("createtime", createtime);
                jsonObject.put("nikename", nikename);
                jsonObject.put("pic", pic);
                System.out.println(jsonObject);
                System.out.println(page);
                jsonArray.add(page,jsonObject);
                page = page +1;
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JSONObject returnJSon = new JSONObject();
        returnJSon.put("search", jsonArray);
        returnJSon.put("allpage",page);
        resp.setHeader("content-type","application/json;charset=utf-8");
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
