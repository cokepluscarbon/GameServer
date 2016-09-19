package com.cpcb.gs.io;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class ProtocolDeploy extends BaseDeploy {
	public String rpc;
	public boolean encrypt;
	public long testLong;
	public float testFloat;
	public byte testByte;
	public double testDouble;
	public List<Integer> tests;
	public List<String> tests2;
	public List<Boolean> tests3;
	public JSONObject jsonObject;
	public InnerObject object;
	public TestEnum testEnum = TestEnum.A;

	@Override
	public String toString() {
		return "ProtocolDeploy [id=" + id + ", rpc=" + rpc + ", encrypt=" + encrypt + ", testLong=" + testLong
				+ ", testFloat=" + testFloat + ", testByte=" + testByte + ", testDouble=" + testDouble + ", tests="
				+ tests + ", tests2=" + tests2 + ", tests3=" + tests3 + ", jsonObject=" + jsonObject + ", object="
				+ object + ", testEnum=" + testEnum + "]";
	}

	public static enum TestEnum {
		A, B, C, D
	}

	public static class InnerObject {
		public int id;
		public String name;

		@Override
		public String toString() {
			return "InnerObject [id=" + id + ", name=" + name + "]";
		}
	}
}
