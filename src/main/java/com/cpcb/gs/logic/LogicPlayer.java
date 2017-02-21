package com.cpcb.gs.logic;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cpcb.gs.annotation.Logic;
import com.cpcb.gs.annotation.Rpc;
import com.cpcb.gs.io.ProtocolDeploy;
import com.cpcb.gs.io.RpcReader;
import com.cpcb.gs.io.RpcWriter;

@Logic
@Component
public class LogicPlayer {
	private final Logger logger = LoggerFactory.getLogger(LogicPlayer.class);
	public static int count = 0;

	@Rpc("rpc_get_player")
	public void getPlayer(RpcWriter writer, RpcReader reader) {
		logger.info("getPlayer method");
		int rpcId = reader.readInt();

		writer.writeObject(ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class));
	}

	@Rpc("rpc_get_hero")
	public void getHero(int rpcId, RpcWriter writer) {
		logger.info("getHero method");
		writer.writeObject(ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class));
	}

	@Rpc("rpc_get_gun")
	public ProtocolDeploy getGun(int rpcId, String msg) {
		logger.info("getGun method");
		logger.info("rpc_get_gun msg = " + msg);
		return ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class);
	}

	@Rpc("rpc_get_unity")
	public ProtocolDeploy getUnity(int rpcId) {
		System.out.println("rpcId = " + rpcId);
		logger.info("getGun rpc_get_unity");
		return ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class);
	}

	@Rpc("rpc_heart_beat")
	public String heartBeat(Date currTime) {
		logger.info("receive heart beat at " + currTime);
		return "Server receive heart beat at " + currTime + "!";
	}

}
