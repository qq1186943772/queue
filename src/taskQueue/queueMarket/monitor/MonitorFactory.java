package taskQueue.queueMarket.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import taskQueue.conn.Connect;

/**
 * 监控中兴生产者工厂
 * @author 王勃
 *
 */
public class MonitorFactory {
	
	private MonitorFactory() {}
	
	private static class SingletonInstance {
		private static final MonitorFactory FACTORY = new MonitorFactory();
	}
	
	/**
	 * 返回单例 监控中心工厂
	 * @return 
	 */
	public static MonitorFactory monitorFactory() {
		return SingletonInstance.FACTORY;
	}
	
	/**
	 * 需要监听的 队列的名称 与 监控中心绑定
	 * 每个队列一个监控中心，监控队列的任务情况 
	 */
	static Map<String,Thread> queuenameToServer = new ConcurrentHashMap<String,Thread>();
	
	/**
	 * 构件监控中兴
	 * @param queueName	
	 * @param producer
	 */
	public static void createMonitor(String queueName,Connect connect) {
		
		if(!queuenameToServer.containsKey(queueName)) {
			Thread server = new MonitorServer(queueName,connect);
			queuenameToServer.put(queueName, server);
			server.start();
		}
		
	}
	
}
