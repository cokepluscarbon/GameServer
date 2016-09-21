package com.cpcb.gs.io;

import java.io.DataOutputStream;

import io.netty.buffer.ByteBuf;

public class RpcWriter {
	private DataOutputStream out;

	public RpcWriter(ByteBuf buf) {
		out = new DataOutputStream(buf.);
	}

	public void WriteByte(byte value) {

	}

	public void WriteInt(int value) {

	}

	public void WriteLong(long value) {

	}

	public void WriteFloat(float value) {

	}

	public void WriteDouble(double value) {

	}

	public void WriteBool(boolean value) {

	}

	public void WriteString(String str) {

	}

	public void WriteObject(Object object) {

	}

	public byte[] getBytes() {

	}

}
