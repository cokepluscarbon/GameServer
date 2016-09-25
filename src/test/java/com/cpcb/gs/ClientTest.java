package com.cpcb.gs;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.Test;

import com.cpcb.gs.RpcMessage.RpcRequest.RequestHeader;
import com.cpcb.gs.io.RpcReader;
import com.cpcb.gs.io.RpcWriter;
import com.google.protobuf.ByteString;

public class ClientTest {

	@Test
	public void Test_01() throws UnknownHostException, IOException, InterruptedException {
		Socket socket = new Socket("127.0.0.1", 8080);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		final DataInputStream in = new DataInputStream(socket.getInputStream());

		new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						int len = in.readInt();
						byte[] bytes = new byte[len];
						in.read(bytes);

						RpcMessage.RpcResponse response = RpcMessage.RpcResponse.parseFrom(bytes);
						byte[] content = response.getContent().toByteArray();

						RpcReader reader = new RpcReader(content, StandardCharsets.UTF_8);
						System.out.println(reader.readString());

					}
				} catch (Exception e) {

				}
			}
		}).start();

		int count = 0;
		while (true) {
			int rpcId = new Random().nextInt(4) + 1;

			RpcWriter writer = new RpcWriter(StandardCharsets.UTF_8);
			writer.WriteInt(5);
			writer.WriteString("I am Unity Client!");

			RequestHeader header = RequestHeader.newBuilder().setRpcId(5).setReqId(1).build();
			RpcMessage.RpcRequest request = RpcMessage.RpcRequest.newBuilder().setHeader(header)
					.setContent(ByteString.copyFrom(writer.getBytes())).build();

			byte[] bytes = request.toByteArray();
			out.writeShort(bytes.length);
			out.write(bytes);
			out.flush();
			Thread.sleep(500);
		}
	}

	@Test
	public void Test_02() throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 8080);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		long start = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			RpcWriter writer = new RpcWriter(StandardCharsets.UTF_8);
			writer.WriteString("I am Unity Client!");

			RequestHeader header = RequestHeader.newBuilder().setRpcId(5).setReqId(1).build();
			RpcMessage.RpcRequest request = RpcMessage.RpcRequest.newBuilder().setHeader(header)
					.setContent(ByteString.copyFrom(writer.getBytes())).build();

			byte[] bytes = request.toByteArray();
			System.out.println("len => " + bytes.length);
			out.writeShort(bytes.length);
			out.write(bytes);
			out.flush();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time last " + (end - start));
		System.out.println("Per requset spend " + (end - start) * 1f / 3);

		out.close();

		socket.close();

	}

}
