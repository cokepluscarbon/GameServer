package com.cpcb.gs.utils;

import java.io.IOException;

import org.junit.Test;

import com.cpcb.gs.io.ProtocolDeploy;

public class TestCommonsCSV {

	@Test
	public void test_tableLoader() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(101);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader2() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(1);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader3() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(11);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader4() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(100);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader5() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(201);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader6() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(401);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}
}
