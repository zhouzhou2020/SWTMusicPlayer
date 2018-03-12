package com.yc.WCD.view;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;

import com.yc.WCD.entity.users;
import com.yc.WCD.serviceImpl.addUsersImpl;
import com.yc.WCD.util.UIUtil;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.layout.FillLayout;

public class register {

	protected Shell shell;
	private Text text_name;
	private Text text_password;
	private Text text_passwordre;
	private Text text_mail;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			register window = new register();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NONE);
		shell.setSize(330, 420);
		shell.setText("WCD MusicPlay");
		shell.setLocation(400,200);
		
		
		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		sashForm.setSize(330, 420);
		sashForm.setLocation(0, 0);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		
		Label label = new Label(composite, SWT.NONE);
		label.setImage(UIUtil.changeImage("images/btn_close_normal.png", 37, 29));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label.setBounds(289, 0, 39, 32);
		UIUtil.addCloseEvent(label);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_1.setBounds(10, 10, 69, 22);
		label_1.setText("快速注册");
		
		Label lbl_min = new Label(composite, SWT.NONE);
		lbl_min.setImage(UIUtil.changeImage("images/btn_mini_normal.png", 37, 29));
		lbl_min.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_min.setBounds(250, 0, 37, 29);
		UIUtil.addMinEvent(lbl_min, lbl_min.getShell());
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(composite_1, SWT.LEFT);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("用户登入注册");
		
		Group group = new Group(tabFolder, SWT.NONE);
		tabItem.setControl(group);
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(41, 27, 61, 17);
		label_2.setText("昵称：");
		
		text_name = new Text(group, SWT.BORDER);
		text_name.setBounds(126, 21, 164, 23);
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(41, 64, 61, 17);
		label_3.setText("密码：");
		
		text_password = new Text(group, SWT.BORDER | SWT.PASSWORD);
		text_password.setBounds(126, 61, 164, 23);
		
		Label label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(41, 104, 61, 17);
		label_4.setText("重复密码：");
		
		text_passwordre = new Text(group, SWT.BORDER | SWT.PASSWORD);
		text_passwordre.setBounds(126, 101, 164, 23);
		
		final Button button_man = new Button(group, SWT.RADIO);
		button_man.setSelection(true);
		button_man.setBounds(126, 192, 46, 17);
		button_man.setText("男");
		
		Button button_women = new Button(group, SWT.RADIO);
		button_women.setText("女");
		button_women.setBounds(216, 192, 46, 17);
		
		final Button button_2 = new Button(group, SWT.CHECK);
		button_2.setBounds(94, 235, 106, 17);
		button_2.setText("我已阅读并接受");
		
		final Button btnRegister = new Button(group, SWT.NONE);
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = new MessageBox(btnRegister.getShell(),SWT.NONE);
				mb.setText("提示信息");
				if(button_2.getSelection()){
					if(text_password.getText().equals(text_passwordre.getText())
							||text_name.getText().equals("")||text_mail.getText().equals("")){
						users user = new users();
						if(isRegex(text_mail.getText())){
							user.setUseName(text_name.getText());
							user.setPassword(text_password.getText());
							user.setSex(button_man.getSelection()?"男":"女");
							user.setMail(text_mail.getText());
							
							if(new addUsersImpl().add(user)){
								mb.setMessage("注册成功！！！");
								mb.open();
								button_2.getShell().dispose();
							}else{
								mb.setMessage("注册失败！！！");
								mb.open();
								
							}
						}else{
							mb.setMessage("请检查邮箱格式！！");
							mb.open();
						}
					
					}else{
						mb.setMessage("密码不一样或昵称为空！！请重新输入！！");
						mb.open();
					}
				}else{
					mb.setMessage("请阅读并接受协议！！");
					mb.open();
				}
			}
		});
		btnRegister.setImage(SWTResourceManager.getImage(register.class, "/images/register_down.png"));
		btnRegister.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btnRegister.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent arg0) {
				btnRegister.setImage(SWTResourceManager.getImage(register.class, "/images/register_roll.png"));
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				btnRegister.setImage(SWTResourceManager.getImage(register.class, "/images/register_down.png"));
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				btnRegister.setImage(SWTResourceManager.getImage(register.class, "/images/register_down.png"));
			}
		});
		btnRegister.setBounds(114, 273, 99, 33);
		
		Label label_5 = new Label(group, SWT.NONE);
		label_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_5.setBounds(218, 235, 30, 17);
		label_5.setText("协议");
		
		Label lblQq = new Label(group, SWT.NONE);
		lblQq.setBounds(41, 148, 61, 17);
		lblQq.setText("QQ邮箱：");
		
		text_mail = new Text(group, SWT.BORDER);
		text_mail.setBounds(126, 148, 164, 23);
		
		Label label_7 = new Label(group, SWT.NONE);
		label_7.setBounds(41, 192, 61, 17);
		label_7.setText("性别：");
		
		//打开协议按钮
		label_5.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e){
					new protocol().open();
			}
		});;
		
		sashForm.setWeights(new int[] {1, 8});
		
	}
	public boolean isRegex(String str){
		String ss="\\w+@\\w+\\.\\w+";
		Pattern regex=Pattern.compile(ss);
		Matcher matcher=regex.matcher(str);
		return matcher.matches();	
	}
}
