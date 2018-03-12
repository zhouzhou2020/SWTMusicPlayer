package com.yc.WCD.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.yc.WCD.dao.daoMusicImpl;
import com.yc.WCD.service.ExportMusic;
import com.yc.WCD.util.MP3FileFilter;
import com.yc.WCD.view.mainFrame;

public class ExportMusicImpl implements ExportMusic {

	@Override
	public void exportMP3() {
		List<Map<String, Object>> list = new daoMusicImpl().findAllSong();
		boolean flag = false;
		for (Map<String, Object> m : list) {
			flag = true;
			setTableItem(m);
		}
		if (!flag) {
			MessageBox mb = new MessageBox(mainFrame.shell);
			mb.setText("温馨提示！");
			mb.setMessage("亲！乐库中还没有歌曲，去听听本地音乐吧！");
			mb.open();
		}
	}

	@Override
	public void searchSong(String content) {
		List<Map<String, Object>> list = new daoMusicImpl()
				.findByArtist(content);
		list.addAll(new daoMusicImpl().findByName(content));
		boolean flag = false;
		for (Map<String, Object> m : list) {
			flag = true;
			setTableItem(m);
		}
		if (!flag) {
			MessageBox mb = new MessageBox(mainFrame.shell);
			mb.setText("温馨提示！");
			mb.setMessage("亲！未搜索到结果哦！换个关键词试试吧！");
			mb.open();
		}
	}

	public void setTableItem(Map<String, Object> m) {
		TableItem ti = new TableItem(mainFrame.tableSongLib, 0);
		ti.setText(0, ((BigDecimal) m.get("songid")).toString());
		ti.setText(1, (String) m.get("songname"));
		ti.setText(2, (String) m.get("artist"));
		ti.setText(3, (String) m.get("album"));
		ti.setText(4, String.format("%s:%s",
				Integer.valueOf(m.get("totaltime").toString()) / 60,
				Integer.valueOf(m.get("totaltime").toString()) % 60));
		ti.setText(5, m.get("clickcount").toString());
	}
	
	/**
	 * 下载歌曲，前提条件选中乐库中的某一首歌，并将其ID作为该方法的参数
	 */
	@Override
	public void download(int songid) {  
		//在本地创建下载文件夹
		File f1 = new File("src/downloadPath");
		String str = ""; //存放路径
		try {
			FileReader in = new FileReader(f1);
			char[] cs = new char[1024];// 存储数据的数组
			int len = 0;// 读取到的字符的个数
			while ((len = in.read(cs)) != -1) {
				str += new String(cs, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File file = new File(str);
		if (!file.exists()) {
			file.mkdirs(); // 在本地创建一个下载文件夹，将歌曲下载至此处
		}
		//创建文件名
		Map<String, Object> m = new daoMusicImpl().searchMusic(songid);
		System.out.print(m.get("filename"));
		File mp3file = new File(file.getAbsolutePath() + "/"
				+m.get("filename")+".mp3");
		
		if (mp3file.exists()) {
			MessageBox mb = new MessageBox(mainFrame.shell);
			mb.setText("温馨提示！");
			mb.setMessage("已下载！");
			mb.open();
		} else {
			try {
				mp3file.createNewFile();
				InputStream in = (InputStream) m.get("music");
				// 将数据库中的歌曲写入到文件中
				OutputStream fos = new FileOutputStream(mp3file);
				byte[] bs =new byte[4096];
				int len =0;
				while((len=in.read(bs))!=-1){
					fos.write(bs,0,len);
				}
				fos.flush();
				fos.close();
				in.close();
				
				//将下载歌曲的记录添加至最近下载列表
				File[] files = file.listFiles();
				if(files!=null){
					for(File f:files){
						new TreeItem(mainFrame.treeItem_recent,SWT.NONE).setText(f.getName());	//可通过歌曲文件名来查找歌！！！				
					}
				}				
				//提示下载完成
				MessageBox mb = new MessageBox(mainFrame.shell);
				mb.setText("温馨提示！");
				mb.setMessage("下载完成！");
				mb.open();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
