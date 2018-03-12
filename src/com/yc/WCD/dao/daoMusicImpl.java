package com.yc.WCD.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yc.WCD.entity.Song;
import com.yc.WCD.util.DBHelper;
import com.yc.WCD.view.mainFrame;

public class daoMusicImpl implements daoMusic {

	@Override
	public int addToDefault(int userid, String songPath) {
		String sql1 = "insert into defaultPlayList values(?,seq_songs.nextval,?)";
		DBHelper.doUpdate(sql1, userid,songPath);
		String sql2 = "select * from defaultPlayList where songpath = ?";
		Map<String, Object> map = DBHelper.doQueryOne(sql2, songPath);
		return ((BigDecimal)map.get("songid")).intValue();
	}

	@Override
	public Map<String, Object> searchMusic(int songid) {
		String sql = "select songname,artist,music,filename from song where songid = ?";
		return DBHelper.doQueryOne(sql, songid);
	}

	@Override
	public int uploadMusic(Song song, String musicPath) {
		String sql = "insert into song values(seq_song.nextval,?,?,?,?,?,?,?)";
		try {
			DBHelper.doUpdate(sql, song.getSongName(),song.getArtist(),song.getAlbum()
					,song.getTotalTime(),new FileInputStream(musicPath),song.getClickCount(),song.getFileName());
			
			String sqlString = "select * from song where songname = ?";
			Map<String, Object> map = DBHelper.doQueryOne(sqlString, song.getSongName());
			return ((BigDecimal)map.get("songid")).intValue();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> findAllSong() {
		String sql = "select * from song order by clickCount desc";
		return DBHelper.doQuery(sql, null);
	}

	@Override
	public List<Map<String, Object>> findByName(String songName) {
		String sql = "select * from song where songName =? order by clickCount desc";
		return DBHelper.doQuery(sql, songName);
	}

	@Override
	public List<Map<String, Object>> findByArtist(String artist) {
		String sql = "select * from song where artist = ? order by clickCount desc";
		return DBHelper.doQuery(sql, artist);
	}

	@Override
	public boolean collectMusic(Song song) {
		String sql = "insert into songList values(?,?)";
		return DBHelper.doUpdate(sql, mainFrame.user.getUserId(),song.getKey())>0;
	}

	@Override
	public List<Map<String, Object>> importMylike() {
		String sql = "select * from song join songList on song.songid = songList.songid where userid = ?";
		return DBHelper.doQuery(sql, mainFrame.user.getUserId());
	}

}
