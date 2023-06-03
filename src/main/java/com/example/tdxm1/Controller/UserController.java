package com.example.tdxm1.Controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.sql.*;
import java.util.*;

public class UserController {
    private Connection connection;
    private String databaseUrl="jdbc:mysql://localhost:3306/jinandaxue";
    private String databaseUser="root";
    private String databasePassword="1234";

    public boolean userExists(int wxid){
        connection=new SQLController().ConnectToSql(databaseUrl,databaseUser,databasePassword);
        if(connection!=null){
            try {
                String sql="select count(*) from `user` where wxid=" + wxid + "limit 1";
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet Judge = ps.executeQuery();
                Judge.next();
                int ct = Judge.getInt("userid");
                System.out.println(ct);
                if(ct == 0){
                    //该用户不存在
                    return false;
                }else {
                    //该用户存在
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public JSONObject getAdmin(@RequestBody Map<String,String> requestBody){
        JSONObject jsonObject=new JSONObject();
        Connection connection=new SQLController().ConnectToSql("jdbc:mysql://localhost:3306/jidazhidemai","root","gFL123456"+"");
        Boolean isAdmin=false;
        if(connection!=null){
            try {
                int _wxid=Integer.parseInt(requestBody.get("wxid"));
                int _userid=0;
                String sql1="select count(*) from `user` where wxid="+_wxid+"limit 1";
                PreparedStatement preparedStatement1=connection.prepareStatement(sql1);
                ResultSet resultSet1=preparedStatement1.executeQuery();
                resultSet1.next();
                int ct1=resultSet1.getInt("wxid");
                if(ct1==0){
                    isAdmin=false;
                    jsonObject.put("status","FAIL_NOMATCH");
                    return jsonObject;
                }else {
                    String sql2="select userid from `user` where wxid="+_wxid;
                    Statement statement2=connection.createStatement();
                    ResultSet resultSet2=statement2.executeQuery(sql2);
                    while (resultSet2.next()){
                        _userid=resultSet2.getInt("userid");
                    }
                    String sql3="select count(*) from `admin` where userid=" + _userid + "limit 1";
                    PreparedStatement ps3 = connection.prepareStatement(sql3);
                    ResultSet resultSet3 = ps3.executeQuery();
                    resultSet3.next();
                    int ct = resultSet3.getInt("userid");
                    if(ct == 0){
                        isAdmin=false;
                    }else{
                        isAdmin=true;
                    }
                    jsonObject.put("status","COMPLETE");
                    jsonObject.put("isAdmin",isAdmin);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    @PostMapping("/openid")
    public JSONObject getOpenID(@RequestBody Map<String,String> requestBody){
        JSONObject jsonObject=new JSONObject();
        Connection connection=new SQLController().ConnectToSql("jdbc:mysql://localhost:3306/jidazhidemai","root","gFL123456"+"");
        if(connection!=null){
            try{
                String wxcode=requestBody.get("wxcode");
                String url="https://api.weixin.qq.com/sns/jscode2session?"
                        +"appid=wxbbd9f06542d698c7"
                        +"&secret=506e627741e3319512e1bcd426c001fb"
                        +"&js_code="+wxcode
                        +"&grant_type=authorization_code";
                BufferedReader in=null;
                try {
                    URL weChatUrl = new URL(url);
                    // 打开和URL之间的连接
                    URLConnection urlConnection = weChatUrl.openConnection();
                    // 设置通用的请求属性
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    // 建立实际的连接
                    urlConnection.connect();
                    // 定义 BufferedReader输入流来读取URL的响应
                    in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    jsonObject.put("status","COMPLETE");
                    jsonObject.put("wxid",sb.toString());
                    return jsonObject;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return  jsonObject;
    }

    public JSONObject getUser(@RequestBody Map<String,String> requestBody){
        JSONObject json = new JSONObject();
        connection=new SQLController().ConnectToSql(databaseUrl,databaseUser,databasePassword);
        if(connection!=null){
            try {
                int _wxid=Integer.parseInt(requestBody.get("wxid"));
                if(userExists(_wxid)){
                    Statement statement=connection.createStatement();
                    ResultSet resultSet=statement.executeQuery("select * from User where wxid=_wxid");
                    while (resultSet.next()){
                        json.put("status","COMPLETE");
                        json.put("wxid",resultSet.getInt("wxid"));
                        json.put("userid",resultSet.getInt("userid"));
                        json.put("username",resultSet.getString("username"));
                        json.put("desc",resultSet.getString("desc"));
                        json.put("gender",resultSet.getInt("gender"));
                        json.put("campus",resultSet.getInt("campus"));
                        json.put("birthday",resultSet.getString("birthday"));
                    }
                }else {
                    json.put("status","FAIL_NOMATCH");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }

    public JSONObject updateUser(@RequestBody Map<String,String> requestBody){
        JSONObject json = new JSONObject();
        connection=new SQLController().ConnectToSql(databaseUrl,databaseUser,databasePassword);
        if(connection!=null){
            try {
                if(userExists(Integer.parseInt(requestBody.get("wxid")))){
                    String sql="update User set "
                            + "wxid=\'"+requestBody.get("wxid")
                            +"\',username=\'"+requestBody.get("username")
                            +"\',desc=\'"+requestBody.get("desc")
                            +"\',gender=\'"+requestBody.get("gender")
                            +"\',campus=\'"+requestBody.get("campus")
                            +"\',birthday=\'"+requestBody.get("birthday")
                            +"where wxid=\'"+requestBody.get("wxid");
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.executeUpdate();
                    json.put("status","COMPLETE");
                    json.put("wxid",requestBody.get("wxid"));
                }else{
                    json.put("status","FAIL_NOMATCH");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }

    public JSONObject createUser(@RequestBody Map<String,String> requestBody){
        return null;
    }

    public JSONObject createFav(@RequestBody Map<String,String> requestBody){
        JSONObject json=new JSONObject();
        connection=new SQLController().ConnectToSql(databaseUrl,databaseUser,databasePassword);
        if(connection!=null){
            try {
                Integer wxid=Integer.parseInt(requestBody.get("wxid"));
                Integer postid=Integer.parseInt(requestBody.get("postid"));
                if(userExists(wxid)){
                    String sql1="select fav from `user` where wxid="+wxid;
                    Statement stmt1=connection.createStatement();
                    ResultSet rs1= stmt1.executeQuery(sql1);
                    String userOldFavs=null;
                    String userNewFavs=null;
                    List<String> userFavList=new ArrayList<>();
                    while (rs1.next()){
                        userOldFavs=rs1.getString("fav");
                        userFavList= Arrays.asList(userOldFavs.split(","));
                        if(userFavList.contains(postid.toString())){
                            //取消收藏
                            userFavList.remove(postid.toString());
                            userNewFavs=String.join(",",userFavList)+",";
                        }else{
                            //添加收藏
                            userNewFavs=userOldFavs+postid.toString()+",";
                        }
                    }
                    //更新该用户的fav字段
                    String sql2="update `user` set fav="+userNewFavs+"where wxid="+wxid;
                    Statement stmt2 = connection.createStatement();
                    stmt2.executeQuery(sql2);

                    json.put("status","COMPLETE");
                    json.put("fav",userNewFavs);
                }else{
                    json.put("status","FAIL_NOMATCH");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }

}
