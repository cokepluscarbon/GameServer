package com.cpcb.gs;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Random;

import org.springframework.beans.BeansException;

import com.cpcb.gs.io.ProtocolDeploy;
import com.cpcb.gs.io.RpcReader;
import com.cpcb.gs.io.RpcWriter;
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

		RpcMessage.RpcRequest request = RpcMessage.RpcRequest.parseFrom(data);

		invokeRpcMethod(buf, request);
	}

	private void invokeRpcMethod(ByteBuf buf, RpcMessage.RpcRequest request) {
		int rpcId = request.getHeader().getRpcId();
		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class);

		RpcHandlerMapping rpcHandler = ServerContext.rpcHandlerMap.get(deploy.rpc);
		try {

			Class<?>[] parameterTypes = rpcHandler.method.getParameterTypes();
			Object[] args = new Object[parameterTypes.length];
			int index = 0;
			for (Class<?> pType : parameterTypes) {
				System.out.println(pType);
				if (pType.equals(RpcReader.class)) {
					args[index] = new RpcReader(request.getContent().toByteArray());
				} else if (pType.equals(RpcWriter.class)) {
					args[index] = new RpcWriter(buf);
				} else {
					args[index] = null;
				}

				index++;
			}
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
