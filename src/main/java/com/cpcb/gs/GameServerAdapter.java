package com.cpcb.gs;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Random;

import org.springframework.beans.BeansException;

import com.cpcb.gs.io.ProtocolDeploy;
import com.cpcb.gs.rpc.RpcHandlerMapping;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Method;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameServerAdapter extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {
		InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		System.out.println(socketAddress.getAddress().getHostAddress() + ":" + socketAddress.getPort());

		ByteBuf buf = (ByteBuf) msg;
		buf.readInt();

		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);

		// System.out.println(new String(data) + " : " + System.nanoTime());

		RpcMessage.RpcRequest request = RpcMessage.RpcRequest.parseFrom(data);

		System.out.println("rpc_id -> " + request.getHeader().getRpcId());
		System.out.println("req_id -> " + request.getHeader().getReqId());
		System.out.println("content -> " + new String(request.getContent().toByteArray()));

		int rpcId = request.getHeader().getRpcId();
		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class);

		RpcHandlerMapping rpcHandler = ServerContext.rpcHandlerMap.get(deploy.rpc);
		try {
			Object[] args = new Object[] { buf, new Random().nextInt() };
			rpcHandler.method.invoke(ServerContext.ctx.getBean(rpcHandler.clazz), args);
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
