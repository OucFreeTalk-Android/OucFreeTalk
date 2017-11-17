package friend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.sun.prism.impl.Disposer.Target;

import Until.Untils;

public class DeleteMeFocus extends HttpServlet {
	String id, target, returnJSon;
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
			target = jsonObject.getString("target");
		} catch (Exception e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
		String deleteFocus = "delete from friends where focus =\"" + id
				+ "\" and befocus = \"" + target + "\"";
		Untils untils = new Untils();
		try {
			untils.delete(deleteFocus);
			returnJSon = "{\"result\": \"" + 1 + "\" }";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJSon = "{\"result\": \"" + 0 + "\" }";
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
		super.doPost(req, resp);
	}
}
