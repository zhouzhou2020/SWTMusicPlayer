package com.yc.WCD.dao;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import com.yc.WCD.entity.users;
import com.yc.WCD.util.DBHelper;
import com.yc.WCD.util.Encrypt;
import com.yc.WCD.view.mainFrame;

public class daoUserImpl implements daoUser {

	@Override
	public boolean add(users user) {
		String passwordEncrypt = Encrypt.md5AndSha(user.getPassword());
		String sql = "insert into users values(seq_users.nextval,null,?,?,?,?)";
		return DBHelper.doUpdate(sql, user.getUseName(),passwordEncrypt,user.getSex(),user.getMail())>0;
	}

	
	public Map<String, Object> selectUser(String username, String password) {
		String sql="select * from users where username = ? and password = ?";	
		return DBHelper.doQueryOne(sql, username,password);
	}

	@Override
	public boolean updatePic(InputStream in) {
		String sql = "update users set photo = ? where userid = ?";
		return DBHelper.doUpdate(sql, in,mainFrame.user.getUserId())>0;
	}

	@Override
	public boolean updateInfo(String name, String password,String mail) {
		String sql = "update users set username = ? ,password = ? ,mail = ? where userid = ?";
		return DBHelper.doUpdate(sql, name,password,mail,mainFrame.user.getUserId())>0;
	}


	@Override
	public int resetPassword(String mail, String username, String password) {
		String sql = "update users set password = ? where mail = ? and username = ?";
		return DBHelper.doUpdate(sql, password,mail,username);
	}

}
