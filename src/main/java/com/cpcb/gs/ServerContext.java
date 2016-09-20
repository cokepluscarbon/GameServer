package com.cpcb.gs;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.logic.LogicPlayer;
import com.cpcb.gs.rpc.RpcHandlerMapping;

public class ServerContext {
	private final Logger logger = LoggerFactory.getLogger(ServerContext.class);
	public static ApplicationContext ctx;
	public static Map<String, RpcHandlerMapping> rpcHandlerMap = new HashMap<String, RpcHandlerMapping>();

	public void start() {
		ctx = new FileSystemXmlApplicationContext("config/spring/spring-context.xml");

		LogicPlayer logicPlayer = ctx.getBean(LogicPlayer.class);
		for (Method method : LogicPlayer.class.getDeclaredMethods()) {
			Rpc rpc = method.getDeclaredAnnotation(Rpc.class);
			if (rpc != null) {
				rpcHandlerMap.put(rpc.value(), new RpcHandlerMapping(LogicPlayer.class, method));
			}
		}

		logger.info("RpcHandlerMap size is {}", rpcHandlerMap.size());
	}

}
