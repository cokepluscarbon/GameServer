package com.cpcb.gs.adapter;

import java.lang.reflect.InvocationTargetException;

import com.cpcb.gs.RpcMessage;
import com.cpcb.gs.RpcMessage.RpcResponse;
import com.cpcb.gs.RpcMessage.RpcResponse.ResponseHeader;
import com.cpcb.gs.ServerContext;
import com.cpcb.gs.io.ProtocolDeploy;
import com.cpcb.gs.io.RpcReader;
import com.cpcb.gs.io.RpcWriter;
import com.cpcb.gs.rpc.RpcHandlerMapping;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DispatchLogicAdapter extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {
		dispatchLogic(ctx, (RpcMessage.RpcRequest) msg);
	}

	private void dispatchLogic(ChannelHandlerContext ctx, RpcMessage.RpcRequest request) {
		int rpcId = request.getHeader().getRpcId();
		ProtocolDeploy deploy = ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class);
		RpcHandlerMapping rpcHandler = ServerContext.rpcHandlerMap.get(deploy.rpc);

		if (rpcHandler != null) {
			try {
				RpcWriter rpcWriter = dynamicInvokeRpcMethod(rpcHandler, request.getContent().toByteArray());

				writeBack(ctx, request, rpcWriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private RpcWriter dynamicInvokeRpcMethod(RpcHandlerMapping rpcHandler, byte[] content)
			throws IllegalAccessException, InvocationTargetException {
		RpcReader rpcReader = new RpcReader(content);
		RpcWriter rpcWriter = new RpcWriter();

		Object[] args = handleParameters(rpcHandler, rpcReader, rpcWriter);
		Object returnObj = rpcHandler.method.invoke(ServerContext.ctx.getBean(rpcHandler.clazz), args);

		hanldeReturn(rpcHandler, rpcWriter, returnObj);

		return rpcWriter;
	}

	private void hanldeReturn(RpcHandlerMapping rpcHandler, RpcWriter rpcWriter, Object returnObj) {
		Class<?> returnType = rpcHandler.method.getReturnType();
		if (returnType.equals(int.class)) {
			rpcWriter.WriteInt((Integer) returnObj);
		} else if (returnType.equals(void.class)) {

		} else {
			rpcWriter.WriteObject(returnObj);
		}
	}

	private Object[] handleParameters(RpcHandlerMapping rpcHandler, RpcReader rpcReader, RpcWriter rpcWriter) {
		Class<?>[] parameterTypes = rpcHandler.method.getParameterTypes();
		Object[] args = new Object[parameterTypes.length];
		int index = 0;

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
		return args;
	}

	private void writeBack(ChannelHandlerContext ctx, RpcMessage.RpcRequest request, RpcWriter rpcWriter) {
		ResponseHeader header = RpcMessage.RpcResponse.ResponseHeader.newBuilder()
				.setReqId(request.getHeader().getReqId()).build();
		RpcResponse response = RpcMessage.RpcResponse.newBuilder().setHeader(header)
				.setContent(ByteString.copyFrom(rpcWriter.getBytes())).build();

		byte[] backBytes = response.toByteArray();

		ByteBuf backBuf = ctx.alloc().buffer(backBytes.length);
		backBuf.writeInt(backBytes.length);
		backBuf.writeBytes(backBytes);
		ctx.writeAndFlush(backBuf);
	}
}
