package com.cpcb.gs.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cpcb.gs.RpcMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcMessageAdapter extends ChannelInboundHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger(RpcMessageAdapter.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {
		ByteBuf buf = (ByteBuf) msg;

		short mLen = buf.readShort();
		if (mLen == 0) {
			logger.error("RpcMessage lenth is 0");
		} else {
			byte[] data = new byte[mLen];
			buf.readBytes(data);

			RpcMessage.RpcRequest request = RpcMessage.RpcRequest.parseFrom(data);
			ctx.fireChannelRead(request);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
