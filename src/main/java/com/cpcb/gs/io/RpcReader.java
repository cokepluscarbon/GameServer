package com.cpcb.gs.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class RpcReader {
	private final Logger logger = LoggerFactory.getLogger(RpcReader.class);
	private DataInputStream input;

	public RpcReader(byte[] bytes) {
		input = new DataInputStream(new ByteArrayInputStream(bytes));
	}

	public int readInt() {
		try {
			return input.readInt();
		} catch (IOException e) {
			logger.error("RpcReader reach the end of bytes.");
		}
		return 0;
	}

	public long readLong() {
		try {
			return input.readLong();
		} catch (IOException e) {
			logger.error("RpcReader reach the end of bytes.");
		}
		return 0;
	}

	public float readFloat() {
		try {
			return input.readFloat();
		} catch (IOException e) {
			logger.error("RpcReader reach the end of bytes.");
		}
		return 0;
	}

	public double readDouble() {
		try {
			return input.readDouble();
		} catch (IOException e) {
			logger.error("RpcReader reach the end of bytes.");
		}
		return 0;
	}

	public String readString() {
		int len = readInt();
		byte[] bytes = new byte[len];
		try {
			input.read(bytes);
			return new String(bytes);
		} catch (IOException e) {
			logger.error("RpcReader reach the end of bytes.");
		}

		return null;
	}

	public <T> T readObject(Class<T> clazz) {
		String json = readString();
		return JSON.parseObject(json, clazz);
	}
}