package com.yc.WCD.service;

import org.eclipse.swt.widgets.Label;

import com.yc.WCD.entity.MP3Player;

public interface importMusic {
	public void Myimport(Label label);
	public void scan(String string);
	public void getMusic();
	public void importMylike();//加载到我喜欢
	public void recDownload();
	public void importFile(Label label);
}
