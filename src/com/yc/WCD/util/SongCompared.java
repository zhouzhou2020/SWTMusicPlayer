package com.yc.WCD.util;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import oracle.net.aso.b;

import org.eclipse.swt.widgets.MessageBox;

import com.yc.WCD.entity.Song;
import com.yc.WCD.view.mainFrame;

public class SongCompared {
	public boolean songCompare(Song song){
		String str = "select * from song";
		List<Map<String, Object>> list = DBHelper.doQuery(str);
		for(Map<String, Object> map:list){
			if(song.getSongName().equals(map.get("songname"))&&
			   song.getArtist().equals(map.get("artist"))&&
			   song.getAlbum().equals(map.get("album"))&&
			   song.getTotalTime()==Integer.parseInt(map.get("totaltime").toString())){
				return false;
			}
		}
		return true;
	}
	public boolean songListCompare(Song song){
		String str = "select * from SongList";
		List<Map<String, Object>> list = DBHelper.doQuery(str);
		for(Map<String, Object> map:list){
			if(mainFrame.user.getUserId()==Integer.parseInt(map.get("userid").toString())&&
					song.getKey()==Integer.parseInt(map.get("songid").toString())){
				return false;
			}
		}
		return true;
	}
}
