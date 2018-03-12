package com.yc.WCD.dao;

import java.io.InputStream;
import java.util.Map;

import com.yc.WCD.entity.users;


public interface daoUser {
	public boolean add(users user);//添加用户
	public Map<String, Object> selectUser(String name,String password);//验证登录
	public boolean updatePic(InputStream in);//更换图片
	public boolean updateInfo(String name,String password,String mail);//修改用户信息
	int resetPassword(String qq, String name, String password);//重置密码
}
