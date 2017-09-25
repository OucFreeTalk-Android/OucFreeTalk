package oucFreeTalk.post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.acl.Owner;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.deploy.uitoolkit.impl.fx.Utils;

import oucFreeTalk.login.Untils;

public class AddPost extends HttpServlet{
	ResultSet rs;
	String returnJSon;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");  
		BufferedReader reader = req.getReader();
		String json= reader.readLine();
		JSONObject jsonObject = new JSONObject(json);
		String title = jsonObject.getString("title");
		String context = jsonObject.getString("context");
		int pclass = jsonObject.getInt("pclass");
		int id = jsonObject.getInt("id");
		Untils untils = new Untils();
		String creatTime = getTime();
		String selectSQL = "select * FROM students WHERE id = " + id ;
		String ADDPOST = "INSERT INTO posts(title,createtime,updatetime,realbody,"
				+ "owner,contentext,ownclass) values(" + title +","+ creatTime+","+
				creatTime+",1"+ "," + id + "," + context +","+pclass+")";
		try {
			rs = untils.select(selectSQL);
		} catch (SQLException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rs == null) {
			returnJSon = "{'result':" + 0 + "}";
		}else {
			try {
				untils.insert(ADDPOST);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnJSon = "{'result':" + 2 + "}";
			}
			returnJSon = "{'result':" + 1 + "}";
		}
		resp.setCharacterEncoding("UTF-8");	
		PrintWriter out = resp.getWriter();
		JSONObject outjson = new JSONObject(returnJSon);
		out.println(outjson);
		untils.close();
		out.flush();
		out.close();
	}
	public String getTime() {
		Date date = new Date(0);
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		return time;
	}

}
