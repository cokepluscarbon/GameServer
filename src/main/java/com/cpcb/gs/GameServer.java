package com.cpcb.gs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cpcb.gs.handler.GameServerHanlder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@SuppressWarnings("unused")
public class GameServer {
	private final Logger logger = LoggerFactory.getLogger(GameServer.class);

	public static void main(String[] args) throws Exception {
		new ServerContext().start();
		new GameServer().run();
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workerGroup);
			server.channel(NioServerSocketChannel.class);
			server.childHandler(new GameServerHanlder());
			server.option(ChannelOption.SO_BACKLOG, 128);
			server.childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture channerlFuture = server.bind(8080).sync();

			logger.info("GameServer start at {}", new Date());
			channerlFuture.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}