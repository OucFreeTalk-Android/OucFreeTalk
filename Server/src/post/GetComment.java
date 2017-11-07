package post;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Until.Untils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetComment extends HttpServlet{
	 ResultSet rs;
	 ResultSet rl;
	 String returnJSon;
	 String title;
	 String createtime;
	 String updatetime;
	 String owner;
	 String content;
	 String nikename;
	 String replyid;
     String pic;
     int id;
	 int realbody;
	 int body;
	 int page = 0;
	 JSONObject jsonObject;
	 JSONArray jsonArray;	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int commentid = Integer.parseInt(req.getParameter("commentid"));
        int index = Integer.parseInt(req.getParameter("index"));
        jsonArray = new JSONArray();
        String selectPost = "SELECT * FROM postreply where ownlocation = "+ commentid;
        System.out.println(selectPost);
        Untils untils = new Untils();
        try {
			rs = untils.select(selectPost);
			while (rs.next()) {
                id = rs.getInt("id");
                owner = rs.getString("owner");
                content = rs.getString("contenttext");
                createtime = rs.getString("createtime");
                replyid = rs.getString("replyto");
                String selectUser = "select nikename from students where id = \""+ owner + "\"";
                System.out.println(selectUser);
                rl = untils.select(selectUser);
                while (rl.next()) {
                    nikename = rl.getString("nikename");
                }
                jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("stuid", owner);
                jsonObject.put("commentcontext", content);
                jsonObject.put("createtime", createtime);
                jsonObject.put("replyid", replyid);
                jsonObject.put("nikename", nikename);
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
        PrintStream out = new PrintStream(resp.getOutputStream());
        out.println(returnJSon);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
