package com.briup.bean;

import java.io.Serializable;

public class Record implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String content;
	private float money;
	private String date;

	public Record(String type, String content, float money, String date) {
		super();
		this.type = type;
		this.content = content;
		this.money = money;
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Record() {
		super();
		// TODO Auto-generated constructor stub
	}
}
