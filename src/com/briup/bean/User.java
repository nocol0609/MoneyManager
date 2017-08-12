package com.briup.bean;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String passwd;
	private String qurenpasswd;

	public User(String name, String passwd, String qurenpasswd) {
		super();
		this.name = name;
		this.passwd = passwd;
		this.qurenpasswd = qurenpasswd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getQurenpasswd() {
		return qurenpasswd;
	}

	public void setQurenpasswd(String qurenpasswd) {
		this.qurenpasswd = qurenpasswd;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String name, String passwd) {
		super();
		this.name = name;
		this.passwd = passwd;
	}

	public User(String passwd) {
		super();
		this.passwd = passwd;
		this.name = name;
	}
	// public User(String name){
	// super();
	// this.name=name;
	// }

}
