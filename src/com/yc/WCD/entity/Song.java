package com.yc.WCD.entity;

import java.io.Serializable;

public class Song implements Serializable{
	private int key;//歌曲序号
	private String songName;// 歌曲名称
	private String artist ; //歌手
	private String album;//专辑
	private int totalTime; //歌曲时长
	private int clickCount; //点击次数
	private String fileName;//文件名
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "Song [key=" + key + ", songName=" + songName + ", artist="
				+ artist + ", album=" + album + ", totalTime=" + totalTime
				+ ", clickCount=" + clickCount + ", fileName=" + fileName + "]";
	}
	public Song() {
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
}
