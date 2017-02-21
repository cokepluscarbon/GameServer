package com.cpcb.gs.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class RpcWriter {
	private Logger logger = LoggerFactory.getLogger(RpcWriter.class);
	private ByteArrayOutputStream byteArray;
	private DataOutputStream out;
	private Charset charset;

	public RpcWriter(Charset charset) {
		this.charset = charset;
		byteArray = new ByteArrayOutputStream();
		out = new DataOutputStream(byteArray);
	}

	public void writeByte(byte value) {
		try {
			out.writeByte(value);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public void writeInt(int value) {
		try {
			out.writeInt(value);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public void writeLong(long value) {
		try {
			out.writeLong(value);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public void writeFloat(float value) {
		try {
			out.writeFloat(value);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public void writeDouble(double value) {
		try {
			out.writeDouble(value);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public void writeBool(boolean value) {
		try {
			out.writeBoolean(value);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public void WriteString(String str) {
		try {
			byte[] bytes = str.getBytes(charset);
			out.writeInt(bytes.length);
			out.write(bytes);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public void writeDate(Date date) {
		try {
			long time = date.getTime();
			out.writeLong(time);
		} catch (IOException e) {
			logger.error("RpcWrite write date error : " + e.getMessage());
		}
	}

	public void writeObject(Object object) {
		try {
			byte[] bytes = JSON.toJSONString(object).getBytes(charset);
			out.writeInt(bytes.length);
			out.write(bytes);
		} catch (IOException e) {
			logger.error("RpcWrite write error : " + e.getMessage());
		}
	}

	public byte[] getBytes() {
		return byteArray.toByteArray();
	}

}
