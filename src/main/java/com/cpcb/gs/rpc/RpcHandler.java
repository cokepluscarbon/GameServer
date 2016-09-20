package com.cpcb.gs.rpc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RpcHandler {
	private static Logger logger = LoggerFactory.getLogger(RpcHandler.class);
	private Map<String, Method> rpcMethodMap = new HashMap<String, Method>();

	public void addRpcMethod(String rpc, Method method) {
		rpcMethodMap.put(rpc, method);
		logger.debug("rpc[name={},Method={}]", rpc, method);
	}
}
