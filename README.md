# GameServer

基于Netty 4.2.1和Protocol Buffers 2.4开发的游戏RPC框架。

Logic基本跟Spring MVC的Controller使用相似。

1、使用@Logic注释RPC处理类，跟Spring MVC的@Controller类似；

```Java
@Logic
@Component
public class LogicPlayer {
	private final Logger logger = LoggerFactory.getLogger(LogicPlayer.class);

  // ...
}
```

2、使用@Rpc注释Rpc调用的路径，跟Spring MVC的@RequestMapping类似；

```Java
@Logic
@Component
public class LogicPlayer {
	private final Logger logger = LoggerFactory.getLogger(LogicPlayer.class);

	@Rpc("rpc_get_player")
	public void getPlayer(RpcWriter writer, RpcReader reader) {
		logger.info("getPlayer method");
		int rpcId = reader.readInt();

		writer.WriteObject(ProtocolDeploy.getDeploy(rpcId, ProtocolDeploy.class));
	}
}
```

3、支持动态解析RPC包的参数，例如下面的rpc_get_player中的msgId和message，框架会自动解析RPC包中的参数并赋值给变量；

```Java
@Logic
@Component
public class LogicPlayer {
	private final Logger logger = LoggerFactory.getLogger(LogicPlayer.class);

	@Rpc("rpc_get_player")
	public void getPlayer(int msgId, String message) {
  
	}
}
```

4、可以返回任意类型，框架会自动序列化对象并写入到RPC的输出流中；

5、多功能的配置表解析机制，支持任意类型数据解析: TableLoader；




