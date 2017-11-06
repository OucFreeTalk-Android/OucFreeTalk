package post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Until.Untils;

public class AddComments extends HttpServlet{
	String id,context,createTime;
	int postId,realbody;
	String truePassword = null;
	ResultSet rs;
	String returnJSon;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 StringBuffer jb = new StringBuffer();
         String line = null;
         String result = "";
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
             postId = jsonObject.getInt("postid");
             context = jsonObject.getString("context");
                
         } catch (Exception e) {
           // crash and burn
           throw new IOException("Error parsing JSON request string");
         }
         Untils untils = new Untils();
         String select_sql = "select realbody from posts where id = "+postId;
         try {
			rs = untils.select(select_sql);
			while (rs.next()) {
	             realbody = rs.getInt("realbody");
	         }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			returnJSon = "{\"result\": \"" + 4 + "\" }";
			
		}
         Date date = new Date();
         createTime = dateToString(date);
         realbody = realbody+1;
 		 String sql = "insert into postc(postlocation,owner,createtime,body,ownpost) "
 				 +"Values("+realbody+",\""+ id + "\",\"" + createTime +"\",\"" + context+"\","+ postId+ ")";
		 try {
			untils.insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJSon = "{\"result\": \"" + 2 + "\" }";
		}
		 returnJSon = "{\"result\": \"" + 1 + "\" }";
		 String update_Sql = "update posts set updatetime = "+ createTime+",realbody = "+realbody+",body = " +realbody+",where id = "+postId;
 		try {
			untils.update(update_Sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		JSONObject rjson = new JSONObject(returnJSon);
        PrintStream out = new PrintStream(resp.getOutputStream());
        out.println(rjson);
	}
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	public static String dateToString(Date time){ 
	    SimpleDateFormat formatter; 
	    formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
	    String ctime = formatter.format(time); 
	    return ctime; 
	} 

}
