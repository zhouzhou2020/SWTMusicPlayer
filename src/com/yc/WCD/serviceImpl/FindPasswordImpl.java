package com.yc.WCD.serviceImpl;

import java.util.Map;

import com.yc.WCD.dao.daoUser;
import com.yc.WCD.dao.daoUserImpl;
import com.yc.WCD.service.FindPassword;
import com.yc.WCD.util.Encrypt;

public class FindPasswordImpl implements FindPassword {

	@Override
	public boolean resetPassword(String qq,String name,String randomPassword) {
		System.out.println("***************"+randomPassword);
		String password = Encrypt.md5AndSha(randomPassword);
		return new daoUserImpl().resetPassword(qq,name,password)>0;
	}
	@Override
	public boolean sendMailVaildate(String name, String qq) {
		
		Map<String, Object> result = new daoUserImpl().selectUser(name, qq);
		if(result == null){
			return false;
		}
		return result.size()>0;
	}
}
