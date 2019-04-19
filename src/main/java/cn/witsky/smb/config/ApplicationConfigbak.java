//package cn.witsky.smb.config;
//
//import cn.witsky.smb.StateMachine.Event;
//import cn.witsky.smb.StateMachine.State;
//import cn.witsky.smb.StateMachine.StateMachineForCollectAndConnect;
//import cn.witsky.smb.StateMachine.StateMachineStore;
//import cn.witsky.smb.pojo.ContextData;
//import cn.witsky.smb.pojo.apk.ApkConfig;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
//import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
//import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
//
///**
// * Created by Administrator on 2019/4/4.
// */
//@Configuration
//public class ApplicationConfigbak {
//	public Config config;
//
//
//	public ApplicationConfigbak(Config config) {
//		this.config=config;
//	}
//
//
//	/**
//	 * 放音收号后再连接的状态机
//	 * @return
//	 */
//	@Bean(name = "stateMachineBuilderForCollectAndConnect")
//	UntypedStateMachineBuilder createStateMachineBuilderForCollectAndConnect() {
//
//		UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(StateMachineForCollectAndConnect.class,
//				ApplicationContext.class,ContextData.class,StateMachineStore.class, ApkConfig.class);
//		builder.setStateMachineConfiguration(StateMachineConfiguration.create());
//		// 定义状态机的状态和跃迁事件
//		// （重要）定时事件触发必须定义在状态跃迁之前，否则不生效
//		// 定时事件触发：【waitAlert状态】下n秒后触发【等待alert超时事件】
//		builder.defineTimedState(State.waitAlerting, config.getTimeoutForWaitingAlert(), 0, Event.timeoutForAlert, null);
////		// 定时事件触发：【waitRinging状态】下n秒后触发【waitRinging超时事件】
//		builder.defineTimedState(State.waitRinging, config.getTimeoutForWaitingRing(), 0, Event.timeoutForRing,
//				null);
//		//定时事件触发：【waitConnect状态】下n秒后触发【waitConnect超时事件】
//		builder.defineTimedState(State.waitConnect, config.getTimeoutForWaitingConnect(), 0, Event.timeoutForConnnect,
//				null);
//		//定时事件触发：【Connect状态】下n秒后触发【放音收号事件】
//		builder.defineTimedState(State.connected, 10, 0, Event.PA,
//				null);
////定时事件触发：【idle状态】下n秒后触发【准备连接】
//		//为了测试销毁状态机 让状态机直接销毁
//		builder.defineTimedState(State.idle, 10, 0, Event.prepareToConnect,
//				null);
////		builder.defineTimedState(State.waitAlerting, 1000, 0, Event.receiveRelease,
////				null);
//		// 状态跃迁：【初始状态】下触发【准备开始连接事件】时，并调用方法(发起连接)
//		builder.internalTransition().within(State.idle).on(Event.prepareToConnect).callMethod("prepareToConnect");
//// 状态跃迁：【初始状态】下触发【准备开始连接事件】时，并调用方法(发起连接)
//		builder.internalTransition().within(State.idle).on(Event.intoFail).callMethod("intoFail");
//		// 状态跃迁：【初始状态】下触发【connectSuccess】时
//		builder.externalTransition().from(State.idle).to(State.waitAlerting).on(Event.connectSuccess);
//
//
//
//		// 状态跃迁：【waitAlert】下触发【receiveAlert】时，跳到【waitRing】
//		builder.externalTransition().from(State.waitAlerting).to(State.waitRinging).on(Event.receiveAlerting).callMethod("receiveAlerting");
//		// 状态跃迁：【waitAlert】下触发【timeoutForAlert】时，跳到【Idel】，并调用方法(endCall)
//		builder.externalTransition().from(State.waitAlerting).to(State.released).on(Event.timeoutForAlert).callMethod("timeoutForAlert");
//		// 状态跃迁：【waitAlert】下触发【receiveRelease】时，跳到【Idel】，并调用方法(releaseSelf)
//		builder.externalTransition().from(State.waitAlerting).to(State.released).on(Event.receiveRelease).callMethod("receiveRelease");
//
//
//		// 状态跃迁：【waitRinging】下触发【receiveRinging】时，跳到【waitConnect】
//		builder.externalTransition().from(State.waitRinging).to(State.waitConnect).on(Event.receiveRinging).callMethod("receiveRinging");
//		// 状态跃迁：【waitRinging】下触发【timeoutForRing】时，跳到【Idel】，并调用方法(endCall)
//		builder.externalTransition().from(State.waitRinging).to(State.released).on(Event.timeoutForRing).callMethod("timeoutForRing");
//		// 状态跃迁：【waitRinging】下触发【receiveRelease】时，跳到【Idel】，并调用方法(endCall)
//		builder.externalTransition().from(State.waitRinging).to(State.released).on(Event.receiveRelease).callMethod("receiveRelease");
//		builder.externalTransition().from(State.waitRinging).to(State.connected).on(Event.receiveConnnecting).callMethod("receiveConnnecting");
//
//
//
//		// 状态跃迁：【waitConnect】下触发【receiveConnnecting】时，跳到【connected】
//		builder.externalTransition().from(State.waitConnect).to(State.connected).on(Event.receiveConnnecting).callMethod("receiveConnnecting");
//		// 状态跃迁：【waitConnect】下触发【timeoutForConnnect】时，跳到【Idel】，并调用方法(endCall)
//		builder.externalTransition().from(State.waitConnect).to(State.released).on(Event.timeoutForConnnect).callMethod("timeoutWhenRinging");
//		// 状态跃迁：【waitConnect】下触发【receiveRelease】时，跳到【Idel】，并调用方法(releaseSelf)
//		builder.externalTransition().from(State.waitConnect).to(State.released).on(Event.receiveRelease).callMethod("ReleasedWhenRinging");
//
//		builder.internalTransition().within(State.waitConnect).on(Event.receiveConnnecting).callMethod("receiveConnnecting");
//
//
//
//		// 状态跃迁：【connected】下触发【PA】时，调用方法(PLAYONLY)
//		builder.internalTransition().within(State.connected).on(Event.PA).callMethod("playOnly");
//		// 状态跃迁：【connected】下触发【playEnd】时，调用方法(playEnd)
//		builder.internalTransition().within(State.connected).on(Event.playEnd).callMethod("playEnd");
//		// 状态跃迁：【connected】下触发【ReleaseWithNoplay】时，跳到【Idel】，并调用方法(endCall)
//		builder.externalTransition().from(State.connected).to(State.released).on(Event.ReleaseWithNoplay).callMethod("ReleaseWithNoplay");
//
//		// 状态跃迁：【connected】下触发【askForReplayKey】时，跳到【waitReport】，并调用方法(askForReplayKey)
//		builder.externalTransition().from(State.connected).to(State.waitReport).on(Event.askForReplayKey).callMethod("askForReplayKey");
//		// 状态跃迁：【connected】下触发【testSessionFail】时，跳到【Idel】，并调用方法(releaseSelf)
//		builder.externalTransition().from(State.connected).to(State.released).on(Event.testSessionFail).callMethod("testSessionFail");
//		// 【connected】下触发【timeToTestSession】时，调用方法(testSession)
//		builder.internalTransition().within(State.connected).on(Event.timeToTestSession).callMethod("testSession");
//		// 状态跃迁：【connected】下触发【receiveRelease】时，跳到【Idel】，并调用方法(releaseSelf)
//		builder.externalTransition().from(State.connected).to(State.released).on(Event.receiveRelease).callMethod("receiveRelease");
//
//
//		//serviceExecuteReportReceived
//		// 状态跃迁：【waitReport】下触发【ReleaseWithReplay】时，跳到【Idel】，并调用方法(releaseSelf)
//		builder.externalTransition().from(State.waitReport).to(State.released).on(Event.ReleaseWithReplay).callMethod("ReleaseWithReplay");
//		// 状态跃迁：【waitReport】下触发【timeoutForReport】时，跳到【Idel】，并调用方法(ReleaseWithNoplay)
//		builder.externalTransition().from(State.waitReport).to(State.released).on(Event.timeoutForReport).callMethod("ReleaseWithNoplay");
//		// 状态跃迁：【waitReport】下触发【ReleaseWithNoplay】时，跳到【Idel】，并调用方法(ReleaseWithNoplay)
//		builder.externalTransition().from(State.waitReport).to(State.released).on(Event.ReleaseWithNoplay).callMethod("ReleaseWithNoplay");
//		return builder;
//	}
//	public RedisConnectionFactory connectionFactory(
//			String hostName, int port, String password, int maxIdle, int minIdle, int maxActive, int databaseIndex, long maxWaitMillis, int timeout, boolean ssl) {
//		JedisConnectionFactory jedis = new JedisConnectionFactory();
//		jedis.setHostName(hostName);
//		jedis.setPort(port);
//		jedis.setPassword(password);
//		jedis.setDatabase(databaseIndex);
//		jedis.setTimeout(timeout);
//		jedis.setUseSsl(ssl);
//	//	jedis.setPoolConfig(this.poolCofig(maxIdle,minIdle, maxActive, maxWaitMillis));
//		// 初始化连接pool
//		jedis.afterPropertiesSet();
//		RedisConnectionFactory factory = jedis;
//		return factory;
//	}
//
//}
