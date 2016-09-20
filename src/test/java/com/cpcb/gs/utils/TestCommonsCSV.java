package com.cpcb.gs.utils;

import java.io.IOException;

import org.junit.Test;

import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.io.BaseDeploy;
import com.cpcb.gs.io.ProtocolDeploy;

public class TestCommonsCSV {

	@Test
	public void test_tableLoader() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(101, ProtocolDeploy.class);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader2() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(1, ProtocolDeploy.class);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader3() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(11, ProtocolDeploy.class);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader4() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(100, ProtocolDeploy.class);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader5() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(201, ProtocolDeploy.class);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_tableLoader6() throws IOException {
		long start = System.currentTimeMillis();

		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(401, ProtocolDeploy.class);
		System.out.println(deploy);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}

	@Test
	public void test_anno() {
		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(1, ProtocolDeploy.class);
		System.out.println(deploy.toString());
	}

	@Rpc("rpc_get_mission")
	public void test_rpc() {

	}
}
