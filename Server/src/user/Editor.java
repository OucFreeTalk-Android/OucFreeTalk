package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Editor extends HttpServlet{
	String truePassword = null;
	ResultSet rs;
	String returnJSon,id,pass,nikename;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

}
