package com.example.tdxm1.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.JobSheets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController {
    private SQLController sqlController;
    public JSONObject getUser(@RequestParam JSONObject frontJson){
        SQLController sqlController=new SQLController();
        JSONObject json = new JSONObject();
        Connection connection=sqlController.ConnectToSql("jdbc:mysql://localhost:3306/jinandaxue","root","1234"+"");
        if(connection!=null){
            try {
                int _wxid=frontJson.getInteger("wxid");
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }

    public JSONObject updateUser(@RequestParam JSONObject frontJson){
        SQLController sqlController=new SQLController();
        JSONObject json = new JSONObject();
        Connection connection=sqlController.ConnectToSql("jdbc:mysql://localhost:3306/jinandaxue","root","1234"+"");
        if(connection!=null){
            try {
                String sql="update User set "
                        + "wxid=\'"+frontJson.getInteger("wxid")
                        +"\',username=\'"+frontJson.getString("username")
                        +"\',desc=\'"+frontJson.getString("desc")
                        +"\',gender=\'"+frontJson.getInteger("gender")
                        +"\',campus=\'"+frontJson.getInteger("campus")
                        +"\',birthday=\'"+frontJson.getString("birthday")
                        +"where wxid=\'"+frontJson.getInteger("wxid");
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.executeUpdate();
                json.put("status","COMPLETE");
                json.put("wxid",frontJson.getInteger("wxid"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }

    public JSONObject createUser(@RequestParam JSONObject frontJson){
        SQLController sqlController=new SQLController();
        JSONObject json = new JSONObject();
        Connection connection=sqlController.ConnectToSql("jdbc:mysql://localhost:3306/jinandaxue","root","1234"+"");
        if(connection!=null){
            int wxid=frontJson.getInteger("wxid");
            String username=frontJson.getString("username");
            String sql="";
            json.put("status","COMPLETE");
            json.put("wxid",wxid);
//            json.put("userid",userid);
        }
        return json;
    }

    public JSONObject createFav(@RequestParam JSONObject frontJson){
        JSONObject json=new JSONObject();
        SQLController sqlController=new SQLController();
        Connection connection=sqlController.ConnectToSql("jdbc:mysql://localhost:3306/jinandaxue","root","1234"+"");
        if(connection!=null){
            Integer wxid=frontJson.getInteger("wxid");
            Integer postid=frontJson.getInteger("postid");

            try {
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }
}
