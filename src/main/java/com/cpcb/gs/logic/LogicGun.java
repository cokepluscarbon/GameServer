package com.cpcb.gs.logic;

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
public class LogicGun {
	private final Logger logger = LoggerFactory.getLogger(LogicGun.class);
	public static int count = 0;

	@Rpc("rpc_user_login")
	public void userLogin(RpcWriter writer, RpcReader reader) {
		logger.info("userLogin method");
		int rpcId = reader.readInt();

		writer.writeObject(ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class));
	}

}
