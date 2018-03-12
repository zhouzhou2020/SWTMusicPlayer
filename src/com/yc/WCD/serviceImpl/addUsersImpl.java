package com.yc.WCD.serviceImpl;

import com.yc.WCD.dao.daoUserImpl;
import com.yc.WCD.entity.users;
import com.yc.WCD.service.addUsers;

public class addUsersImpl implements addUsers {

	public boolean add(users user) {
		if(new daoUserImpl().add(user)){
			return true;
		}
		return false;
	}

}
