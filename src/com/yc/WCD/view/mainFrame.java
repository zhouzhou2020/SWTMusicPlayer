package com.yc.WCD.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Inherited;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.WCD.entity.MP3Player;
import com.yc.WCD.entity.users;
import com.yc.WCD.serviceImpl.ExportMusicImpl;
import com.yc.WCD.serviceImpl.MusicServiceImpl;
import com.yc.WCD.serviceImpl.importMusicImpl;
import com.yc.WCD.serviceImpl.updateUSERImpl;
import com.yc.WCD.util.UIUtil;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class mainFrame {

	public static Shell shell;
	private static boolean flag_play = false;//记录播放按钮的点击
	public static boolean flag_hasUser = false;//记录是否有人登录
	private static boolean flag = false;//记录播放按钮的点击
	private static boolean flag_sound = true;//记录声音按钮的点击
	private static String musicPath;
	private static String musicName;
	public static Label lbl_login;//登录按钮
	public static Label lbl_logo;//用户头像
	public static users user;//登录中的用户
	public static Label lblmp;//显示音乐名的标签
	public static Label lbl_register ;//注册标签
	public static Label label_tool;//设置标签
	public static Table tableDefaultList;
	public static Table tableSongLib;
	public static MP3Player mp3;
	public static String passsword;
	public static TreeItem treeItem_like;//收藏歌曲
	public static TreeItem treeItem_recent;
	public static String str;//记录读取音乐文件的路径
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			mainFrame window = new mainFrame();
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
		systemPrepare();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void systemPrepare() {
		importMusicImpl im = new importMusicImpl();
		File f1 = new File("src/defaultMusicPath.txt");
		str = "";
		try {
			FileReader in = new FileReader(f1);
			char[] cs = new char[1024];//存储数据的数组
			int len = 0 ;//读取到的字符的个数
			while((len=in.read(cs))!=-1){
				str += new String(cs,0,len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		im.scan(str);
		im.recDownload();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NONE);
		shell.setImage(SWTResourceManager.getImage(mainFrame.class, "/images/logo_update.jpg"));
		shell.setSize(800, 600);
		shell.setText("SWT Application"); 
		UIUtil.winCenter(shell);
		UIUtil.moveWin(shell);
	
		
		SashForm sashForm_main = new SashForm(shell, SWT.VERTICAL);
		sashForm_main.setSize(800, 600);
		sashForm_main.setLocation(0, 0);
		
		Composite composite_top = new Composite(sashForm_main, SWT.NONE);
		composite_top.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		addmoveWin(composite_top);
		
		Label lblClose = new Label(composite_top, SWT.NONE);
		lblClose.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblClose.setImage(UIUtil.changeImage("images/btn_close_normal.png", 39, 39) );
		lblClose.setBounds(761, 0, 39, 39);
		UIUtil.addCloseEvent(lblClose);
		
		lbl_logo = new Label(composite_top, SWT.NONE);
		lbl_logo.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_logo.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_logo.setImage(UIUtil.changeImage("images/user.png", 75, 45));
		lbl_logo.setBounds(0, 0, 75, 45);
		lbl_logo.setToolTipText("修改头像");
		new updateUSERImpl().updateUserPic(lbl_logo);
		
		lbl_login = new Label(composite_top, SWT.NONE);
		lbl_login.setForeground(SWTResourceManager.getColor(248, 248, 255));
		lbl_login.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_login.setBounds(81, 17, 29, 17);
		lbl_login.setText("登录|");
		
		lbl_register = new Label(composite_top, SWT.NONE);
		lbl_register.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_register.setForeground(SWTResourceManager.getColor(248, 248, 255));
		lbl_register.setBounds(117, 17, 51, 17);
		lbl_register.setText("注册");
		
		Label lblMin = new Label(composite_top, SWT.NONE);
		lblMin.setImage(UIUtil.changeImage("images/btn_mini_normal.png", 39, 39));
		lblMin.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblMin.setBounds(722, 0, 39, 39);
		UIUtil.addMinEvent(lblMin, lblMin.getShell());
		
		label_tool = new Label(composite_top, SWT.NONE);
		label_tool.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				new updateInfo(shell, SWT.MIN|SWT.CLOSE).open();
			}
		});
		label_tool.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_tool.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_tool.setBounds(168, 17, 39, 17);
		label_tool.setText("设置");
		label_tool.setVisible(false);
		
		textContent = new Text(composite_top, SWT.BORDER);
		textContent.setBounds(420, 14, 133, 23);
		
		Label lblSearch = new Label(composite_top, SWT.NONE);
		lblSearch.setImage(UIUtil.changeImage("images/find.jpg", 44, 33));
		lblSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		lblSearch.setFont(SWTResourceManager.getFont("楷体", 12, SWT.ITALIC));
		lblSearch.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblSearch.setBounds(559, 10, 44, 33);
		
		Label label_4 = new Label(composite_top, SWT.NONE);
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_4.setImage(UIUtil.changeImage("images/theme2.png", 30, 30));
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_4.setBounds(650, 5, 39, 39);
		
		//登录和注册事件
		lbl_register.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e){
				if(!flag_hasUser){
					new register().open();
				}
				}
		});
		
		lbl_login.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e){
				if(flag_hasUser == false){
					new login().open();
				}else{
					MessageBox mb = new MessageBox(shell);
					mb.setText("退出");
					mb.setMessage("你已成功退出！！！");
					mb.open();
					lbl_logo.setImage(UIUtil.changeImage("images/user.png", 75, 45));
					lbl_login.setText("登录|");
					lbl_register.setText("注册");
					label_tool.setVisible(false);
					treeItem_like.removeAll();
					user = null;
					flag_hasUser = false;
				}
				}
		});
		
		Label lbl_top = new Label(sashForm_main, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		Composite composite_center = new Composite(sashForm_main, SWT.NONE);
		composite_center.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_center = new SashForm(composite_center, SWT.NONE);
		
		Composite composite_left = new Composite(sashForm_center, SWT.NONE);
		composite_left.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_2 = new SashForm(composite_left, SWT.NONE);
		
		TabFolder tabFolder_1 = new TabFolder(sashForm_2, SWT.NONE);
		tabFolder_1.setTouchEnabled(true);
		
		TabItem tabItem_3 = new TabItem(tabFolder_1, SWT.NONE);
		tabItem_3.setText("播放列表");
		
		
		Composite composite_3 = new Composite(tabFolder_1, SWT.NONE);
		tabItem_3.setControl(composite_3);
		
		Tree tree = new Tree(composite_3, SWT.BORDER);
		tree.setFont(SWTResourceManager.getFont("华文行楷", 12, SWT.NORMAL));
		tree.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		tree.setBounds(0, 0, 240, 427);
		
		treeItem_like = new TreeItem(tree, SWT.NONE);
		treeItem_like.setFont(SWTResourceManager.getFont("华文行楷", 14, SWT.NORMAL));
		treeItem_like.setText("我喜欢");
		
		treeItem_recent = new TreeItem(tree, SWT.NONE);
		treeItem_recent.setFont(SWTResourceManager.getFont("华文行楷", 14, SWT.NORMAL));
		treeItem_recent.setText("最近播放");
		
		TabItem tbtmNewItem = new TabItem(tabFolder_1, SWT.NONE);
		tbtmNewItem.setText("下载管理");
		
		Composite composite_4 = new Composite(tabFolder_1, SWT.NONE);
		tbtmNewItem.setControl(composite_4);
		
		Tree tree_1 = new Tree(composite_4, SWT.BORDER);
		tree_1.setFont(SWTResourceManager.getFont("华文行楷", 16, SWT.NORMAL));
		tree_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		tree_1.setBounds(0, 0, 240, 427);
		
		treeItem_recent = new TreeItem(tree_1, SWT.NONE);
		treeItem_recent.setText("最近下载");
		sashForm_2.setWeights(new int[] {1});
		
		Label lbl_ccenter = new Label(sashForm_center, SWT.SEPARATOR);
		
		Composite composite_right = new Composite(sashForm_center, SWT.NONE);
		composite_right.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final TabFolder tabFolder = new TabFolder(composite_right, SWT.NONE);
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("歌单列表");
		
		Group group_music = new Group(tabFolder, SWT.NONE);
		tabItem.setControl(group_music);
		group_music.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(group_music, SWT.VERTICAL);
		
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		
		text_1 = new Label(composite, SWT.NONE);
		text_1.setTouchEnabled(true);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		text_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		text_1.setFont(SWTResourceManager.getFont("华文行楷", 20, SWT.BOLD | SWT.ITALIC));
		text_1.setText("默认列表");
		text_1.setBounds(10, 10, 133, 35);
		
		Label label_importMusic = new Label(composite, SWT.NONE);
		label_importMusic.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_importMusic.setImage(UIUtil.changeImage("images/add.gif", 25, 25));
		label_importMusic.setBounds(354, 10, 25, 25);//导入
		label_importMusic.setToolTipText("添加歌曲");
		new importMusicImpl().Myimport(label_importMusic);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setImage(UIUtil.changeImage("images/loveheart_click.png", 25, 25));
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_1.setBounds(445, 10, 25, 25);//收藏
		label_1.setToolTipText("收藏");
		new MusicServiceImpl().collection(label_1);
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setImage(UIUtil.changeImage("images/delete.png", 25, 25));
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_2.setFont(SWTResourceManager.getFont("楷体", 18, SWT.BOLD));
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_2.setBounds(496, 10, 25, 25);
		label_2.setToolTipText("删除");
		new MusicServiceImpl().deleteMusic(label_2);
		
		Label label_7 = new Label(composite, SWT.NONE);
		label_7.setImage(UIUtil.changeImage("images/updown.gif", 30, 30));
		label_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_7.setBounds(398, 10, 25, 25);//上传按钮
		label_7.setToolTipText("上传");
		new MusicServiceImpl().upload(label_7);
		
		Label lbl_file = new Label(composite, SWT.NONE);
		lbl_file.setImage(UIUtil.changeImage("images/file.png", 25, 25));
		lbl_file.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		lbl_file.setBounds(311, 10, 25, 25);
		lbl_file.setToolTipText("添加文件");
		new importMusicImpl().importFile(lbl_file);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		tableDefaultList = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		tableDefaultList.setHeaderVisible(true);
		
		TableColumn tblclmn_num = new TableColumn(tableDefaultList, SWT.NONE);
		tblclmn_num.setWidth(50);
		tblclmn_num.setText("序号");
		
		TableColumn tblclmn_songName = new TableColumn(tableDefaultList, SWT.NONE);
		tblclmn_songName.setWidth(120);
		tblclmn_songName.setText("歌曲名");
		
		TableColumn tblclmn_artist = new TableColumn(tableDefaultList, SWT.NONE);
		tblclmn_artist.setWidth(105);
		tblclmn_artist.setText("歌手");
		
		TableColumn tblclmn_album = new TableColumn(tableDefaultList, SWT.NONE);
		tblclmn_album.setWidth(150);
		tblclmn_album.setText("专辑");
		
		TableColumn tblclmn_time = new TableColumn(tableDefaultList, SWT.NONE);
		tblclmn_time.setWidth(100);
		tblclmn_time.setText("时间");
		
		TableColumn tblclmn_Clickcount = new TableColumn(tableDefaultList, SWT.NONE);
		tblclmn_Clickcount.setText("播放次数");
		
		TableColumn tblclmnNewColumn = new TableColumn(tableDefaultList, SWT.NONE);
		tblclmnNewColumn.setText("New Column");
		sashForm.setWeights(new int[] {1, 6});
		
		TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("歌词");
		
		Group group_lrc = new Group(tabFolder, SWT.NONE);
		tabItem_2.setControl(group_lrc);
		group_lrc.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(group_lrc, SWT.NONE);
		
		Composite composite_2 = new Composite(sashForm_1, SWT.NONE);
		composite_2.setLayout(null);
		
		Label lbl_background = new Label(composite_2, SWT.NONE);
		lbl_background.setBounds(0, 0, 531, 390);
		sashForm_1.setWeights(new int[] {1});
		sashForm_center.setWeights(new int[] {250, 2, 548});
		lbl_background.setImage(UIUtil.changeImage("images/backgroundMusic.jpg",
				lbl_background.getBounds().width, lbl_background.getBounds().height));
		
		final TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("乐库");
		
		SashForm sashForm_3 = new SashForm(tabFolder, SWT.NONE);
		tabItem_1.setControl(sashForm_3);
		
		Composite compositeSongLib = new Composite(sashForm_3, SWT.NONE);
		compositeSongLib.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		
		Label label_5 = new Label(compositeSongLib, SWT.NONE);
		label_5.setImage(UIUtil.changeImage("images/backgroundMusic.jpg", 450, 110));
		label_5.setBounds(30, 20, 450, 110);
		
		Button btnFindAll = new Button(compositeSongLib, SWT.NONE);
		btnFindAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableSongLib.removeAll();
				new ExportMusicImpl().exportMP3();
			}
		});
		btnFindAll.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		btnFindAll.setBounds(303, 142, 55, 21);
		btnFindAll.setText("查看");
		
		Button btndownload = new Button(compositeSongLib, SWT.NONE);
		btndownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem ti;
				if(tableSongLib.getSelectionCount()==0){
					MessageBox mb = new MessageBox(mainFrame.shell);
					mb.setText("温馨提示！");
					mb.setMessage("亲！请选中要下载的歌哦！");
					mb.open();
				}else{
					ti=tableSongLib.getSelection()[0];
					int songid = Integer.parseInt(ti.getText(0));
					new ExportMusicImpl().download(songid);
				}	
			}
		});
		btndownload.setText("下载");
		btndownload.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		btndownload.setBounds(364, 142, 55, 21);
		
		Button button_1 = new Button(compositeSongLib, SWT.NONE);
		button_1.setText("收藏");
		button_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		button_1.setBounds(425, 142, 55, 21);
		new MusicServiceImpl().collection(button_1);
		
		tableSongLib = new Table(compositeSongLib, SWT.BORDER | SWT.FULL_SELECTION);
		tableSongLib.setLinesVisible(true);
		tableSongLib.setHeaderVisible(true);
		tableSongLib.setBounds(10, 169, 517, 250);
		
		TableColumn tableColumn_2 = new TableColumn(tableSongLib, SWT.NONE);
		tableColumn_2.setWidth(50);
		tableColumn_2.setText("序号");
		
		TableColumn tableColumn_3 = new TableColumn(tableSongLib, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("歌曲名");
		
		TableColumn tableColumn_4 = new TableColumn(tableSongLib, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("歌手");
		
		TableColumn tableColumn_5 = new TableColumn(tableSongLib, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("专辑");
		
		TableColumn tableColumn_6 = new TableColumn(tableSongLib, SWT.NONE);
		tableColumn_6.setWidth(90);
		tableColumn_6.setText("时间");
		
		TableColumn tableColumn_7 = new TableColumn(tableSongLib, SWT.NONE);
		tableColumn_7.setWidth(70);
		tableColumn_7.setText("播放次数");
		sashForm_3.setWeights(new int[] {1});
		
		Label label_down = new Label(sashForm_main, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		Composite composite_down = new Composite(sashForm_main, SWT.NONE);
		composite_down.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		composite_down.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		
		Label lbl_rewind = new Label(composite_down, SWT.NONE);
		lbl_rewind.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_rewind.setImage(UIUtil.changeImage("images/rewind.png", 50, 40));
		lbl_rewind.setBounds(0, 25, 50, 40);
		UIUtil.lblClick(lbl_rewind, "images/rewind.png", "images/rewind.png", "images/rewind on.png");
		
	    Label lbl_play = new Label(composite_down, SWT.NONE);
		lbl_play.setImage(UIUtil.changeImage( "images/play.png", 50, 40));
		lbl_play.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_play.setBounds(60, 25, 50, 40);
		play(lbl_play);
		
		Label lbl_forward = new Label(composite_down, SWT.NONE);
		lbl_forward.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_forward.setImage(UIUtil.changeImage( "images/forward.png", 50, 40));
		lbl_forward.setBounds(120, 25, 50, 40);
		sashForm_main.setWeights(new int[] {48, 2, 450, 2, 98});
		UIUtil.lblClick(lbl_forward,  "images/forward.png",  "images/forward.png",  "images/forward on.png");
		
		Label lbl_sound = new Label(composite_down, SWT.NONE);
		lbl_sound.setImage(UIUtil.changeImage("images/sound on.png", 50, 40));
		lbl_sound.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lbl_sound.setBounds(590, 25, 50, 40);
		
		Scale scale = new Scale(composite_down, SWT.NONE);
		scale.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		scale.setBounds(640, 25, 160, 40);
		
		Combo combo = new Combo(composite_down, SWT.READ_ONLY);
		combo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		combo.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		combo.setItems(new String[] {"单曲循环", "顺序播放", "随机播放"});
		combo.setBounds(485, 35, 88, 25);
		combo.select(0);
		
		ProgressBar progressBar = new ProgressBar(composite_down, SWT.BORDER | SWT.SMOOTH);
		progressBar.setBounds(179, 48, 300, 5);
		
		Label label_song = new Label(composite_down, SWT.NONE);
		label_song.setText("00:00");
		label_song.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label_song.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		label_song.setBounds(438, 25, 41, 17);
		
		lblmp = new Label(composite_down, SWT.NONE);
		lblmp.setText("未播放音乐");
		lblmp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		lblmp.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblmp.setBounds(176, 10, 161, 25);
		sashForm_main.setWeights(new int[] {47, 1, 458, 1, 81});
		getRunWord(lblmp);
		
		//搜索事件
		lblSearch.addMouseListener(new MouseAdapter(){
			Label b;
			//按下
			public void mouseDown(MouseEvent e){
				b = (Label) e.widget;
				b.setImage(UIUtil.changeImage("images/find on.png", b.getBounds().width, b.getBounds().height));	
			}
			//弹起
			public void mouseUp(MouseEvent e){
				b = (Label) e.widget;
				b.setImage(UIUtil.changeImage("images/find.jpg", b.getBounds().width, b.getBounds().height));
				String content = textContent.getText().toString();
				if(!"".equals(content)){
					tableSongLib.removeAll();
					new ExportMusicImpl().searchSong(content);
					tabFolder.setSelection(tabItem_1);					
				}else{
					MessageBox mb =new MessageBox(mainFrame.shell);
					mb.setText("温馨提示！");
					mb.setMessage("亲！还没输入关键词哦！");
					mb.open();
				}
			}
		});
		
		lbl_sound.addMouseListener(new MouseAdapter() {
			//按下
			public void mouseDown(MouseEvent e){
				Label b = (Label) e.widget;
				if(flag){
					b.setImage(UIUtil.changeImage("images/sound.png", b.getBounds().width, b.getBounds().height));
				}else{
					b.setImage(UIUtil.changeImage("images/sound on.png", b.getBounds().width, b.getBounds().height));
				}
			}
			//弹起
			public void mouseUp(MouseEvent e){
				if(flag){
					flag = false;
				}else{
					flag = true;
				}
			}
		});

	}
	
	//跑马灯效果
			private void getRunWord(final Label lblmp) {
				new Thread(){
					public void run(){
						while(true){
							try {
								Thread.sleep(200);
								Display.getDefault().asyncExec(new Runnable(){
									public void run(){
										String content = lblmp.getText();
										content = content.substring(1)+content.charAt(0);
										lblmp.setText(content);
									}
								});
								
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}.start();
				
			}
	
	//为上部面板添加移动事件
	private int beginX;
	private int beginY;
	private boolean isPress;
	private Label text_1;
	private Text textContent;
	
	private void addmoveWin(Composite composite_top) {
		composite_top.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(isPress){
					int moveX = e.x-beginX;
					int moveY = e.y-beginY;
					shell.setLocation(shell.getBounds().x+moveX,shell.getBounds().y+moveY);
				}
				
			}
		});
		composite_top.addMouseListener(new MouseAdapter() {
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

	
	public void play(Label label) {
		//添加事件
		label.addMouseTrackListener(new MouseTrackAdapter() {
					//放上
					public void mouseHover(MouseEvent e){
						Label b = (Label) e.widget;
						if(flag_play){
							b.setImage(UIUtil.changeImage("images/pause on.png", b.getBounds().width, b.getBounds().height));
						}else{
							b.setImage(UIUtil.changeImage("images/play on.png", b.getBounds().width, b.getBounds().height));
						}
					}
					//移开
					public void mouseExit(MouseEvent e){
						Label b = (Label) e.widget;
						if(flag_play){
							b.setImage(UIUtil.changeImage("images/pause.png", b.getBounds().width, b.getBounds().height));
						}else{
							b.setImage(UIUtil.changeImage("images/play.png", b.getBounds().width, b.getBounds().height));
						}
					}
				});
									
				label.addMouseListener(new MouseAdapter() {
					//按下
					public void mouseDown(MouseEvent e){
						Label b = (Label) e.widget;
						if(flag_play){
							b.setImage(UIUtil.changeImage("images/play.png", b.getBounds().width, b.getBounds().height));
						}else{
							b.setImage(UIUtil.changeImage("images/pause.png", b.getBounds().width, b.getBounds().height));
						}
					}
					//弹起
					public void mouseUp(MouseEvent e){
						new importMusicImpl().getMusic();
						if(flag_play){
							flag_play = false;
							if(mainFrame.mp3!=null){
								//mainFrame.mp3.stop();
							}
						}else{
							flag_play = true;
							if(mainFrame.mp3!=null){
								System.out.println("正在播放...");
								lblmp.setText(importMusicImpl.tiSeleItem.getText(1)+"-"+importMusicImpl.tiSeleItem.getText(2)+".mp3");
								mainFrame.mp3.play();
								System.out.println("正在播放...");
							}
						}
					}
				});
	}
}
