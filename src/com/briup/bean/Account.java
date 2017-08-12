package com.briup.bean;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private float price;

	public Account(String name, float price) {
		super();
		this.name = name;
		this.price = price;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(float price) {
		// TODO Auto-generated constructor stub
		super();
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
