package com.yc.WCD.util;

import java.awt.Composite;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.WCD.serviceImpl.UserLoginImpl;
import com.yc.WCD.view.mainFrame;
import com.yc.WCD.view.register;

public class UIUtil {
	private static int beginX;
	private static int beginY;
	private static boolean isPress;//是否按下
	
	
	//按钮添加动态图片
	public static void lblClick(final Label lbl,final String normal,final String highlight,final String down){
		//添加事件
		lbl.addMouseTrackListener(new MouseTrackAdapter() {
					//放上
					public void mouseHover(MouseEvent e){
						Label b = (Label) e.widget;
						b.setImage(changeImage(highlight, lbl.getBounds().width, lbl.getBounds().height));
					}
					//移开
					public void mouseExit(MouseEvent e){
						Label b = (Label) e.widget;
						b.setImage(changeImage(normal, lbl.getBounds().width, lbl.getBounds().height));
					}
				});
		lbl.addMouseListener(new MouseAdapter() {
					//按下
					public void mouseDown(MouseEvent e){
						Label b = (Label) e.widget;
						b.setImage(changeImage(down, lbl.getBounds().width, lbl.getBounds().height));
					}
					//弹起
					public void mouseUp(MouseEvent e){
						Label b = (Label) e.widget;
						Rectangle r = b.getBounds();
						if(e.x>0 && e.y>0 && e.x< r.width && e.y<r.height){
							MessageBox mb=new MessageBox(lbl.getShell(),SWT.NONE);
							mb.setText("提示");
							mb.setMessage("点了一下");
							mb.open();
						}
					}
				});
	}
	
	/**
	 * 图片自适应
	 * @param imageClassPath 图片的路径
	 * @param width  改变成的宽度
	 * @param height 改变成的高度
	 * @return 改变后的图片
	 */
	public static Image changeImage(String imageClassPath, int width, int height) {
		//判断文件的路径是相对还是绝对路径
		//window的绝对路径是：以"盘符号"开始, linux 的绝对路径是：以"/"开始
		//imageClassPath.charAt(0) == '/' || imageClassPath.charAt(1) == ':'  //判断是否绝对路径
		try {
			InputStream in  = (imageClassPath.charAt(0) == '/' || imageClassPath.charAt(1) == ':') ? new FileInputStream(imageClassPath) :
				mainFrame.class.getClassLoader().getResourceAsStream(imageClassPath);
			return changeImage(in, width, height);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 图片自适应
	 * @param in 图片数据流
	 * @param width  改变成的宽度
	 * @param height 改变成的高度
	 * @return 改变后的图片
	 */
	public static Image changeImage(InputStream in, int width, int height) {
		ImageData idata = new ImageData(in).scaledTo(width, height);
		Image image = new Image(null, idata);
		return image;
	}
	
	/**
	 * 窗口居中
	 * @param shell 窗口对象
	 */
	public static void winCenter(Shell shell){
		Rectangle r = Display.getDefault().getBounds();
		Rectangle winR = shell.getBounds();
		shell.setLocation((r.width-winR.width)/2,(r.height-winR.height)/2);
	}
	
	/**
	 * 添加关闭按钮的一系列事件
	 * @param lblClose 添加事件的标题组件
		 */
		public static void addCloseEvent(final Label lblClose){
		//添加事件
				lblClose.addMouseTrackListener(new MouseTrackAdapter() {
					//放上
					public void mouseHover(MouseEvent e){
						Label l = (Label) e.widget;
						l.setImage(changeImage("images/btn_close_highlight.png", lblClose.getBounds().width, lblClose.getBounds().height));
					}
					//移开
					public void mouseExit(MouseEvent e){
						Label l = (Label) e.widget;
						l.setImage(changeImage("images/btn_close_normal.png", lblClose.getBounds().width, lblClose.getBounds().height));
					}
				});
				lblClose.addMouseListener(new MouseAdapter() {
					//按下
					public void mouseDown(MouseEvent e){
						Label l = (Label) e.widget;
						l.setImage(changeImage("images/btn_close_down.png", lblClose.getBounds().width, lblClose.getBounds().height));
					}
					//弹起
					public void mouseUp(MouseEvent e){
						Label l = (Label) e.widget;
						Rectangle r = l.getBounds();
						if(e.x>0 && e.y>0 && e.x< r.width && e.y<r.height){
							lblClose.getShell().dispose();
						}
					}
				});
	}
	
	public static void moveWin(final Shell shell){
			shell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(isPress){
					int moveX = e.x-beginX;
					int moveY = e.y-beginY;
					shell.setLocation(shell.getBounds().x+moveX,shell.getBounds().y+moveY);
				}
				
			}
		});
		shell.addMouseListener(new MouseAdapter() {
			//按下
			public void mouseDown(MouseEvent e){
				isPress = true;
				beginX = e.x;
				beginY = e.y;
			}
			//弹起
			public void mouseUp(MouseEvent e){
				isPress = false;
			;
			}
		});
	}
	
	
	//界面最小化
	public static void addMinEvent(Label lb_min,final Shell shell) {
		lb_min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Label l=(Label)e.widget;
				l.setImage(UIUtil.changeImage("images/btn_mini_down.png", l.getBounds().width, l.getBounds().height));
			}
			@Override
			public void mouseUp(MouseEvent e) {
						shell.setMinimized(true);
					}
		});
		lb_min.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent e) {
				Label l=(Label)e.widget;
				l.setImage(UIUtil.changeImage("images/btn_mini_normal.png", l.getBounds().width, l.getBounds().height));
			}
			@Override
			public void mouseHover(MouseEvent e) {
				Label l=(Label)e.widget;
				l.setImage(UIUtil.changeImage("images/btn_mini_highlight.png", l.getBounds().width, l.getBounds().height));
			}
		});
	  }
	
	//按钮关闭
	public static void addButtonClose(final Shell shell,Button btn_exit) {
		btn_exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				closeDisplay(shell);
			}
		});		
	}
	public static void closeDisplay(Shell shell)
	{
		shell.dispose();
	}

	//设置提示信息关闭
	public static void textSetEvent(Text text,final Shell shell,final String str)
	{
		text.setForeground(new Color(shell.getDisplay(),200,200,200));
		text.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent fe) {
			Text text=(Text)fe.widget;
			if(str.equals(text.getText().trim()))
			text.setText("");
			text.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
			text.setForeground(new Color(shell.getDisplay(),200,200,200));
			}
			@Override
			public void focusLost(FocusEvent fe) {
				Text text=(Text)fe.widget;
				if("".equals(text.getText().trim()))
				text.setText(str);
				text.setForeground(new Color(shell.getDisplay(),200,200,200));
				}		
		});
	}
}