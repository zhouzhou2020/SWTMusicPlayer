package com.yc.WCD.serviceImpl;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

import com.yc.WCD.dao.daoUserImpl;

public class updateUSERImplTest {

	@Test
	public void testUpdateUserPic() {
		try {
			new daoUserImpl().updatePic(new FileInputStream("images/add.gif"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
