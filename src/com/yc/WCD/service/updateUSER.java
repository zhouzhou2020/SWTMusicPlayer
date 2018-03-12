package com.yc.WCD.service;

import org.eclipse.swt.widgets.Label;

public interface updateUSER {
	public void updateUserPic(Label label);//修改头像
	public boolean updateUserInfo(String name,String password,String mail);
}
