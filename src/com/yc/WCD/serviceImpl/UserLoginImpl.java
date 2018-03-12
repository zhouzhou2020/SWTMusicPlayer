package com.yc.WCD.serviceImpl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import com.yc.WCD.dao.daoUserImpl;
import com.yc.WCD.entity.users;
import com.yc.WCD.service.UserLogin;
import com.yc.WCD.util.Encrypt;

public class UserLoginImpl implements UserLogin {

	@Override
	public users login(String username, String password) {
		String passwordEncrypt = Encrypt.md5AndSha(password);
		Map<String, Object> loginUser = new daoUserImpl().selectUser(username, passwordEncrypt);
		if(loginUser!=null){
			users user = new users();
			user.setUserId(((BigDecimal)loginUser.get("userid")).intValue());
			user.setUseName(username);
			user.setPassword(passwordEncrypt);
			user.setSex((String)loginUser.get("sex"));
			user.setPhoto((InputStream)loginUser.get("photo"));
			user.setMail((String)loginUser.get("mail"));
	        return user;
		}
		return null;
	}

}
