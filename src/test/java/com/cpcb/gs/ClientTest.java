package com.cpcb.gs;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import com.cpcb.gs.RpcMessage.RpcRequest.RequestHeader;
import com.google.protobuf.ByteString;

public class ClientTest {

	@Test
	public void Test_01() throws UnknownHostException, IOException {
		String msg = "Hello GameSerer!";

		Socket socket = new Socket("127.0.0.1", 8080);

		OutputStream out = socket.getOutputStream();

		RequestHeader header = RequestHeader.newBuilder().setRpcId(123456).setReqId(5201314).build();
		RpcMessage.RpcRequest request = RpcMessage.RpcRequest.newBuilder().setHeader(header)
				.setContent(ByteString.copyFrom(msg.getBytes())).build();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 102400; i++) {
			int len = msg.getBytes().length;
			out.write(len);
			// System.out.println("msg len = " + len);
			out.write(msg.getBytes());
			out.flush();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time last " + (end - start));
		System.out.println("Per requset spend " + (end - start) * 1f / 102400);

		out.close();

		socket.close();

	}

}
