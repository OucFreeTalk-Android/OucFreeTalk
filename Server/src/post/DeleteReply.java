package post;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Until.Untils;

public class DeleteReply extends HttpServlet {
	String returnJSon;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		int replyid = Integer.parseInt(req.getParameter("replyid"));
		String deletePost = "delete from postreply where id = " + replyid;
		Untils untils = new Untils();
		try {
			untils.delete(deletePost);
			returnJSon = "{\"del_result\": \"" + 1 + "\" }";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			returnJSon = "{\"del_result\": \"" + 0 + "\" }";
			e.printStackTrace();

		}
		JSONObject rjson = new JSONObject(returnJSon);
		resp.setHeader("content-type", "application/json;charset=utf-8");
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
