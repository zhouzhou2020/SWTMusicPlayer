package com.yc.WCD.serviceImpl;
import java.io.File;

import javax.management.MBeanAttributeInfo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.yc.WCD.dao.daoMusicImpl;
import com.yc.WCD.entity.Song;
import com.yc.WCD.service.MusicService;
import com.yc.WCD.util.SongCompared;
import com.yc.WCD.util.UIUtil;
import com.yc.WCD.view.mainFrame;

public class MusicServiceImpl implements MusicService {
	private boolean b = false;
	@Override
	public void upload(Label label) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Label l = (Label)e.widget;
				if(mainFrame.tableDefaultList.getSelectionCount()>0){
					TableItem ti = mainFrame.tableDefaultList.getSelection()[0];
					String str[] = ti.getText(4).split(":");
					Song song = new Song();
					song.setKey(Integer.parseInt(ti.getText(0)));
					song.setSongName(ti.getText(1));
					song.setArtist(ti.getText(2));
					song.setAlbum(ti.getText(3));
					song.setTotalTime((Integer.parseInt(str[0])*60)+Integer.parseInt(str[1]));
					song.setClickCount(Integer.parseInt(ti.getText(5)));
					System.out.print(ti.getText(6));
					String[] str1 = ti.getText(6).replace("\\", "/").split("/");
					song.setFileName(str1[str1.length-1].split(".mp3")[0]);
					
					MessageBox mb = new MessageBox(l.getShell());
					mb.setText("提示");
					if(new SongCompared().songCompare(song)){
						
						if(new daoMusicImpl().uploadMusic(song, ti.getText(6))!=0){
							mb.setMessage("上传成功！！");
							mb.open();
						}else{
							mb.setMessage("上传失败！！");
							mb.open();
						}
					}else{
						mb.setMessage("歌曲已存在哦");
						mb.open();
					}
				}else{
					MessageBox mb = new MessageBox(l.getShell());
					mb.setText("提示");
					mb.setMessage("您还没有选择歌曲哦");
					mb.open();
				}
			}
		});
	}
	@Override
	public void collection(Label label) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Label l = (Label)e.widget;
				MessageBox mb = new MessageBox(l.getShell());
				mb.setText("提示");
				if(mainFrame.flag_hasUser){
					if(mainFrame.tableDefaultList.getSelectionCount()>0){
						TableItem ti = mainFrame.tableDefaultList.getSelection()[0];
						String str[] = ti.getText(4).split(":");
						Song song = new Song();
						song.setKey(Integer.parseInt(ti.getText(0)));
						song.setSongName(ti.getText(1));
						song.setArtist(ti.getText(2));
						song.setAlbum(ti.getText(3));
						song.setTotalTime((Integer.parseInt(str[0])*60)+Integer.parseInt(str[1]));
						song.setClickCount(Integer.parseInt(ti.getText(5)));
						String[] str1 = ti.getText(6).replace("\\", "/").split("/");
						song.setFileName(str1[str1.length-1].split(".mp3")[0]);
						SongCompared songCompared = new SongCompared();
						if(songCompared.songCompare(song)&&songCompared.songListCompare(song)){
							int i = new daoMusicImpl().uploadMusic(song, ti.getText(6));
							song.setKey(i);
							if(i!=0&&new daoMusicImpl().collectMusic(song)){
								mb.setMessage("收藏成功！！");
								mb.open();
								new TreeItem(mainFrame.treeItem_like, SWT.NONE).setText(song.getArtist()+" - "+song.getSongName());
							}else{
								mb.setMessage("收藏失败！！");
								mb.open();
							}
						}else{
							mb.setMessage("您已收藏该歌曲");
							mb.open();
						}
					}else{
						mb.setMessage("您还没有选中歌曲哦");
						mb.open();
					}
				}else{
					mb.setMessage("用户没有登录哦");
					mb.open();
				}
			}
		});
	}
	@Override
	public void deleteMusic(Label label) {
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Label label = (Label)e.widget;
				MessageBox mb = new MessageBox(label.getShell());
				mb.setText("温馨提示");
				if(mainFrame.tableDefaultList.getSelectionCount()>0){
					TableItem ti = mainFrame.tableDefaultList.getSelection()[0];
					System.out.println(ti.getText(6));
					File file = new File(ti.getText(6));
					file.delete();
					mainFrame.tableDefaultList.removeAll();
					importMusicImpl.i = 1;
					new importMusicImpl().scan(mainFrame.str);
					mb.setMessage("删除成功！！");
					mb.open();
				}else{
					mb.setMessage("您还没有选中歌曲哦");
					mb.open();
				}
			}
		});
	}
	
	//在乐库里收藏歌曲
	public void collection(Button btn) {
		btn.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseDown(MouseEvent e) {
			Button l = (Button)e.widget;
			MessageBox mb = new MessageBox(l.getShell());
			mb.setText("提示");
			if(mainFrame.flag_hasUser){
				if(mainFrame.tableSongLib.getSelectionCount()>0){
					TableItem ti = mainFrame.tableSongLib.getSelection()[0];
					String str[] = ti.getText(4).split(":");
					Song song = new Song();
					song.setKey(Integer.parseInt(ti.getText(0)));
					song.setSongName(ti.getText(1));
					song.setArtist(ti.getText(2));
					song.setAlbum(ti.getText(3));
					song.setTotalTime((Integer.parseInt(str[0])*60)+Integer.parseInt(str[1]));
					song.setClickCount(Integer.parseInt(ti.getText(5)));
					String[] str1 = ti.getText(6).replace("\\", "/").split("/");
					song.setFileName(str1[str1.length-1].split(".mp3")[0]);
					if(new SongCompared().songListCompare(song)){
						if(new daoMusicImpl().collectMusic(song)){
							mb.setMessage("收藏成功！！");
							mb.open();
							new TreeItem(mainFrame.treeItem_like, SWT.NONE).setText(song.getArtist()+" - "+song.getSongName());
						}else{
							mb.setMessage("收藏失败！！");
							mb.open();
						}
					}else{
						mb.setMessage("您已收藏该歌曲");
						mb.open();
					}
				}else{
					mb.setMessage("您还没有选中歌曲哦");
					mb.open();
				}
			}else{
				mb.setMessage("用户没有登录哦");
				mb.open();
			}
		}
	});
}
}
