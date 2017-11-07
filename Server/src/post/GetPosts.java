package post;

import Until.Untils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetPosts extends HttpServlet {
    ResultSet rs;
    ResultSet rl;
    String returnJSon;
    String title;
    String createtime;
    String updatetime;
    String owner;
    String content;
    String nikename;
    String pic;
    int id;
    int realbody;
    int body;
    int page = 0;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        int pclass = Integer.parseInt(req.getParameter("pclass"));
        int index = Integer.parseInt(req.getParameter("index"));
        int perpage = 20;
        jsonArray = new JSONArray();
        String selectPost = "SELECT * FROM posts where ownclass = "+ pclass;
        System.out.println(selectPost);
        Untils untils = new Untils();
        try {
            rs = untils.select(selectPost);
            while (rs.next()) {
                id = rs.getInt("id");
                title = rs.getString("title");
                owner = rs.getString("owner");
                content = rs.getString("contenttext");
                createtime = rs.getString("createtime");
                String selectUser = "select nikename,pic from students where id = \""+ owner + "\"";
                System.out.println(selectUser);
                rl = untils.select(selectUser);
                while (rl.next()) {
                    nikename = rl.getString("nikename");
                    pic = rl.getString("pic");
                }
                jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("title", title);
                jsonObject.put("owner", owner);
                jsonObject.put("content", content);
                jsonObject.put("createtime", createtime);
                jsonObject.put("nikename", nikename);
                jsonObject.put("pic", pic);
                System.out.println(jsonObject);
                System.out.println(page);
                jsonArray.add(page,jsonObject);
                page = page +1;
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject returnJSon = new JSONObject();
        returnJSon.put("search", jsonArray);
        returnJSon.put("allpage",page);
        PrintStream out = new PrintStream(resp.getOutputStream());
        out.println(returnJSon);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
}