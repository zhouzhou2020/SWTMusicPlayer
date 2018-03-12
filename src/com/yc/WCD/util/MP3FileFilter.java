package com.yc.WCD.util;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

/**
 * MP3文件过滤器
 * @author JYS
 *
 */
public class MP3FileFilter extends FileFilter {

	public String getDescription() {
		return "*.Mp3(音乐文件)";
	}

	public boolean accept(File file) {
		MP3Info info;
		try {
			String name = file.getName();
			if (name.toLowerCase().endsWith(".mp3")|| !name.toLowerCase().contains(".")) {
				info = new MP3Info(file);
				if(!"".equals(info.getSongName())
						&&!"".equals(info.getArtist())
						&&!"".equals(info.getAlbum())){
					return true;
				}	
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
