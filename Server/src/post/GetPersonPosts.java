package post;

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

public class GetPersonPosts extends HttpServlet{
	 ResultSet rs;
	    ResultSet rl;
	    String returnJSon;
	    String title,intro;
	    String createtime;
	    String updatetime;
	    String owner;
	    String content;
	    String nikename;
	    String pic;
	    int id;
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
        String username = req.getParameter("id");
        jsonArray = new JSONArray();
        String selectPost = "SELECT * FROM posts where owner = "+ username;
        System.out.println(selectPost);
        Untils untils = new Untils();
        page = 0;
        try {
            rs = untils.select(selectPost);
            while (rs.next()) {
                id = rs.getInt("id");
                title = rs.getString("title");
                owner = rs.getString("owner");
                content = rs.getString("contenttext");
                createtime = rs.getString("createtime");
                jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("title", title);
                jsonObject.put("owner", owner);
                jsonObject.put("content", content);
                jsonObject.put("createtime", createtime);
                jsonObject.put("pic", pic);
                System.out.println(jsonObject);
                System.out.println(page);
                jsonArray.add(page,jsonObject);
                page = page +1;
            }
            String selectUser = "select nikename,pic,introduction from students where id = "+ username;
            System.out.println(selectUser);
            rl = untils.select(selectUser);
            while (rl.next()) {
                nikename = rl.getString("nikename");
                pic = rl.getString("pic");
                intro = rl.getString("introduction");
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(nikename);
        System.out.println(intro);
        JSONObject returnJSon = new JSONObject();
        returnJSon.put("nikename", nikename);
        returnJSon.put("intro", intro);
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
