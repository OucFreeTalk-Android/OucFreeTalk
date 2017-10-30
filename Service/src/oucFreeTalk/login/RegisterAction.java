package oucFreeTalk.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.omg.CORBA.StringHolder;
@WebServlet("/register.do")
public class RegisterAction extends HttpServlet{
	String truePassword;
	ResultSet rs;
	String returnJSon;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");  
		String user = req.getParameter("username");
		String pass = req.getParameter("password");
		Untils untils = new Untils();
		String username = user +"";
		String intro =  "海大学生";
		Calendar c = Calendar.getInstance();
		String birth ="1996-01-01" ;
		String year = c.get(Calendar.YEAR) + "";
		String selectSQL = "select PASSWORD FROM students WHERE id = " + user ;
		String insertSQL = "INSERT INTO students(id, password,nikename,name,sex,birth,year,pic,ifname,ifemail,ifbirth,ifmobile,ifsex,introduction)"
				+ " VALUES("+ user +","+ pass +"," + username +","+ username +","+ false +", '"+ birth +"' ,"+ year +
				"," + "pic" + "," + false + "," + false + "," + false + "," + false + "," + false + ", '" + intro + "' )" ;
		try {
			rs = untils.select(selectSQL);
			while (rs.next()) {  
				truePassword = rs.getString("password");
				System.out.println(truePassword);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (truePassword != null) {
			returnJSon = "{'result':" + 0 + "}";
		}else {
			try {
				untils.insert(insertSQL);
				returnJSon = "{'result':" + 1+ "}";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnJSon = "{'result':" + 2 + "}";
			}
		}
		resp.setCharacterEncoding("UTF-8");	
		PrintWriter out = resp.getWriter();
		JSONObject outjson = new JSONObject(returnJSon);
		out.println(outjson);
		untils.close();
		
		out.flush();
		out.close();
	}
}
