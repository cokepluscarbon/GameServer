package com.cpcb.gs;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import org.junit.Test;

import com.cpcb.gs.RpcMessage.RpcRequest.RequestHeader;
import com.google.protobuf.ByteString;

public class ClientTest {

	@Test
	public void Test_01() throws UnknownHostException, IOException {
		String msg = "Hello GameSerer!";

		Socket socket = new Socket("127.0.0.1", 8080);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		long start = System.currentTimeMillis();
		for (int i = 0; i < 102400; i++) {
			RequestHeader header = RequestHeader.newBuilder().setRpcId(new Random().nextInt())
					.setReqId(new Random().nextInt()).build();
			RpcMessage.RpcRequest request = RpcMessage.RpcRequest.newBuilder().setHeader(header)
					.setContent(
							ByteString.copyFrom(("Hello GameServer!Hello GameServer!Hello GameServer!" + i).getBytes()))
					.build();

			byte[] bytes = request.toByteArray();
			out.writeInt(bytes.length);
			out.write(bytes);
			out.flush();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time last " + (end - start));
		System.out.println("Per requset spend " + (end - start) * 1f / 102400);

		out.close();

		socket.close();

	}

}
