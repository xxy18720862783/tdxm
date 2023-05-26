package com.example.tdxm1;

import com.alibaba.fastjson.JSONObject;
import com.example.tdxm1.Controller.SQLController;
import com.example.tdxm1.Controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

@SpringBootTest
class Tdxm1ApplicationTests {
	@Test
	void contextLoads() {
		System.out.println("单元测试");
	}
	@Test
	void SqlConnect(){
		SQLController sqlController=new SQLController();
		Connection connection=sqlController.ConnectToSql("jdbc:mysql://localhost:3306/jinandaxue","root","1234"+"");
		if(connection!=null){
			System.out.println("成功连接到数据库！");
		}else {
			System.out.println("连接数据库失败！");
		}
	}
	@Test
	void testGetUser(){
		UserController userController=new UserController();
		JSONObject js1=new JSONObject();
		js1.put("wxid","123");
		System.out.println(userController.getUser(js1));
		System.out.println("getUser success!");
	}
	@Test
	void testUpdateUser(){
		UserController userController=new UserController();
		JSONObject js2=new JSONObject();
		js2.put("wxid","123");
		js2.put("username","lili");
		js2.put("desc","dddd");
		js2.put("gender","1");
		js2.put("campus","1");
		js2.put("birthday","20200104");
		System.out.println(userController.getUser(js2));
		System.out.println("updateUser success!");
	}
}
