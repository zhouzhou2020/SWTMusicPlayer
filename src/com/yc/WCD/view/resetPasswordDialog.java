package com.yc.WCD.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.WCD.service.FindPassword;
import com.yc.WCD.serviceImpl.FindPasswordImpl;
import com.yc.WCD.util.UIUtil;

public class resetPasswordDialog extends Dialog {

	protected boolean result;
	protected Shell shell;
	private Text text_name;
	private Text text_qq;
	private String qq;
	private String randomPassword;
	/**5
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public resetPasswordDialog(Shell parent,String randomPassword) {
		super(parent, SWT.SYSTEM_MODAL);
		setText("重置密码");
		this.randomPassword = randomPassword;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public boolean open() {
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
		shell.setSize(394, 197);
		shell.setText(getText());
		UIUtil.winCenter(shell);
		UIUtil.moveWin(shell);
		
		Label labelName = new Label(shell, SWT.NONE);
		labelName.setBounds(48, 50, 72, 17);
		labelName.setText("\u8BF7\u8F93\u5165\u59D3\u540D\uFF1A");
		
		text_name = new Text(shell, SWT.BORDER);
		text_name.setBounds(126, 47, 180, 23);
		
		text_qq = new Text(shell, SWT.BORDER);
		text_qq.setBounds(126, 104, 180, 23);
		
		Label lblqq = new Label(shell, SWT.NONE);
		lblqq.setText("请输入邮箱：");
		lblqq.setBounds(48, 107, 72, 17);
		
		Button btnVialdate = new Button(shell, SWT.NONE);
		btnVialdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 String name = text_name.getText();
				 qq = text_qq.getText();
				FindPassword ss = new FindPasswordImpl();
				if(!(result = ss.sendMailVaildate(name,qq))){
					MessageBox mb = new MessageBox(shell);
					mb.setText("QQ邮箱验证֤");
					mb.setMessage("注册QQ与姓名不一致，请与管理员联系");
					mb.open();
				}else{
					
					if(resetPassword(qq,name)){
						shell.dispose();
					}
				}
			}
		});
		btnVialdate.setBounds(277, 144, 80, 27);
		btnVialdate.setText("\u9A8C\u8BC1");
		
		Label label = new Label(shell, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(resetPasswordDialog.class, "/images/btn_close_normal.png"));
		label.setBounds(353, 0, 39, 17);
		UIUtil.addCloseEvent(label);

	}
	private boolean resetPassword(String qq,String name) {
		FindPassword ss = new FindPasswordImpl();
		return ss.resetPassword(qq,name,randomPassword);
	}
	public String getQqMail(){
		return String.format("%s@qq.com", qq);
	}
}
