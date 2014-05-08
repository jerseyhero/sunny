package com.ssh.model;

import org.springframework.beans.factory.annotation.Qualifier;

public class Znk {
	private String id;
	public void init(@Qualifier("iserDap")Znk znk){}
}
