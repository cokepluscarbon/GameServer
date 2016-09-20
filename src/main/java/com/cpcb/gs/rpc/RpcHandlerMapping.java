package com.cpcb.gs.rpc;

import java.lang.reflect.Method;

public class RpcHandlerMapping {
	public Class<?> clazz;
	public Method method;

	public RpcHandlerMapping(Class<?> clazz, Method method) {
		super();
		this.clazz = clazz;
		this.method = method;
	}

}
