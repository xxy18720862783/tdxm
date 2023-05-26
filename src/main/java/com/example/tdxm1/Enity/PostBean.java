package com.example.tdxm1.Enity;

import java.util.ArrayList;
import java.util.List;

public class PostBean {
    public PostBean() {
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getPosterid() {
        return posterid;
    }

    public void setPosterid(int posterid) {
        this.posterid = posterid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public double getOldprice() {
        return oldprice;
    }

    public void setOldprice(double oldprice) {
        this.oldprice = oldprice;
    }

    public double getNewprice() {
        return newprice;
    }

    public void setNewprice(double newprice) {
        this.newprice = newprice;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    private int postid;
    private int posterid;
    private String name;
    private int location;
    private double oldprice;
    private double newprice;
    private int like;//点赞数
    private int dislike;//点踩数
    private ArrayList<String> comments;//每个元素形式：{commentid:123459,poster:1234,content:'哒哒哒哒'}
}
