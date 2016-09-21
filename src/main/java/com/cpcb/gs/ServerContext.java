package com.cpcb.gs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.cpcb.gs.annotation.Logic;
import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.logic.LogicPlayer;
import com.cpcb.gs.rpc.RpcHandlerMapping;

public class ServerContext {
	private final Logger logger = LoggerFactory.getLogger(ServerContext.class);
	public static ApplicationContext ctx;
	public static Map<String, RpcHandlerMapping> rpcHandlerMap = new HashMap<String, RpcHandlerMapping>();

	public void start() {
		ctx = new FileSystemXmlApplicationContext("config/spring/spring-context.xml");

		InitHanderMap();
	}

	private void InitHanderMap() {
		for (Class<?> clazz : scanLogics()) {
			for (Method method : clazz.getDeclaredMethods()) {
				Rpc rpc = method.getDeclaredAnnotation(Rpc.class);
				if (rpc != null) {
					rpcHandlerMap.put(rpc.value(), new RpcHandlerMapping(LogicPlayer.class, method));
				}
			}

			logger.info("RpcHandlerMap size is {}", rpcHandlerMap.size());
		}
	}

	private List<Class<?>> scanLogics() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Logic.class));

		List<Class<?>> classList = new ArrayList<Class<?>>();
		for (BeanDefinition bd : scanner.findCandidateComponents("com.cpcb.gs")) {
			try {
				Class<?> logicClass = Class.forName(bd.getBeanClassName());
				classList.add(logicClass);
			} catch (ClassNotFoundException e) {
				logger.error("Could not find Class for name[{}]", bd.getBeanClassName());
			}
		}

		return classList;
	}

}
