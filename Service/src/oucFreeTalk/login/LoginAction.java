
package oucFreeTalk.login;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/login.do")
public class LoginAction extends HttpServlet {

	String truePassword = null;
	ResultSet rs;
	String returnJSon;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pass = req.getParameter("password");
		System.out.print("user:"+id);
		System.out.println("pass:"+pass);
		Untils untils = new Untils();
		String sql = "select PASSWORD FROM students WHERE id = " + id ;
		try {
			rs = untils.select(sql);
			while (rs.next()) {  
				truePassword = rs.getString("password");
				System.out.println(truePassword);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (truePassword == null) {
			returnJSon = "{\"result\": \"" + 0 + "\" }";
		}else if (truePassword.equals(pass)) {
			returnJSon = "{\"result\": \"" + 1 + "\" }";
		}else {
			returnJSon = "{\"result\": \"" + 2 + "\" }";
		}
		
		resp.setContentType("application/json;charset=utf-8");   
		PrintWriter out = resp.getWriter();
		JSONObject outjson =JSONObject.fromObject(returnJSon);
		out.println(outjson);
		out.flush();
		out.close();
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
