package com.cpcb.gs.handler;

import com.cpcb.gs.adapter.DispatchLogicAdapter;
import com.cpcb.gs.adapter.RpcMessageAdapter;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class GameServerHanlder implements ChannelHandler {

	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		ctx.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4));
		ctx.pipeline().addLast(new RpcMessageAdapter());
		ctx.pipeline().addLast(new DispatchLogicAdapter());
	}

	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub

	}

}
