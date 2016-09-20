package com.cpcb.gs.rpc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class RpcHandler {
	private Map<String, Method> rpcMethodMap = new HashMap<String, Method>();

	public void addRpcMethod(String rpc, Method method) {
		rpcMethodMap.put(rpc, method);
	}
}
