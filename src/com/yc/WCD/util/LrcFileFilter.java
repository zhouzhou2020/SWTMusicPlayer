package com.yc.WCD.util;

import java.io.File;
import java.io.IOException;

import com.lrc.io.ReadLRC;
import com.lrc.lrc.LRC;

public class LrcFileFilter {
	public boolean accept(File file) {	
		String name =file.getName();
		if (name.toLowerCase().endsWith(".lrc")){
			LRC lrc = ReadLRC.readLRC(file);
			if(lrc!=null) return true;
			} else {
				return false;
		}
		return false;
	}
}
