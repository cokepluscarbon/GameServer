package com.cpcb.gs;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import com.cpcb.gs.RpcMessage.RpcRequest.RequestHeader;
import com.cpcb.gs.RpcMessage.RpcResponse;
import com.cpcb.gs.RpcMessage.RpcResponse.ResponseHeader;
import com.cpcb.gs.io.ProtocolDeploy;
import com.cpcb.gs.io.RpcReader;
import com.cpcb.gs.io.RpcWriter;
import com.cpcb.gs.rpc.RpcHandlerMapping;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Method;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameServerAdapter extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {
		InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		// System.out.println(socketAddress.getAddress().getHostAddress() + ":"
		// + socketAddress.getPort());

		ByteBuf buf = (ByteBuf) msg;
		buf.readInt();

		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);

		RpcMessage.RpcRequest request = RpcMessage.RpcRequest.parseFrom(data);

		invokeRpcMethod(ctx, buf, request);
	}

	private void invokeRpcMethod(ChannelHandlerContext ctx, ByteBuf buf, RpcMessage.RpcRequest request) {
		int rpcId = request.getHeader().getRpcId();
		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class);

		RpcHandlerMapping rpcHandler = ServerContext.rpcHandlerMap.get(deploy.rpc);
		try {

			Class<?>[] parameterTypes = rpcHandler.method.getParameterTypes();
			Object[] args = new Object[parameterTypes.length];

			int index = 0;
			RpcReader rpcReader = new RpcReader(request.getContent().toByteArray());
			RpcWriter rpcWriter = new RpcWriter();

			for (Class<?> pType : parameterTypes) {
				if (pType.equals(int.class)) {
					args[index] = rpcReader.readInt();
				} else if (pType.equals(String.class)) {
					args[index] = rpcReader.readString();
				} else if (pType.equals(RpcReader.class)) {
					args[index] = rpcReader;
				} else if (pType.equals(RpcWriter.class)) {
					args[index] = rpcWriter;
				} else {
					args[index] = null;
				}

				index++;
			}

			Object returnObj = rpcHandler.method.invoke(ServerContext.ctx.getBean(rpcHandler.clazz), args);

			Class<?> returnType = rpcHandler.method.getReturnType();
			if (returnType.equals(int.class)) {
				rpcWriter.WriteInt((Integer) returnObj);
			} else if (returnType.equals(void.class)) {

			} else {
				rpcWriter.WriteObject(returnObj);
			}

			// Write Back
			ResponseHeader header = RpcMessage.RpcResponse.ResponseHeader.newBuilder()
					.setReqId(request.getHeader().getReqId()).build();
			RpcResponse response = RpcMessage.RpcResponse.newBuilder().setHeader(header)
					.setContent(ByteString.copyFrom(rpcWriter.getBytes())).build();

			byte[] backBytes = response.toByteArray();

			ByteBuf backBuf = ctx.alloc().buffer(backBytes.length);
			backBuf.writeInt(backBytes.length);
			backBuf.writeBytes(backBytes);
			ctx.writeAndFlush(backBuf);

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
