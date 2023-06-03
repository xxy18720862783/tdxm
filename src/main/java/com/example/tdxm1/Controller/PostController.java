package com.example.tdxm1.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.String;
import java.sql.Statement;
import java.util.*;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

public class PostController {

    public JSONObject getPostDetail(@RequestParam JSONObject frontJson){
        JSONObject json=new JSONObject();
        return json;
    }

    public JSONObject createPost(@RequestParam JSONObject frontJson){
        JSONObject json=new JSONObject();
        return json;
    }

    public JSONObject deletePost(@RequestParam JSONObject frontJson){
        JSONObject json=new JSONObject();
        return json;
    }

    public JSONObject highlightPost(@RequestParam JSONObject frontJson){
        JSONObject json=new JSONObject();
        return json;
    }

    public JSONObject createComment(@RequestBody Map<String,String> requestBody){
        JSONObject json=new JSONObject();

        SQLController sqlController=new SQLController();
        Connection connection=sqlController.ConnectToSql("jdbc:mysql://localhost:3306/jinan","root","1234"+"");

        if(connection!=null){
            int postid=Integer.parseInt(requestBody.get("postid"));
            int wxid=Integer.parseInt(requestBody.get("wxid"));
            String content= requestBody.get("content");

            try {
                String sql1 = "select count(*) from `post` where postid=" + postid + "limit 1";
                PreparedStatement ps = connection.prepareStatement(sql1);
                ResultSet Judge = ps.executeQuery();
                Judge.next();
                int ct = Judge.getInt("postid");
                System.out.println(ct);
                if(ct == 0)
                {
                    System.out.println("该帖子不存在");
                    json.put("status","FALL_NOMATCH");
                }
                else
                {
                    System.out.println("该帖子存在");
                    //更新post表中该post的comments字段(comments字段为text,string;以下划线_分割）
                    String sql2="select * from `post` where postid="+postid;
                    Statement stmt2 = connection.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    String postOldComments = null;
                    String postNewComments=null;
                    String userOldComments=null;
                    String userNewComments=null;
                    List<String> oldCommentsList=new ArrayList<>();
                    while (rs2.next()){
                        postOldComments=rs2.getString("comments");
                        oldCommentsList=Arrays.asList(postOldComments.split(" "));
                        //给该帖子插入一条评论，commentid,poster,content
                        postNewComments=postOldComments+"{commentid:"+oldCommentsList.size()+"poster:"+wxid+"content:"+content+"} ";
                    }
                    String sql3="update `post` set comments="+postNewComments+"where postid="+postid;
                    Statement stmt3 = connection.createStatement();
                    stmt3.executeQuery(sql3);

                    //给该用户的comments字段插入该postid和commentid
                    String sql4="select comments from `user` where wxid="+wxid;
                    Statement stmt4=connection.createStatement();
                    ResultSet rs4=stmt4.executeQuery(sql4);
                    while (rs4.next()){
                        userOldComments=rs4.getString("comments");
                        oldCommentsList=Arrays.asList(postOldComments.split(" "));
                        userNewComments=userOldComments+"{postid:"+postid+"commentid:"+oldCommentsList.size()+"} ";
                    }
                    String sql5="update `user` set comments="+userNewComments+"where wxid="+wxid;
                    Statement stmt5 = connection.createStatement();
                    stmt3.executeQuery(sql5);

                    json.put("status","COMPLETE");
                    json.put("postid",postid);
                    json.put("commentid",oldCommentsList.size());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }

    public JSONObject createVote(@RequestBody Map<String,String> requestBody){
        JSONObject json=new JSONObject();
        SQLController sqlController=new SQLController();
        Connection connection=sqlController.ConnectToSql("jdbc:mysql://localhost:3306/jinan","root","1234"+"");

        if(connection!=null){
            int postid=Integer.parseInt(requestBody.get("postid"));
            int wxid=Integer.parseInt(requestBody.get("wxid"));
            int vote=Integer.getInteger(requestBody.get("vote"));

            try {
                String sql1 = "select count(*) from `post` where postid=" + postid + "limit 1";
                PreparedStatement ps = connection.prepareStatement(sql1);
                ResultSet Judge = ps.executeQuery();
                Judge.next();
                int ct = Judge.getInt("postid");
                System.out.println(ct);
                if(ct == 0)
                {
                    System.out.println("该帖子不存在");
                    json.put("status","FAIL_NOMATCH");
                }
                else
                {
                    System.out.println("该帖子存在");
                    //给该帖子点赞或点踩:post.like++或post.dislike++
                    int oldLike = 0;
                    int oldDislike=0;
                    String sql2="select * from `post` where postid="+postid;
                    Statement stmt2 = connection.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next()){
                        oldLike= rs2.getInt("like");
                        oldDislike= rs2.getInt("dislike");
                    }
                    String sql3=null;
                    if(vote==1){
                        sql3="update `post` set like="+(oldLike+1)+"where postid="+postid;
                    }else{
                        sql3="update `post` set dislike="+(oldDislike+1)+"where postid="+postid;
                    }
                    Statement stmt3 = connection.createStatement();
                    stmt3.executeQuery(sql3);

                    //给该用户的votes字段插入该postid和vote;形式{postid: 5654, vote: 1}
                    String sql4="select votes from `user` where wxid="+wxid;
                    Statement stmt4=connection.createStatement();
                    ResultSet rs4=stmt4.executeQuery(sql4);
                    String userOldVotes=null;
                    String userNewVotes=null;
                    while (rs4.next()){
                        userOldVotes=rs4.getString("votes");
                        userNewVotes=userOldVotes+"{postid:"+postid+",vote:"+vote+"} ";
                    }
                    String sql5="update `user` set votes="+userNewVotes+"where wxid="+wxid;
                    Statement stmt5 = connection.createStatement();
                    stmt3.executeQuery(sql5);

                    json.put("status","COMPLETE");
                    json.put("postid",postid);
                    json.put("vote",vote);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return json;
    }

}




