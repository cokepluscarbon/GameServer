package com.cpcb.gs.logic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.cpcb.gs.annotation.Logic;
import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.io.RpcWriter;
import com.mysql.cj.core.io.ByteValueFactory;

import io.netty.buffer.ByteBuf;

@Logic
@Component
public class LogicPlayer {

	@Rpc("rpc_get_player")
	public void getPlayer(ByteBuf buf) {
		System.err.println(String.format("call rpc_get_player"));
	}

}
