package com.yc.WCD.entity;

import java.io.InputStream;

public class users{
	private int userId;
	private String username;
	private String password;
	private String sex;
	private InputStream photo;
	private String mail;
	public users(){}
	public users(int userId, String username, String password, String sex,
			InputStream photo,String mail) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.photo = photo;
		this.mail=mail;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUseName() {
		return username;
	}
	public void setUseName(String useName) {
		this.username = useName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public InputStream getPhoto() {
		return photo;
	}
	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "Users [userId=" + userId + ", useName=" + username
				+ ", password=" + password + ", sex=" + sex + ", photo="
				+ photo + ", mail=" + mail + "]";
	}	
}
