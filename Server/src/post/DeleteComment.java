package post;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Until.Untils;

public class DeleteComment extends HttpServlet{
	String returnJSon;
	ResultSet rs,rl;
	int realbody,ownpost;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		int commentid = Integer.parseInt(req.getParameter("commentid"));
        String deletePost = "delete from postc where id = "+commentid;
        System.out.println("deletePost"+deletePost);
        String selectownpost = "select ownpost from postc where id = "+ commentid;
        System.out.println("selectownpost"+selectownpost);
        Untils untils = new Untils();
        try {
			
			returnJSon = "{\"del_result\": \"" + 1 + "\" }";
			rs = untils.select(selectownpost);
			while (rs.next()) {
				ownpost = rs.getInt("ownpost");
			}
			String updateRealbody = "update posts set realbody =realbody-1 where id = "+ownpost;
			System.out.println("updateRealbody"+updateRealbody);
			untils.update(updateRealbody);
			untils.delete(deletePost);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			returnJSon = "{\"del_result\": \"" + 0 + "\" }";
			e.printStackTrace();
			
		}
        JSONObject rjson = new JSONObject(returnJSon);
 		resp.setHeader("content-type","application/json;charset=utf-8");
		resp.setCharacterEncoding("utf8");
		PrintWriter out = resp.getWriter(); 
        out.println(rjson);
       
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
