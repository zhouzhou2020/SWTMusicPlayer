package com.yc.WCD.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.yc.WCD.util.DBHelper;

public class daoLrcImpl implements daoLrc {

	@Override
	public boolean uploadLrc(String musicName, String lrcPath) {  //去掉后缀名
		String sql = "insert into lyric values(?,?)";
		try {
			return DBHelper.doUpdate(sql, musicName,new FileInputStream(lrcPath))>0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

}
