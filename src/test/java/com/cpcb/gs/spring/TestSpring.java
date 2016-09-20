package com.cpcb.gs.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cpcb.gs.logic.LogicPlayer;
import com.cpcb.gs.rpc.RpcHandler;

public class TestSpring {

	@Test
	public void test_01() {
		// ApplicationContext context = new ClassPathXmlApplicationContext(new
		// String[] { "services.xml", "daos.xml" });
		ApplicationContext ctx = new FileSystemXmlApplicationContext("config/spring/spring-context.xml");

		System.out.println("Bean Count : " + ctx.getBeanDefinitionCount());
		System.out.println("LogicPlayer : " + ctx.getBean(LogicPlayer.class));

		RpcHandler rpcHandler = ctx.getBean(RpcHandler.class);

		Integer i = this.getBean(Integer.class);
	}

	public <T> T getBean(Class<T> requiredType) {
		return null;
	}

}
