package com.yc.WCD.serviceImpl;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;

import com.yc.WCD.dao.daoLrcImpl;
import com.yc.WCD.service.LrcService;
import com.yc.WCD.view.LyricUpload;
import com.yc.WCD.view.mainFrame;

public class LrcServiceImpl implements LrcService {

	@Override
	public void upload() {
		if(LyricUpload.tableUpload.getSelectionCount()>0){
			TableItem ti =LyricUpload.tableUpload.getSelection()[0];
			String[] str = ti.getText(1).split(".lrc");
			String musicName = str[0];
			String path = ti.getText(2);
			MessageBox mb = new MessageBox(LyricUpload.shell);
			mb.setText("温馨提示！");
			if(new daoLrcImpl().uploadLrc(musicName, path)){
				mb.setMessage("上传成功！");
				mb.open();
			}else{
				mb.setMessage("歌词已存在！！！");
				mb.open();
			}		
		}
	}

}
