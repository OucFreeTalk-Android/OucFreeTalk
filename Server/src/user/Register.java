package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Until.Untils;

public class Register extends HttpServlet {
	String truePassword = null;
	ResultSet rs;
	String returnJSon,id,pass,nikename;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 req.setCharacterEncoding("utf8");
		 StringBuffer jb = new StringBuffer();
         String line = null;
         String result = "";
         String intro = "海大学生";
         String birth ="1996-01-01" ;
 		 String year = "1996";
         try {
             //读取输入流到StringBuffer中
             BufferedReader reader = req.getReader();
               while ((line = reader.readLine()) != null)
                 jb.append(line);
                
         } catch (Exception e) { /*report an error*/ }
         System.out.println("json:"+URLDecoder.decode(jb.toString(),"utf-8"));
         try {
             //使用JSONObject的parseObject方法解析JSON字符串
             JSONObject jsonObject = new JSONObject(jb.toString());
             id = jsonObject.getString("id");
             pass = jsonObject.getString("password");
             nikename = jsonObject.getString("nikename");
                
         } catch (Exception e) {
           // crash and burn
           throw new IOException("Error parsing JSON request string");
         }
         String selectSQL = "select PASSWORD FROM students WHERE id = " + id ;
         String insertSQL = "INSERT INTO students(id, password,nikename,name,sex,birth,year,pic,ifname,ifemail,ifbirth,ifmobile,ifsex,introduction)"
 				+ " VALUES(\""+ id +"\",\""+ pass +"\",\"" + nikename +"\","+ "\"\" " +","+ false +", \""+ birth +"\" ,\""+ year +
 				"\"," + "\"pic\"" + "," + false + "," + false + "," + false + "," + false + "," + false + ",\"" + intro + "\")" ;
         System.out.println(insertSQL);
         Untils untils = new Untils();
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
 			returnJSon = "{'result':" + 2 + "}";
 		}else {
 			try {
 				untils.insert(insertSQL);
 				returnJSon = "{'result':" + 1+ "}";
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 				returnJSon = "{'result':" + 0 + "}";
 			}
 		}
         JSONObject rjson = new JSONObject(returnJSon);
         resp.setHeader("content-type","application/json;charset=utf-8");
 		resp.setCharacterEncoding("utf8");
 		PrintWriter out = resp.getWriter(); 
         out.println(rjson);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
}
