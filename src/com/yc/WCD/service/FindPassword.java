package com.yc.WCD.service;


public interface FindPassword {
	public boolean resetPassword(String qq,String name,String randomPassword);
	public boolean sendMailVaildate(String name, String qq);
}
