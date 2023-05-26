package com.example.tdxm1.Enity;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.List;

public class UserBean {
    //构造函数
    public UserBean(int userid, int wxid, int campus, String username, int gneder, String desc, String birthday) {
        this.userid = userid;
        this.wxid=wxid;
        this.gneder = gneder;
        this.birthday=birthday;
        this.campus=campus;
        this.desc=desc;
        this.username=username;
    }

    //成员变量
    private int userid;
    private int wxid;
    private String username;
    private int gneder;
    private int campus;
    private String desc;
    private String birthday;

    private List<Integer> fav;//用户收藏的帖子的postid组成的数组
    private List<Integer> pubc;//用户发布的帖子的postid组成的数组
    private List<String> comments;//用户发布的评论列表,每个元素形式为：{postid:1246,commentid:15428}
    private List<String> votes;//用户的点赞点踩列表，每个元素形式为：{postid:1234,vote:1}

    public int getId() {
        return userid;
    }

    public void setId(int userid) {
        this.userid = userid;
    }

    public int getWxid() {
        return wxid;
    }

    public void setWxid(int wxid) {
        this.wxid = wxid;
    }
    public int getGneder() {return gneder;}

    public void setGneder(int gneder) {this.gneder = gneder;}

    public int getCampus() {
        return campus;
    }

    public void setCampus(int campus) {
        this.campus = campus;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
