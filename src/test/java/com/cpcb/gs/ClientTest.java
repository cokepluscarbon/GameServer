package com.cpcb.gs;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import org.junit.Test;

import com.cpcb.gs.RpcMessage.RpcRequest.RequestHeader;
import com.cpcb.gs.io.RpcWriter;
import com.google.protobuf.ByteString;

public class ClientTest {

	@Test
	public void Test_02() throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 8080);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			RpcWriter writer = new RpcWriter();
			writer.WriteString("Hello GameServer : " + i);
			
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
		System.out.println("Per requset spend " + (end - start) * 1f / 102400);

		out.close();

		socket.close();

	}

}
