package com.cpcb.gs.io;

import com.cpcb.gs.Rpc;

public class BaseDeploy {
	public int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void GetDeploy() {
		Class clazz = this.getClass();
		Rpc annotation = (Rpc) clazz.getAnnotation(Rpc.class);

		System.out.println(annotation.value());
	}
}
