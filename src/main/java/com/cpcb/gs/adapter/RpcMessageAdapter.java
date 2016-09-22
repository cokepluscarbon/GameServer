package com.cpcb.gs.adapter;

import com.cpcb.gs.RpcMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcMessageAdapter extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {
		ByteBuf buf = (ByteBuf) msg;
		buf.readInt();

		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		
		RpcMessage.RpcRequest request = RpcMessage.RpcRequest.parseFrom(data);
		ctx.fireChannelRead(request);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
