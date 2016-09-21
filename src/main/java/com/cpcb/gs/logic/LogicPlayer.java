package com.cpcb.gs.logic;

import org.springframework.stereotype.Component;

import com.cpcb.gs.annotation.Logic;
import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.io.RpcReader;
import com.cpcb.gs.io.RpcWriter;

@Logic
@Component
public class LogicPlayer {
	public static int count = 0;

	@Rpc("rpc_get_player")
	public void getPlayer(RpcWriter writer, RpcReader reader) {
		System.err.println(String.format("call rpc_get_player [writer=%s,reader=%s]", writer, reader));

		//System.err.println(new String(reader.readString()));
		
		//writer.WriteString("RPC CALL BACK! cout = " + count++);
	}

}
