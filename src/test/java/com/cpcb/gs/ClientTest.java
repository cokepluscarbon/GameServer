package com.cpcb.gs;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
						System.out.println("Client len -> " + len);

						byte[] bytes = new byte[len];
						in.read(bytes);

						RpcMessage.RpcResponse response = RpcMessage.RpcResponse.parseFrom(bytes);
						byte[] content = response.getContent().toByteArray();

						RpcReader reader = new RpcReader(content);
						System.out.println(reader.readString());
						
					}
				} catch (Exception e) {

				}
			}
		}).start();

		int count = 0;
		while (true) {
			RpcWriter writer = new RpcWriter();
			writer.WriteString("Hello GameServer : " + count++);
			writer.WriteString("----------------- zengguotai");
			writer.WriteInt(10086);

			RequestHeader header = RequestHeader.newBuilder().setRpcId(1).setReqId(new Random().nextInt()).build();
			RpcMessage.RpcRequest request = RpcMessage.RpcRequest.newBuilder().setHeader(header)
					.setContent(ByteString.copyFrom(writer.getBytes())).build();

			byte[] bytes = request.toByteArray();
			out.writeInt(bytes.length);
			out.write(bytes);
			out.flush();
			Thread.sleep(50);
		}
	}

	public void Test_02() throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 8080);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		long start = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			RpcWriter writer = new RpcWriter();
			writer.WriteString("Hello GameServer : " + i);
			writer.WriteString("----------------- zengguotai");
			writer.WriteInt(10086);

			RequestHeader header = RequestHeader.newBuilder().setRpcId(1).setReqId(new Random().nextInt()).build();
			RpcMessage.RpcRequest request = RpcMessage.RpcRequest.newBuilder().setHeader(header)
					.setContent(ByteString.copyFrom(writer.getBytes())).build();

			byte[] bytes = request.toByteArray();
			out.writeInt(bytes.length);
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
