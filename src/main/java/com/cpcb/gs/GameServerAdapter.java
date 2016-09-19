package com.cpcb.gs;

import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameServerAdapter extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {
		ByteBuf buf = (ByteBuf) msg;
		buf.readByte();

		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);

		System.out.println(new String(data) + " : " + System.nanoTime());

		// RpcMessage.RpcRequest request = RpcMessage.RpcRequest.parseFrom(new
		// byte[buf.readableBytes()]);
		//
		// System.out.println("rpc_id -> " + request.getHeader().getRpcId());
		// System.out.println("req_id -> " + request.getHeader().getReqId());
		// System.out.println("content -> " + request.getContent().toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}