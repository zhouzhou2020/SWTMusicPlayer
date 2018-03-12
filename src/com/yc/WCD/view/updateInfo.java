package com.yc.WCD.view;

import oracle.net.aso.b;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.WCD.entity.users;
import com.yc.WCD.serviceImpl.updateUSERImpl;
import com.yc.WCD.util.UIUtil;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class updateInfo extends Dialog {

	protected Object result;
	protected Shell shell;
	private Label label_name;
	private Text text_name;
	private Text text_password;
	private Text text_mail;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public updateInfo(Shell parent, int style) {
		super(parent, style);
		setText("修改信息");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(452, 248);
		shell.setText(getText());
		UIUtil.moveWin(shell);
		UIUtil.winCenter(shell);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 446, 219);
		
		Label label = new Label(composite, SWT.NONE);
		label.setImage(UIUtil.changeImage("images/logo_update.jpg", 60, 60));
		label.setBounds(63, 41, 60, 60);
		
		label_name = new Label(composite, SWT.NONE);
		label_name.setBounds(154, 41, 40, 17);
		label_name.setText("昵称：");
		
		text_name = new Text(composite, SWT.BORDER);
		text_name.setText(mainFrame.user!=null?mainFrame.user.getUseName():"");
		text_name.setBounds(214, 38, 157, 23);
		
		Label label_password = new Label(composite, SWT.NONE);
		label_password.setText("密码：");
		label_password.setBounds(154, 84, 40, 17);
		
		text_password = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		text_password.setText(mainFrame.passsword);
		text_password.setBounds(214, 78, 157, 23);
		
		Label lblQq = new Label(composite, SWT.NONE);
		lblQq.setBounds(154, 128, 61, 17);
		lblQq.setText("QQ邮箱：");
		
		text_mail = new Text(composite, SWT.NONE);
		text_mail.setBounds(214, 122, 157, 23);
		text_mail.setText(mainFrame.user.getMail());
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = new MessageBox(shell);
				mb.setText("提示");
				if(mainFrame.user==null){
					mb.setMessage("您还没有登录！！！");
					mb.open();
				}else{
					String name = text_name.getText();
					String password = text_password.getText();
					String mail = text_mail.getText();
					if(password.equals(mainFrame.user.getPassword())){
						password = login.text_1.getText();
					}
					boolean b;
					if(b = new updateUSERImpl().updateUserInfo(name, password,mail)){
						mb.setMessage("修改成功！！！");
						mb.open();
						shell.dispose();
					}else{
						mb.setMessage("修改失败！！！");
						mb.open();
					}
				}
			}
		});
		btnNewButton.setBounds(321, 172, 80, 25);
		btnNewButton.setText("修改");
		

		
	}
}
