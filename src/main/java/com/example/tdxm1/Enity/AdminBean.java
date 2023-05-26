package com.example.tdxm1.Enity;

public class AdminBean {
    public int getPosterid() {
        return posterid;
    }

    public void setPosterid(int postid) {
        this.posterid = postid;
    }

    private int posterid;

    public AdminBean() {
    }
    public AdminBean(int posterid) {
        this.posterid = posterid;
    }
}
