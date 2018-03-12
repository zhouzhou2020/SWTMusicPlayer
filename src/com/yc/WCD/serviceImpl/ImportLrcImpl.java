package com.yc.WCD.serviceImpl;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;

import com.yc.WCD.service.ImportLrc;
import com.yc.WCD.util.LrcFileFilter;
import com.yc.WCD.util.MP3FileFilter;
import com.yc.WCD.view.LyricUpload;
import com.yc.WCD.view.mainFrame;

public class ImportLrcImpl implements ImportLrc {
	public static int i = 1;
	@Override
	public void ImportLrcToTable() {
		DirectoryDialog dd = new DirectoryDialog(LyricUpload.shell,SWT.OPEN);
		MessageBox mb = new MessageBox(LyricUpload.shell);
		mb.setText("提示");
		dd.setText("添加歌词");
		String path = dd.open();
		if(path!=null){
			 scan(path);
		}
	}
	
	public void scan(String filePath){
		File file = new File(filePath);
		File[] files = file.listFiles();
		boolean flag = false;
		if(files!=null){
			for(File f:files){
				if(new LrcFileFilter().accept(f)){
					importLrc(f.getName(),f.getPath());
					flag = true;
				}
			}
		}
		if(!flag){
			MessageBox mb = new MessageBox(LyricUpload.shell);
			mb.setText("温馨提示！");
			mb.setMessage("该文件夹中没有标准的歌词文件！");
			mb.open();
		}
	}
	
	public void importLrc(String fileName,String LrcPath){
		TableItem ti =new TableItem(LyricUpload.tableUpload, 0);
		ti.setText(0,i+"");
		ti.setText(1, fileName);
		ti.setText(2, LrcPath);	
		i++;
	}

}
