package com.example.tdxm1.Enity;

public class CommentBean {
    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private int commentid;//commentid直接用该comment在数组中的下标
    private int poster;
    private String content;

    public CommentBean() {
    }

}
