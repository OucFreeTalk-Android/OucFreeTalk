package oucFreeTalk.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import oucFreeTalk.login.Untils;

public class GetUser extends HttpServlet{
	ResultSet rs;
	String returnJSon;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuffer jb = new StringBuffer();
        String line = null;
        JSONObject jsonObject;
        try {
            //读取输入流到StringBuffer中
            BufferedReader reader = req.getReader();
              while ((line = reader.readLine()) != null)
                jb.append(line);
               
        } catch (Exception e) { /*report an error*/ }
        try {
            //使用JSONObject的parseObject方法解析JSON字符串
            jsonObject = new JSONObject(jb.toString());       
        } catch (Exception e) {
          // crash and burn
          throw new IOException("Error parsing JSON request string");
        }
        
		String user = jsonObject.getString("username");
		String pass = jsonObject.getString("password");
		String selectSQL = "select * from students where id = " + user; 
		Untils untils = new Untils();
		try {
			rs = untils.select(selectSQL);
			returnJSon = untils.resultSetToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setCharacterEncoding("UTF-8");	
		PrintWriter out = resp.getWriter();
		JSONObject outjson = new JSONObject(returnJSon);
		out.println(outjson);
		untils.close();
		out.flush();
		out.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
