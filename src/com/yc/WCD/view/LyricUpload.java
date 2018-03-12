package com.yc.WCD.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.WCD.serviceImpl.ImportLrcImpl;
import com.yc.WCD.serviceImpl.LrcServiceImpl;
import com.yc.WCD.util.UIUtil;

public class LyricUpload extends Shell {
	public static Table tableUpload;
	public static LyricUpload shell;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new LyricUpload(display);
			UIUtil.winCenter(shell);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public LyricUpload(Display display) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		
		
		tableUpload = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableUpload.setBounds(10, 51, 574, 301);
		tableUpload.setHeaderVisible(true);
		tableUpload.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(tableUpload, SWT.NONE);
		tableColumn.setText("序号");
		tableColumn.setWidth(52);
		
		TableColumn tableItemMusicName = new TableColumn(tableUpload, SWT.NONE);
		tableItemMusicName.setWidth(181);
		tableItemMusicName.setText("歌名");
		
		TableColumn tableItemPath = new TableColumn(tableUpload, SWT.NONE);
		tableItemPath.setResizable(false);
		tableItemPath.setWidth(335);
		tableItemPath.setText("路径");
		
		Button btnUpload = new Button(this, SWT.NONE);
		btnUpload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new LrcServiceImpl().upload();
			}
		});
		btnUpload.setBounds(408, 18, 80, 27);
		btnUpload.setText("上传");
		
		Button btnLook = new Button(this, SWT.NONE);
		btnLook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ImportLrcImpl().ImportLrcToTable();
			}
		});
		btnLook.setBounds(494, 18, 80, 27);
		btnLook.setText("查看");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setTouchEnabled(true);
		lblNewLabel.setFont(SWTResourceManager.getFont("华文行楷", 25, SWT.NORMAL));
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblNewLabel.setBounds(99, 11, 230, 35);
		lblNewLabel.setText("歌词上传管理");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("歌词上传");
		setSize(600, 400);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
