package com.cpcb.gs.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.logic.LogicPlayer;

public class TestLogicAnnotation {

	@Test
	public void test_01() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		LogicPlayer instance = new LogicPlayer();

		Method[] methods = LogicPlayer.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getAnnotation(Rpc.class) != null) {
				System.out.print(method.getName() + ":");
				for (Class<?> pType : method.getParameterTypes()) {
					System.out.print(pType + ",\t");
				}
				if (method.getName().equals("getPlayer")) {
					method.invoke(instance, 2016070588L);
				}
			}
			System.out.println();
		}
	}

}
