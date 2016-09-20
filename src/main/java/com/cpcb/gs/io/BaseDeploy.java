package com.cpcb.gs.io;

import com.cpcb.gs.annotation.Deploy;

public class BaseDeploy {
	public int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static <T extends BaseDeploy> T getDeploy(int key, Class<T> classType) {
		Deploy deploy = classType.getAnnotation(Deploy.class);
		return (T) TableLoader.Load(deploy.value(), classType).get(key);
	}
}
