package com.yc.WCD.serviceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;

import com.yc.WCD.dao.daoUserImpl;
import com.yc.WCD.service.updateUSER;
import com.yc.WCD.util.Encrypt;
import com.yc.WCD.util.UIUtil;
import com.yc.WCD.view.mainFrame;

public class updateUSERImpl implements updateUSER {
	public static String picPath;//图片的存放路径
	@Override
	public void updateUserPic(Label label) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Label lbl = (Label)e.widget;
				FileDialog fd = new FileDialog(lbl.getShell(),SWT.OPEN);
				fd.setText("图像");
				fd.setFilterExtensions(new String[]{"*.png","*.gif","*.jpg"});
				fd.setFilterNames(new String[]{"*.png","*.gif","*.jpg"});
				picPath = fd.open();
				if(picPath!=null){
					try {
						lbl.setImage(UIUtil.changeImage(picPath, 75, 45));
						new daoUserImpl().updatePic(new FileInputStream(picPath));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

	}
	@Override
	public boolean updateUserInfo(String name, String password,String mail) {
		String passwordEncrypy = Encrypt.md5AndSha(password);
		return new daoUserImpl().updateInfo(name, passwordEncrypy,mail);
	}

}
