package com.yc.WCD.dao;

import java.util.List;
import java.util.Map;

import com.yc.WCD.entity.Song;

public interface daoMusic {
	public int addToDefault(int userid,String songPath);//添加到歌曲默认列表
	public int uploadMusic(Song song,String musicPath);
	List<Map<String, Object>> findAllSong();
	List<Map<String, Object>> findByName(String songName);
	List<Map<String, Object>> findByArtist(String artist);
	public boolean collectMusic(Song song);
	public List<Map<String, Object>> importMylike();
	Map<String, Object> searchMusic(int songid);
}
