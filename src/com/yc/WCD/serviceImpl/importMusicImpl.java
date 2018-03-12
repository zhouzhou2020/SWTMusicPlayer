package com.yc.WCD.serviceImpl;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream.Filter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.table.TableColumn;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.yc.WCD.dao.daoMusicImpl;
import com.yc.WCD.entity.MP3Player;
import com.yc.WCD.entity.Song;
import com.yc.WCD.service.importMusic;
import com.yc.WCD.util.MP3FileFilter;
import com.yc.WCD.util.MP3Info;
import com.yc.WCD.view.mainFrame;

public class importMusicImpl implements importMusic {
	public static int i = 1;
	private static String musicPath;
	private static Song song;
	public static TableItem tiSeleItem;
	public void importFile(Label label){
		//导入歌曲事件
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						Label l = (Label)e.widget;
						DirectoryDialog dd = new DirectoryDialog(mainFrame.shell,SWT.OPEN);
						dd.setText("添加文件");
						String string = dd.open();
						scan(string);
					}
				});

	}
	@Override
	public void Myimport(Label label) {
		//导入歌曲事件
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				FileDialog fd = new FileDialog(mainFrame.shell,SWT.OPEN);
				MessageBox mb = new MessageBox(mainFrame.shell);
				mb.setText("提示");
				fd.setText("添加歌曲");
				fd.setFilterExtensions(new String[]{"*.mp3","*.lrc"});
				fd.setFilterNames(new String[]{"*.mp3","*.lrc"});
				musicPath = fd.open();
				System.out.println(musicPath);
				
				if(musicPath!=null){
					String[] str = musicPath.split("mp3");
					System.out.println(str[0]);
					String lrcPath = str[0]+"lrc";
					File file_copyed = new File(musicPath);
					File file_lrc = new File(lrcPath);
					if(new MP3FileFilter().accept(file_copyed)){
						File file_copy = new File("E:\\Music\\SWTMusic\\"+file_copyed.getName());
						File file_lrc1 = new File("E:\\Music\\SWTMusic\\"+file_lrc.getName());
						if(!file_copy.exists()){
							try {
								InputStream in = new FileInputStream(file_copyed);
								OutputStream out = new FileOutputStream(file_copy);
								 byte[] by = new byte[4096];
							        int n = 0;
							        while(-1!=(n=in.read(by))){
							                out.write(by, 0, n);
							        }
							        in.close();
							        out.close();
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if(!file_lrc1.exists()&&file_lrc.exists()){
								try {
									InputStream in1 = new FileInputStream(file_lrc);
									OutputStream out1 = new FileOutputStream(file_lrc1);
									 byte[] by = new byte[4096];
								        int n = 0;
								        while(-1!=(n=in1.read(by))){
								        	out1.write(by, 0, n);
								        }
								        in1.close();
								        out1.close();
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
							importMP3(file_copy.getAbsolutePath());
							mb.setMessage("添加成功！");
							mb.open();
						}else{
							mb.setMessage("歌曲存在！！");
							mb.open();
						}
					}else{
						mb.setMessage("歌曲格式不对");
						mb.open();
					}
				}
			}
		});

	}
	//自动扫描文件夹中的mp3文件
	public void scan(String string){
		if(!string.equals("")){
			File file = new File(string);
			if(!file.exists()){
					file.mkdirs();
			}
			File[] files = file.listFiles();
			if(files!=null){
				for(File f:files){
					if(new MP3FileFilter().accept(f)){
						importMP3(f.getPath());
					}
				}
			}
		}
	}
	
	//显示最近下载
		public void recDownload(){
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
			//将下载歌曲的记录添加至最近下载列表
			File[] files = file.listFiles();
			if(files!=null){
				for(File f:files){
					new TreeItem(mainFrame.treeItem_recent,SWT.NONE).setText(f.getName());				
				}
			}		
		}

	
	//MP3文件导入表格
	public void importMP3(String musicPath){
		File MP3FILE = new File(musicPath);
		try {
			//根据歌曲本地路径解析歌曲信息
			MP3Info info = new MP3Info(MP3FILE);
			info.setCharset("GBK");
			song = new Song();
			song.setSongName(info.getSongName());
			song.setArtist(info.getArtist());
			song.setAlbum(info.getAlbum());
			song.setTotalTime(info.getMp3TrackLength(MP3FILE));
			song.setClickCount(0);
			song.setFileName(MP3FILE.getName().split(".mp3")[0]);
			System.out.println(song.toString());
			
			///添加到表格
			TableItem ti = new TableItem(mainFrame.tableDefaultList,0);
			ti.setText(0,i+"");
			ti.setText(1,song.getSongName());
			ti.setText(2,song.getArtist());
			ti.setText(3,song.getAlbum());
			ti.setText(4,String.format("%s:%s",song.getTotalTime()/60,song.getTotalTime()%60>9?song.getTotalTime()%60:"0"+song.getTotalTime()%60));
			ti.setText(5,song.getClickCount()+"");
			ti.setText(6,musicPath);
			i++;
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void getMusic() {
		if(mainFrame.tableDefaultList.getSelectionCount()>0){
			tiSeleItem = mainFrame.tableDefaultList.getSelection()[0];
		}else{
			tiSeleItem = null;
		}
		
		if(tiSeleItem!=null){
			mainFrame.mp3 = new MP3Player(tiSeleItem.getText(6));
		}
	}
	@Override
	public void importMylike() {
		List<Map<String, Object>> list = new daoMusicImpl().importMylike();
		for(Map<String, Object> map : list){
			new TreeItem(mainFrame.treeItem_like, SWT.NONE).setText(map.get("artist")+" - "+map.get("songname"));
		}
	}
}
