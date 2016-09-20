package com.cpcb.gs.logic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.cpcb.gs.annotation.Logic;
import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.io.RpcWriter;

@Logic
@Component
public class LogicPlayer {

	@Rpc("rpc_get_player")
	public void getPlayer(long pid) {
		System.out.println(String.format("rpc_get_player[pid=%s]", pid));
	}

	@Rpc("rpc_player_rename")
	public byte playerRename(String newName) {
		return 0;
	}

	@Rpc("rpc_player_xxx")
	public void playerxxx(DataOutputStream out) throws IOException {
		out.writeInt(1);
		out.writeBytes("Hello RPC!");
	}

	@Rpc("rpc_player_yyy")
	public void playeryyy(long pid, RpcWriter writer) throws IOException {
		writer.WriteBool(true);
		writer.WriteString("Hello RPC!");
		writer.WriteObject(new Object());
	}

}
