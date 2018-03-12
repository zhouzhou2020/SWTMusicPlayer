package com.yc.WCD.view;
import java.util.Random;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import com.yc.WCD.entity.users;
import com.yc.WCD.serviceImpl.UserLoginImpl;
import com.yc.WCD.serviceImpl.importMusicImpl;
import com.yc.WCD.util.SimpleSendMail;
import com.yc.WCD.util.UIUtil;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Composite;

public class login {
	protected Shell shell;
	private Text text;
	public static Text text_1;
	/**
	 * 主界面 开始
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			login window = new login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 打开窗口
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents(display);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	/**
	 * 窗口布局
	 */
	protected void createContents(Display display) {
		shell = new Shell(display,SWT.NONE);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		shell.setSize(434, 378);
		shell.setLocation(400,200);
		shell.setText("\u7231\u79C0\u64AD\u653E\u5668");
		shell.setLayout(null);
		
		Label lb_close = new Label(shell, SWT.NONE);
		lb_close.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lb_close.setImage(UIUtil.changeImage("images/btn_close_normal.png", 30, 20));
		lb_close.setAlignment(SWT.CENTER);
		lb_close.setBounds(402, 0, 30, 20);
		UIUtil.addCloseEvent(lb_close);
		
		Label lb_min = new Label(shell, SWT.NONE);
		lb_min.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lb_min.setImage(UIUtil.changeImage("images/btn_mini_normal.png", 30, 20));
		lb_min.setBounds(372, 0, 30, 20);
		lb_min.setAlignment(SWT.CENTER);
		UIUtil.addMinEvent(lb_min,shell);
		
		Label lblBackGround = new Label(shell, SWT.NONE);
		lblBackGround.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		lblBackGround.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblBackGround.setAlignment(SWT.CENTER);
		lblBackGround.setBounds(0, 26, 432, 176);
		lblBackGround.setImage(UIUtil.changeImage("images/background.png",432, 176));
		UIUtil.moveWin(shell);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		composite.setBounds(0, 200, 432, 176);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("用户名：");
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label.setBounds(123, 13, 61, 17);
		
		text = new Text(composite, SWT.BORDER);
		text.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		text.setToolTipText("请输入用户名");
		text.setText("");
		text.setBounds(190, 10, 132, 23);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("密   码：");
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_1.setBounds(123, 61, 61, 17);
		
		text_1 = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		text_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		text_1.setToolTipText("请输入密码");
		text_1.setText("a");
		text_1.setBounds(190, 58, 132, 23);
		
		Button button = new Button(composite, SWT.NONE);
		button.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		button.setText("登录");
		button.setBounds(115, 108, 80, 27);
		loginOperator(button);
		
		Button button_1 = new Button(composite, SWT.NONE);
		button_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		button_1.setText("取消");
		button_1.setBounds(225, 108, 80, 27);
		UIUtil.addButtonClose(button_1.getShell(), button_1);
		
		Link link = new Link(composite, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = new MessageBox(shell,SWT.YES|SWT.NO|SWT.ICON_INFORMATION);
				mb.setText("忘记密码");
				mb.setMessage("\n是否重置密码？\n\n重置密码在你的qq邮箱中可以找到重置密码...\n");
				if(mb.open()==SWT.YES){
					String randomPassword = getRandomPassword();
					resetPasswordDialog id = new resetPasswordDialog(shell,randomPassword);
					if(id.open()){
						boolean isSendResult = new SimpleSendMail().send("重置密码"
								, String.format("你重置后的密码是:%s", randomPassword)
								,id.getQqMail());
						mb = new MessageBox(shell);
						mb.setText("发送邮件结果");
						mb.setMessage(String.format("发送邮件%s", isSendResult ? "成功..." : "失败..."));
						mb.open();
					}
				}else{
					System.out.println("不重置");
				}
			}


			
		});
		link.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.ITALIC));
		link.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		link.setBounds(328, 61, 53, 17);
		link.setText("<a>忘记密码</a>");
	}
	//生成随机密码
	private String getRandomPassword() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < 8 ; i++){
			//生成a-z的随机数
			sb.append((char)(random.nextInt( ('z' - 'a' ) + 1 ) + 'a'));
		}
		return sb.toString();
	}
	//界面登入
	public void loginOperator(Button btn_enter) {
		btn_enter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn=(Button) e.widget;
				MessageBox mb=new MessageBox(btn.getShell(),SWT.NONE);
				mb.setText("登录信息");
				String name=text.getText();
				String password=text_1.getText();
				mainFrame.passsword=text_1.getText();
				if(   (mainFrame.user = new UserLoginImpl().login(name, password))   !=null)
				{
					mainFrame.lbl_login.setText("退出|");
					mainFrame.lbl_register.setText("你好,"+mainFrame.user.getUseName());
					mainFrame.label_tool.setVisible(true);
					mainFrame.flag_hasUser = true;
					new importMusicImpl().importMylike();
					if(mainFrame.user.getPhoto()!=null){
						mainFrame.lbl_logo.setImage(UIUtil.changeImage(mainFrame.user.getPhoto(), 75, 45));
					}
					btn.getShell().close();
				}else{
							mb.setMessage("用户名或密码错误  请重新输入!!!");
							mb.open();
						}
				
			 }
	      	});
	}
}
