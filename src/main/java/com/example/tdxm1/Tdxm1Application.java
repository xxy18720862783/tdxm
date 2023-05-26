package com.example.tdxm1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.tdxm1.Controller.PostController;
import com.example.tdxm1.Controller.SQLController;
import com.example.tdxm1.Controller.UserController;
import com.example.tdxm1.Enity.AdminBean;
import com.example.tdxm1.Enity.HighlightBean;
import com.example.tdxm1.Enity.PostBean;
import com.example.tdxm1.Enity.UserBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Tdxm1Application {
	private ArrayList<UserBean> userBeans=new ArrayList<>();//所有用户
	private ArrayList<PostBean> postBeans=new ArrayList<>();//所有帖子
	private ArrayList<AdminBean> adminBeans=new ArrayList<>();//所有管理员
	private ArrayList<HighlightBean> highlightBeans=new ArrayList<>();//所有精选帖


	public static void main(String[] args) {

		SpringApplication.run(Tdxm1Application.class, args);
		System.out.println("hello world");

		SQLController sqlController=new SQLController();
		UserController userController=new UserController();
		PostController postController=new PostController();

//		String  a="123,456,78,255，55,25,";
//		System.out.println(a);
//		Integer s=90;
//		System.out.println(s);
//		System.out.println(s.toString());
//		String b=a+s.toString()+",";
//		System.out.println(b);
//		List<String> l=Arrays.asList(b.split(","));
//		System.out.println(l);
//		System.out.println(l.contains(90));
//		System.out.println(l.contains("90"));
//		String ls=String.join(",",l)+",";
//		System.out.println(ls);
	}
}
