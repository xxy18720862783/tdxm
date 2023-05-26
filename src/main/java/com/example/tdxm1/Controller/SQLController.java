package com.example.tdxm1.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLController {
    public Connection ConnectToSql(String url,String user,String password){
        Connection connection=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("注册驱动成功!");
        }catch(ClassNotFoundException e) {
//            System.out.println("注册驱动失败!");
            e.printStackTrace();
        }
        try {
//            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/jinandaxue","root","1234"+"");
            connection=DriverManager.getConnection(url, user, password);
//            System.out.println("成功连接到数据库！");
        }catch (Exception e){
//            System.out.println("连接数据库失败!");
            e.printStackTrace();
        }
        return connection;
    }
}
